package mx.org.inegi.sistemacaptura.armonizacion.clasificaciones;

import java.lang.reflect.*;
import java.util.*;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.*;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.*;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.*;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.microdatos.microdatos_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.clasificaciones.clasificaciones_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.clasificadores.clasificadores_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.service.variables.variables_armo_service_impl;
import mx.org.inegi.sistemacaptura.armonizacion.service.clasificaciones.clasificaciones_armo_service_impl;
import mx.org.inegi.sistemacaptura.armonizacion.service.clasificadores.clasificadores_armo_service_impl;

/** Service regression checks using in-memory repositories; never connects to a database. */
public class ClasificacionFlagCheck {
    private static final Map<Integer, Object> clases = new LinkedHashMap<>();
    private static final Map<Integer, Object> clasificadores = new LinkedHashMap<>();
    private static final variables_armo_enty variable = new variables_armo_enty();

    private static void inject(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static boolean tieneRelaciones() {
        return !clases.isEmpty() || !clasificadores.isEmpty();
    }

    @SuppressWarnings("unchecked")
    private static <T> T proxy(Class<T> type, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] { type }, handler);
    }

    private static Object relacion(Map<Integer, Object> data, Method method, Object[] args) throws Exception {
        switch (method.getName()) {
            case "save":
                Object entity = args[0];
                Integer id = (Integer) entity.getClass().getMethod("getIdUnique").invoke(entity);
                if (id == null) {
                    id = data.size() + 1;
                    entity.getClass().getMethod("setIdUnique", Integer.class).invoke(entity, id);
                }
                data.put(id, entity);
                return entity;
            case "findById": return Optional.ofNullable(data.get(args[0]));
            case "delete":
                data.remove(args[0].getClass().getMethod("getIdUnique").invoke(args[0]));
                return null;
            default: throw new AssertionError("Unexpected relation call: " + method);
        }
    }

    private static void check(boolean expected, String scenario) {
        if (!Boolean.valueOf(expected).equals(variable.getClasificacion())) throw new AssertionError(scenario);
    }

    public static void main(String[] args) throws Exception {
        variable.setIdA("TEST-001");
        variable.setClasificacion(false);
        variables_armo_repo variablesRepo = proxy(variables_armo_repo.class, (p, m, a) -> {
            switch (m.getName()) {
                case "existsById": return true;
                case "findById": return Optional.of(variable);
                case "tieneClasificacionOClasificador": return tieneRelaciones();
                case "sincronizarClasificacion": variable.setClasificacion(tieneRelaciones()); return 1;
                case "save": return a[0];
                default: throw new AssertionError("Unexpected variable call: " + m);
            }
        });
        clasificaciones_armo_service_impl clasesService = new clasificaciones_armo_service_impl();
        inject(clasesService, "variablesArmoRepo", variablesRepo);
        inject(clasesService, "clasificacionesArmoRepo",
                proxy(clasificaciones_armo_repo.class, (p, m, a) -> relacion(clases, m, a)));
        clasificadores_armo_service_impl clasificadoresService = new clasificadores_armo_service_impl();
        inject(clasificadoresService, "variablesArmoRepo", variablesRepo);
        inject(clasificadoresService, "clasificadoresArmoRepo",
                proxy(clasificadores_armo_repo.class, (p, m, a) -> relacion(clasificadores, m, a)));

        clasificaciones_armo_dto clase = clasesService.guardarClasificacion(
                new clasificaciones_armo_dto(null, "TEST-001", "Clase", "-"));
        check(true, "One classification is enough");
        clasesService.eliminarClasificacion(clase.getIdUnique());
        check(false, "No relations");
        clasificadores_armo_dto clasificador = clasificadoresService.guardarClasificador(
                new clasificadores_armo_dto(null, "TEST-001", "Clasificador", "2026", "-", "-"));
        check(true, "One classifier is enough");
        clase = clasesService.guardarClasificacion(new clasificaciones_armo_dto(null, "TEST-001", "Clase", "-"));
        clasesService.eliminarClasificacion(clase.getIdUnique());
        check(true, "Deleting classification preserves classifier");
        clase = clasesService.guardarClasificacion(new clasificaciones_armo_dto(null, "TEST-001", "Clase", "-"));
        clasificadoresService.eliminarClasificador(clasificador.getIdUnique());
        check(true, "Deleting classifier preserves classification");
        clasesService.eliminarClasificacion(clase.getIdUnique());
        check(false, "Deleting last relation clears flag");
        clasificador = clasificadoresService.guardarClasificador(
                new clasificadores_armo_dto(null, "TEST-001", "Clasificador", "2026", "-", "-"));
        variable.setClasificacion(false);
        clasificadoresService.actualizarClasificador(clasificador.getIdUnique(), clasificador);
        check(true, "Editing classifier repairs stale flag");

        variables_armo_service_impl variablesService = new variables_armo_service_impl();
        inject(variablesService, "variablesArmoRepo", variablesRepo);
        inject(variablesService, "microdatosRepo", proxy(microdatos_armo_repo.class, (p, m, a) -> false));
        variables_armo_dto dto = new variables_armo_dto();
        dto.setClasificacion(false);
        variablesService.actualizarVariable("TEST-001", dto);
        check(true, "Stale form cannot clear flag");
        clasificadoresService.eliminarClasificador(clasificador.getIdUnique());
        dto.setClasificacion(true);
        variablesService.actualizarVariable("TEST-001", dto);
        check(false, "Form cannot enable flag without relations");
        System.out.println("PASS: 9 classification/classifier flag scenarios; no database writes");
    }
}
