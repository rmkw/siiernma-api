package mx.org.inegi.sistemacaptura.armonizacion.repository.visor;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class visor_variables_repo {
    @PersistenceContext
    private EntityManager em;

    private static final String FROM = " FROM variables_armo_enty v";
    private static final Set<String> CAMPOS = new HashSet<>(Arrays.asList(
            "acronimo", "idFuente", "anioReferencia", "tematica", "tema1", "subtema1",
            "tema2", "subtema2", "clasificacion", "tabulados", "datosabiertos",
            "mdea", "ods", "validada", "microdatos"));

    private String condiciones(Map<String, Object> filtros) {
        StringBuilder where = new StringBuilder(" WHERE 1 = 1");
        for (String campo : filtros.keySet()) {
            if ("unidad".equals(campo)) {
                where.append(" AND EXISTS (SELECT p.acronimo FROM procesos_enty p")
                        .append(" WHERE p.acronimo = v.acronimo AND p.unidad = :unidad)");
            } else if ("variableA".equals(campo)) {
                where.append(" AND LOWER(v.variableA) LIKE :variableA ESCAPE '!'");
            } else if ("anioReferencia".equals(campo)) {
                where.append(" AND CAST(v.anioReferencia AS string) = :anioReferencia");
            } else if (CAMPOS.contains(campo)) {
                where.append(" AND v.").append(campo).append(" = :").append(campo);
            } else {
                throw new IllegalArgumentException("Filtro no permitido: " + campo);
            }
        }
        return where.toString();
    }

    private <T> TypedQuery<T> consulta(String jpql, Class<T> tipo, Map<String, Object> filtros) {
        TypedQuery<T> query = em.createQuery(jpql, tipo);
        filtros.forEach(query::setParameter);
        return query;
    }

    public List<Object[]> listar(Map<String, Object> filtros, int offset, int size) {
        String jpql = "SELECT v.idA, v.variableA, v.acronimo, v.idFuente,"
                + " f.fuente, f.edicion, v.anioReferencia, v.validada" + FROM
                + " LEFT JOIN fuentes_armo_enty f ON f.idFuente = v.idFuente"
                + condiciones(filtros) + " ORDER BY v.idA ASC";
        return consulta(jpql, Object[].class, filtros)
                .setFirstResult(offset).setMaxResults(size).getResultList();
    }

    public long contar(Map<String, Object> filtros) {
        return consulta("SELECT COUNT(v)" + FROM + condiciones(filtros),
                Long.class, filtros).getSingleResult();
    }

    public List<String> unidades() {
        return em.createQuery("SELECT DISTINCT p.unidad FROM procesos_enty p"
                + " WHERE p.unidad IS NOT NULL AND TRIM(p.unidad) <> ''"
                + " AND EXISTS (SELECT v.idA" + FROM + " WHERE v.acronimo = p.acronimo)"
                + " ORDER BY p.unidad", String.class).getResultList();
    }

    public List<Object[]> procesos(Map<String, Object> filtros) {
        return consulta("SELECT DISTINCT p.acronimo, p.proceso FROM procesos_enty p"
                + " WHERE EXISTS (SELECT v.idA" + FROM + condiciones(filtros)
                + " AND v.acronimo = p.acronimo) ORDER BY p.acronimo",
                Object[].class, filtros).getResultList();
    }

    public List<Object[]> fuentes(Map<String, Object> filtros) {
        return consulta("SELECT DISTINCT f.idFuente, f.fuente, f.edicion"
                + " FROM fuentes_armo_enty f WHERE EXISTS (SELECT v.idA"
                + FROM + condiciones(filtros) + " AND v.idFuente = f.idFuente)"
                + " ORDER BY f.fuente, f.edicion, f.idFuente",
                Object[].class, filtros).getResultList();
    }

    public <T> List<T> distintos(String campo, Class<T> tipo, Map<String, Object> filtros) {
        if (!CAMPOS.contains(campo)) throw new IllegalArgumentException("Campo no permitido");
        String jpql = "SELECT DISTINCT v." + campo + FROM + condiciones(filtros)
                + " AND v." + campo + " IS NOT NULL";
        if (tipo == String.class) jpql += " AND TRIM(v." + campo + ") <> ''";
        return consulta(jpql + " ORDER BY v." + campo, tipo, filtros).getResultList();
    }
}
