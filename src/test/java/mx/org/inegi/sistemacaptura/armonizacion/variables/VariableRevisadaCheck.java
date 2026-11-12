package mx.org.inegi.sistemacaptura.armonizacion.variables;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.*;
import java.util.*;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.*;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.service.variables.variables_armo_service_impl;
import mx.org.inegi.sistemacaptura.armonizacion.service.fuentes.fuentes_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuentes_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.fuentes.fuentes_armo_repository;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tickets.tickets_variables_repo;
import mx.org.inegi.sistemacaptura.entity.variables.variable_movimiento_fuente_dto;

/** Regression checks with in-memory repositories, without database connections. */
public class VariableRevisadaCheck {
    private static final String ROOT = "mx.org.inegi.sistemacaptura.armonizacion.";
    private static int checks;
    private static int writes;
    private static final variables_armo_enty variable = new variables_armo_enty();

    private static void inject(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static Object proxy(Class<?> type, InvocationHandler handler) {
        return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[]{type}, handler);
    }

    private static void blocked(Runnable action) {
        int before = writes;
        try {
            action.run();
            throw new AssertionError("Reviewed variable was writable");
        } catch (org.springframework.web.server.ResponseStatusException e) {
            if (!e.getMessage().contains("revisada")) throw e;
        }
        if (writes != before) throw new AssertionError("Write before guard");
        checks++;
    }

    private static void call(Object target, String method, Class<?>[] types, Object... args) {
        try {
            target.getClass().getMethod(method, types).invoke(target, args);
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof RuntimeException) throw (RuntimeException)e.getCause();
            throw new AssertionError(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) throws Exception {
        variable.setIdA("TEST-001");
        variable.setValidada(true);
        variables_armo_repo repo = (variables_armo_repo) proxy(variables_armo_repo.class, (p, m, a) -> {
            if (m.isDefault()) return MethodHandles.lookup()
                    .findSpecial(m.getDeclaringClass(), m.getName(),
                            MethodType.methodType(m.getReturnType(), m.getParameterTypes()), m.getDeclaringClass())
                    .bindTo(p).invokeWithArguments(a);
            switch (m.getName()) {
                case "findById": case "buscarParaActualizar": return Optional.of(variable);
                case "bloquearPorFuente": return Collections.singletonList(variable);
                case "existsById": return true;
                case "save": writes++; return a[0];
                case "saveAll": writes++; return a[0];
                case "flush": return null;
                case "tieneClasificacionOClasificador": return false;
                default: throw new AssertionError("Unexpected variable call: " + m);
            }
        });
        variables_armo_service_impl service = new variables_armo_service_impl();
        inject(service, "variablesArmoRepo", repo);
        variables_armo_dto dto = new variables_armo_dto();
        dto.setIdA("TEST-001");
        dto.setValidada(false); // A stale/malicious payload must not bypass the stored flag.
        blocked(() -> service.guardarVariable(dto));
        blocked(() -> service.actualizarVariable("TEST-001", dto));
        blocked(() -> service.eliminarVariable("TEST-001"));
        blocked(() -> service.eliminarVariableDesdeValidacion("TEST-001"));
        blocked(() -> repo.exigirFuenteEditable("FUENTE"));
        fuentes_armo_enty fuenteDestino = new fuentes_armo_enty();
        fuenteDestino.setIdFuente("DESTINO");
        fuenteDestino.setAcronimo("TEST");
        inject(service, "fuentesArmoRepo", proxy(fuentes_armo_repository.class, (p, m, a) -> {
            if (m.getName().equals("findByIdFuente")) return Optional.of(fuenteDestino);
            writes++;
            throw new AssertionError("Unexpected destination write: " + m);
        }));
        variable_movimiento_fuente_dto movimiento = new variable_movimiento_fuente_dto();
        movimiento.setIdFuenteDestino("DESTINO");
        movimiento.setIdsVariables(Collections.singletonList("TEST-001"));
        blocked(() -> service.moverVariablesDeFuente(movimiento));

        String[][] relations = {
            {"clasificaciones", "clasificaciones_armo", "clasificacionesArmoRepo", "Clasificacion"},
            {"clasificadores", "clasificadores_armo", "clasificadoresArmoRepo", "Clasificador"},
            {"microdatos", "microdatos_armo", "microdatosArmoRepo", "Microdato"},
            {"datosabiertos", "datos_abiertos_armo", "datosAbiertosArmoRepo", "DatoAbierto"}
        };
        for (String[] relation : relations) {
            String dir = relation[0], name = relation[1], suffix = relation[3];
            Object relationService = Class.forName(ROOT + "service." + dir + "." + name + "_service_impl").getDeclaredConstructor().newInstance();
            Class<?> dtoType = Class.forName(ROOT + "entity." + dir + "." + name + "_dto");
            Object relationDto = dtoType.getDeclaredConstructor().newInstance();
            for (Method setter : dtoType.getMethods()) {
                if (setter.getName().startsWith("set") && Arrays.equals(setter.getParameterTypes(), new Class<?>[]{String.class}))
                    setter.invoke(relationDto, setter.getName().equals("setIdA") ? "TEST-001" : "dato");
            }
            Object entity = Class.forName(ROOT + "entity." + dir + "." + name + "_enty").getDeclaredConstructor().newInstance();
            entity.getClass().getMethod("setIdA", String.class).invoke(entity, "TEST-001");
            Class<?> relationRepo = Class.forName(ROOT + "repository." + dir + "." + name + "_repo");
            inject(relationService, "variablesArmoRepo", repo);
            inject(relationService, relation[2], proxy(relationRepo, (p, m, a) -> {
                if (m.getName().equals("findById")) return Optional.of(entity);
                if (m.getName().equals("existsById")) return true;
                writes++;
                throw new AssertionError("Unexpected relation write: " + m);
            }));
            blocked(() -> call(relationService, "guardar" + suffix, new Class<?>[]{dtoType}, relationDto));
            blocked(() -> call(relationService, "eliminar" + suffix, new Class<?>[]{Integer.class}, 1));
            if (!dir.equals("clasificaciones"))
                blocked(() -> call(relationService, "actualizar" + suffix, new Class<?>[]{Integer.class, dtoType}, 1, relationDto));
        }

        fuentes_armo_service fuentes = new fuentes_armo_service();
        fuentes_armo_enty fuente = new fuentes_armo_enty();
        fuente.setIdFuente("FUENTE");
        inject(fuentes, "variablesArmoRepo", repo);
        inject(fuentes, "repository", proxy(fuentes_armo_repository.class, (p, m, a) -> {
            if (m.getName().equals("findByIdFuente")) return Optional.of(fuente);
            writes++;
            throw new AssertionError("Unexpected source write: " + m);
        }));
        blocked(() -> fuentes.deleteFuenteByIdFuente("FUENTE"));

        variable.setValidada(false);
        variable.setAcronimo("TEST");
        variable.setIdFuente("ORIGEN");
        Map<String, Object> movidas = service.moverVariablesDeFuente(movimiento);
        if (!"DESTINO".equals(variable.getIdFuente()) || !Integer.valueOf(1).equals(movidas.get("totalMovidas"))) {
            throw new AssertionError("The harmonized variable was not moved to its destination");
        }
        checks++;
        final boolean[] ticketsVaciados = {false};
        fuentes_armo_service fuentesEditables = new fuentes_armo_service();
        inject(fuentesEditables, "variablesArmoRepo", repo);
        inject(fuentesEditables, "ticketsRepository", proxy(tickets_variables_repo.class, (p, m, a) -> {
            if (m.getName().equals("findByIdAOrderByFechaCreacionDesc")) return Collections.emptyList();
            if (m.getName().equals("deleteAll")) return null;
            if (m.getName().equals("flush")) { ticketsVaciados[0] = true; return null; }
            throw new AssertionError("Unexpected ticket call: " + m);
        }));
        inject(fuentesEditables, "repository", proxy(fuentes_armo_repository.class, (p, m, a) -> {
            if (m.getName().equals("findByIdFuente")) return Optional.of(fuente);
            if (m.getName().equals("delete")) {
                if (!ticketsVaciados[0]) throw new AssertionError("Source deleted before ticket cleanup");
                return null;
            }
            throw new AssertionError("Unexpected source call: " + m);
        }));
        fuentesEditables.deleteFuenteByIdFuente("FUENTE");
        checks++;

        service.actualizarValidacion("TEST-001", false);
        repo.exigirEditable("TEST-001");
        checks++;
        variable.setValidada(null);
        repo.exigirEditable("TEST-001");
        checks++;
        System.out.println("PASS: " + checks + " reviewed variable scenarios; no database writes");
    }
}
