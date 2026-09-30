package mx.org.inegi.sistemacaptura;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.persistence.Table;
import mx.org.inegi.sistemacaptura.config.DatabaseTables;
import mx.org.inegi.sistemacaptura.service.import_excel.import_excel_service;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;

/** Checks the actual import repository destinations without connecting to a database. */
public class ImportDestinationCheck {
    public static void main(String[] args) {
        boolean prod = "produccion".equals(DatabaseTables.AMBIENTE);
        int checked = 0;
        for (Field field : import_excel_service.class.getDeclaredFields()) {
            if (!Repository.class.isAssignableFrom(field.getType())) continue;
            Class<?> entity = new DefaultRepositoryMetadata(field.getType()).getDomainType();
            Table table = entity.getAnnotation(Table.class);
            if ("usuarioRepo".equals(field.getName())) {
                check("usuarios".equals(table.schema()) && "usuarios".equals(table.name()), "User destination");
            } else {
                check((prod ? "seleccion" : "public").equals(table.schema()), "Import schema: " + field.getName());
                check(prod ? !table.name().endsWith("_s") : table.name().endsWith("_s"), "Import table: " + field.getName());
            }
            for (Method method : field.getType().getMethods()) {
                Query query = method.getAnnotation(Query.class);
                if (query != null && query.nativeQuery()) {
                    check(query.value().contains(prod ? "armonizacion." : "public."), "Complementary query: " + method.getName());
                    check(!prod || !query.value().contains("public."), "Production native query references tests");
                }
            }
            checked++;
            System.out.println("PASS import " + field.getName() + " -> " + table.schema() + "." + table.name());
        }
        check(checked == 7, "Expected seven import repositories");
        System.out.println("PASS import destinations " + DatabaseTables.AMBIENTE);
    }

    private static void check(boolean ok, String message) {
        if (!ok) throw new AssertionError(message);
    }
}
