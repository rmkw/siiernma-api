# Visor de variables

## API

- GET /api/armo/variables/visor
- GET /api/armo/variables/visor/filtros
- GET /api/armo/variables/{idA}/detalle (incluye clasificadores)

La consulta acepta unidad, acronimo, idFuente, variableA, anioReferencia,
tematica, tema1, subtema1, tema2, subtema2, clasificacion, tabulados,
datosabiertos, mdea, ods, validada, microdatos, page y size.

Los filtros omitidos no restringen resultados. Las banderas solo aceptan
true/false; false no incluye null. El nombre usa coincidencia parcial,
sin distinguir mayúsculas, conservando acentos y tratando % y _ como texto.
page empieza en 0. size admite 20, 50 y 100; por defecto 20.

La respuesta contiene content, page, size, totalElements y totalPages.
content incluye idA, variableA, acronimo, idFuente, fuente, edicion,
anioReferencia y validada. El orden es idA ascendente.

Los catálogos devuelven unidades, procesos, fuentes, anios, tematica,
tema1, subtema1, tema2 y subtema2. Se basan en valores registrados.
Las fuentes requieren acronimo. Las opciones temáticas y años se acotan
por unidad/proceso/fuente; cada subtema también por su tema.

La comparación del año convierte la columna a texto porque el esquema
actual usa text aunque la entidad existente declara Integer. No se cambia
el esquema ni el mapeo compartido.

## Comprobación de solo lectura

Después de ejecutar Maven package, desde la raíz del backend en CMD:

```bat
java -cp target/pruebas/test-classes;target/pruebas/classes;target/pruebas/siscapback/WEB-INF/lib/* mx.org.inegi.sistemacaptura.armonizacion.visor.VisorReadOnlyCheck src/main/resources/application.properties
```

Este programa se ejecuta explícitamente; no lo ejecuta Surefire. Utiliza
las credenciales locales sin imprimirlas, deshabilita cambios de esquema
y establece la conexión y transacción como solo lectura.
Comprueba paginación, orden, conteos, filtros, validaciones, relaciones del
detalle y EXPLAIN ANALYZE de las consultas de lectura. No crea datos.

Las pruebas del frontend se ejecutan desde su repositorio:

```bat
npm.cmd test -- --watch=false --browsers=ChromeHeadless --include=src/app/visor/**/*.spec.ts
```

## Verificación integrada

Actualizar el backend mediante el procedimiento habitual antes de probar
/varvisor: una instancia anterior responderá 404 al nuevo endpoint.
No se ha cambiado la URL de conexión ni se incluye un despliegue.

Con sesión iniciada, abrir /#/varvisor, combinar filtros, cambiar páginas
y abrir/cerrar el detalle. Comprobar también que Validación conserva su
comportamiento con el campo adicional clasificadores.

En la base revisada (3,457 variables), los índices existentes cubren el
orden por ID y la relación con fuentes. No se añadieron índices.
