# Perfiles de despliegue

`mvn -Ppruebas package` (predeterminado) genera `target/pruebas/siscapback.war`.
`mvn -Pproduccion package` genera `target/produccion/prodback.war`.
No activar ambos perfiles a la vez. En NetBeans ejecutar `package` con el perfil
correspondiente; no es necesario renombrar el proyecto.

Maven genera `DatabaseTables` desde `src/main/table-templates` con las propiedades
del perfil. Las entidades JPA y consultas nativas usan las mismas constantes.
No editar los archivos generados dentro de target. Los directorios de salida
son independientes, incluso si se alternan perfiles sin clean.

Producción mapea selección a `seleccion` sin `_s`, armonización a `armonizacion`
sin `_a` y tickets a `usuarios.tickets`. Usuarios y catálogos no cambian.
La importación conserva encabezados, validaciones y columnas; sus repositorios
apuntan a selección. Las consultas complementarias apuntan a armonización.
`application.properties` conserva la conexión y `hibernate.hbm2ddl.auto=none`.

El contexto y `sessionCookiePath` del WAR son `/prodback` o `/siscapback`.
El pool revisa todas sus conexiones disponibles cada minuto y cierra las que
lleven más de cinco minutos sin usarse. Puede quedar en cero conexiones;
mantiene el máximo de diez por instancia y no cierra conexiones prestadas.
Este cambio requiere reemplazar ambos WAR de backend desplegados.
Desplegar conservando el nombre del archivo. No cambiar la configuración global
de Tomcat ni el contexto de la otra aplicación.

## Comprobaciones explícitas (CMD)

Después de compilar producción:

```bat
java -cp target/produccion/test-classes;target/produccion/classes;target/produccion/prodback/WEB-INF/lib/* mx.org.inegi.sistemacaptura.DeploymentReadOnlyCheck src/main/resources/application.properties
java -cp target/produccion/test-classes;target/produccion/classes;target/produccion/prodback/WEB-INF/lib/* mx.org.inegi.sistemacaptura.armonizacion.visor.VisorReadOnlyCheck src/main/resources/application.properties
java -cp target/produccion/test-classes;target/produccion/classes;target/produccion/prodback/WEB-INF/lib/* mx.org.inegi.sistemacaptura.ImportDestinationCheck
java -cp target/produccion/test-classes;target/produccion/classes;target/produccion/prodback/WEB-INF/lib/* mx.org.inegi.sistemacaptura.armonizacion.clasificaciones.ClasificacionFlagCheck
```

Para pruebas, sustituir `produccion` por `pruebas` y `prodback` por `siscapback`
en el classpath. Las dos primeras comprobaciones acceden a la base con conexión
y transacción de solo lectura. Las otras son aisladas y no acceden a la base.
Estos programas se ejecutan explícitamente, no durante `mvn package`.

No copiar las credenciales al frontend ni imprimirlas. No ejecutar migraciones
o importaciones para verificar el despliegue. La entrega no cambia datos.
