package mx.org.inegi.sistemacaptura;

import java.lang.reflect.Method;
import mx.org.inegi.sistemacaptura.service.import_excel.import_excel_service;

/** Regression check for MDEA estadistica2 values in the import template. */
public class ImportEstadistica2Check {
    public static void main(String[] args) throws Exception {
        Method convertir = import_excel_service.class.getDeclaredMethod(
                "buildNumberCode", String.class, String.class);
        convertir.setAccessible(true);
        import_excel_service importador = new import_excel_service();

        check("-", convertir.invoke(importador, "254d", "No cuenta con estadístico"));
        check("-", convertir.invoke(importador, "254d", " no cuenta con estadistico "));
        check("-", convertir.invoke(importador, "254d", "N"));
        check("-", convertir.invoke(importador, "254d", "N. No cuenta con estadístico"));
        check("-", convertir.invoke(importador, "254d", "no cuenta con estadistico"));
        check("-", convertir.invoke(importador, "254d", "no tiene"));
        check("-", convertir.invoke(importador, "254d", "No tiene estadístico"));
        check("-", convertir.invoke(importador, "254d", "na"));
        check("-", convertir.invoke(importador, "254d", "N/A"));
        check("-", convertir.invoke(importador, "254d", "no asignado"));
        check("254d2", convertir.invoke(importador, "254d", "2. Número de eventos"));
        check("-", convertir.invoke(importador, "254d", "-"));
        System.out.println("PASS estadistica2 import normalization");
    }

    private static void check(String esperado, Object actual) {
        if (!esperado.equals(actual)) {
            throw new AssertionError("Expected " + esperado + " but got " + actual);
        }
    }
}
