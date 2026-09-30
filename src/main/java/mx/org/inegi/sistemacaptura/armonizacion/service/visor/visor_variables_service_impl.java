package mx.org.inegi.sistemacaptura.armonizacion.service.visor;

import java.util.*;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.repository.visor.visor_variables_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class visor_variables_service_impl implements visor_variables_service {
    @Autowired
    private visor_variables_repo repo;

    private static final List<String> TEXTOS = Arrays.asList("unidad", "acronimo",
            "idFuente", "tematica", "tema1", "subtema1", "tema2", "subtema2");
    private static final List<String> BANDERAS = Arrays.asList("clasificacion",
            "tabulados", "datosabiertos", "mdea", "ods", "validada", "microdatos");

    private int entero(String valor, int defecto, String campo) {
        if (valor == null) return defecto;
        try {
            return Integer.parseInt(valor);
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El parametro " + campo + " debe ser entero");
        }
    }

    private Map<String, Object> interpretar(Map<String, String> parametros) {
        Map<String, Object> filtros = new LinkedHashMap<>();
        for (String campo : TEXTOS) {
            String valor = parametros.get(campo);
            if (valor != null && !valor.trim().isEmpty()) filtros.put(campo, valor);
        }
        String nombre = parametros.get("variableA");
        if (nombre != null && !nombre.trim().isEmpty()) {
            filtros.put("variableA", "%" + nombre.trim().toLowerCase(Locale.ROOT)
                    .replace("!", "!!").replace("%", "!%").replace("_", "!_") + "%");
        }
        if (parametros.containsKey("anioReferencia")) {
            filtros.put("anioReferencia",
                    String.valueOf(entero(parametros.get("anioReferencia"), 0, "anioReferencia")));
        }
        for (String campo : BANDERAS) {
            if (!parametros.containsKey(campo)) continue;
            String valor = parametros.get(campo);
            if (!"true".equals(valor) && !"false".equals(valor)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "El parametro " + campo + " debe ser true o false");
            }
            filtros.put(campo, Boolean.valueOf(valor));
        }
        return filtros;
    }

    private Map<String, Object> fila(Object[] valores, String... campos) {
        Map<String, Object> fila = new LinkedHashMap<>();
        for (int i = 0; i < campos.length; i++) fila.put(campos[i], valores[i]);
        return fila;
    }

    @Override
    public Map<String, Object> listar(Map<String, String> parametros) {
        int page = entero(parametros.get("page"), 0, "page");
        int size = entero(parametros.get("size"), 20, "size");
        if (page < 0 || !Arrays.asList(20, 50, 100).contains(size)
                || (long) page * size > Integer.MAX_VALUE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Pagina invalida o tamano distinto de 20, 50 o 100");
        }
        Map<String, Object> filtros = interpretar(parametros);
        long total = repo.contar(filtros);
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("content", repo.listar(filtros, page * size, size).stream()
                .map(valores -> fila(valores, "idA", "variableA", "acronimo", "idFuente",
                "fuente", "edicion", "anioReferencia", "validada")).collect(Collectors.toList()));
        resultado.put("page", page);
        resultado.put("size", size);
        resultado.put("totalElements", total);
        resultado.put("totalPages", (total + size - 1) / size);
        return resultado;
    }

    @Override
    public Map<String, Object> filtros(Map<String, String> parametros) {
        Map<String, Object> todos = interpretar(parametros);
        Map<String, Object> ambito = new LinkedHashMap<>();
        Map<String, Object> resultado = new LinkedHashMap<>();
        resultado.put("unidades", repo.unidades());
        if (todos.containsKey("unidad")) ambito.put("unidad", todos.get("unidad"));
        resultado.put("procesos", repo.procesos(ambito).stream()
                .map(v -> fila(v, "acronimo", "proceso")).collect(Collectors.toList()));
        if (todos.containsKey("acronimo")) ambito.put("acronimo", todos.get("acronimo"));
        resultado.put("fuentes", ambito.containsKey("acronimo")
                ? repo.fuentes(ambito).stream().map(v -> fila(v, "idFuente", "fuente", "edicion"))
                        .collect(Collectors.toList())
                : Collections.emptyList());
        if (todos.containsKey("idFuente")) ambito.put("idFuente", todos.get("idFuente"));
        resultado.put("anios", repo.distintos("anioReferencia", Integer.class, ambito));
        for (String campo : Arrays.asList("tematica", "tema1", "tema2")) {
            resultado.put(campo, repo.distintos(campo, String.class, ambito));
        }
        for (int i = 1; i <= 2; i++) {
            Map<String, Object> subambito = new LinkedHashMap<>(ambito);
            String tema = "tema" + i;
            if (todos.containsKey(tema)) subambito.put(tema, todos.get(tema));
            resultado.put("subtema" + i, repo.distintos("subtema" + i, String.class, subambito));
        }
        return resultado;
    }
}
