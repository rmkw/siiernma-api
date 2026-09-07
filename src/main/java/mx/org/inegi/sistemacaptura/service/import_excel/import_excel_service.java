/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author LUIS.CASTANEDAL
 */

package mx.org.inegi.sistemacaptura.service.import_excel;

import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import mx.org.inegi.sistemacaptura.entity.comentarios_pp.comentarios_pp_seleccion_enty;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_enty;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_error_fila_dto;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_import_result_dto;
import mx.org.inegi.sistemacaptura.entity.import_excel.import_excel_validacion_dto;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import mx.org.inegi.sistemacaptura.repository.comentarios_pp.comentarios_pp_seleccion_repo;
import mx.org.inegi.sistemacaptura.repository.fuentes.fuentes_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.produccion.mdea_repo;
import mx.org.inegi.sistemacaptura.repository.ods.produccion.ods_repo;
import mx.org.inegi.sistemacaptura.repository.pertinencias.pertinencia_repo;

import mx.org.inegi.sistemacaptura.repository.usuario.usuario_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

@Service
public class import_excel_service {

    @Autowired
    private variables_repo variablesRepo;

    @Autowired
    private fuentes_repo fuentesRepo;

    @Autowired
    private mdea_repo mdeaRepo;

    @Autowired
    private ods_repo odsRepo;

    @Autowired
    private pertinencia_repo pertinenciaRepo;

    @Autowired
    private usuario_repo usuarioRepo;

    @Autowired
    private comentarios_pp_seleccion_repo comentariosPpRepo;

    private static final List<String> REQUIRED_HEADERS = Arrays.asList(
            "proceso",
            "acronimo",
            "metodo",
            "comentario_sProceso",
            "fuente",
            "url",
            "urlVariable",
            "edicion",
            "comentario_sFuente",
            "id_s",
            "nombre",
            "definicion",
            "comentario_sVariable",
            "mdea",
            "componente",
            "subcomponente",
            "tema",
            "estadistica1",
            "estadistica2",
            "contribucionMdea",
            "comentario_sMdea",
            "ods",
            "objetivo",
            "meta",
            "indicador",
            "contribucionOds",
            "comentario_sOds",
            "pertinencia",
            "contribucionPertinencia",
            "viabilidad",
            "propuesta",
            "comentario_sPertinencia");

    private Integer getResponsableIdFromSession(Principal principal) {
        if (principal == null || isBlank(principal.getName())) {
            throw new RuntimeException("No se pudo identificar al usuario de la sesion.");
        }

        String username = principal.getName();
        usuario_enty user = usuarioRepo.findByNombre(username);

        if (user == null) {
            throw new RuntimeException("Usuario de sesion no existe en BD: " + username);
        }

        return Math.toIntExact(user.getId());
    }

    public import_excel_validacion_dto validar(MultipartFile file) {
        List<import_excel_error_fila_dto> errors =
                new ArrayList<import_excel_error_fila_dto>();

        if (file == null || file.isEmpty()) {
            errors.add(new import_excel_error_fila_dto(
                    0,
                    "-",
                    "No se recibio archivo o viene vacio."));
            return import_excel_validacion_dto.fail("Archivo vacio.", errors);
        }

        String originalName = file.getOriginalFilename();
        String fileName = originalName == null ? "" : originalName.toLowerCase();

        if (!fileName.endsWith(".xlsx")) {
            errors.add(new import_excel_error_fila_dto(
                    0,
                    "-",
                    "Formato invalido. El archivo debe ser .xlsx"));
            return import_excel_validacion_dto.fail("Formato invalido.", errors);
        }

        try (InputStream is = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getNumberOfSheets() > 0
                    ? workbook.getSheetAt(0)
                    : null;

            if (sheet == null) {
                errors.add(new import_excel_error_fila_dto(
                        0,
                        "-",
                        "El archivo Excel no contiene hojas."));
                return import_excel_validacion_dto.fail("Excel sin hojas.", errors);
            }

            Row headerRow = sheet.getRow(0);

            if (headerRow == null) {
                errors.add(new import_excel_error_fila_dto(
                        1,
                        "-",
                        "No se encontro la fila de encabezados."));
                return import_excel_validacion_dto.fail("Encabezados faltantes.", errors);
            }

            Map<String, Integer> headerIndex = buildHeaderIndex(headerRow);

            for (String required : REQUIRED_HEADERS) {
                if (!headerIndex.containsKey(normalize(required))) {
                    errors.add(new import_excel_error_fila_dto(
                            1,
                            required,
                            "Falta el encabezado requerido: " + required));
                }
            }

            if (!errors.isEmpty()) {
                return import_excel_validacion_dto.fail(
                        "La plantilla no coincide con el formato esperado.",
                        errors);
            }

            int filasConDatos = 0;
            Set<String> relacionesArchivo = new HashSet<String>();
            Set<String> idsARevisados = new HashSet<String>();

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);

                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                filasConDatos++;

                String idS = getByHeader(row, headerIndex, "id_s");
                String acronimo = getByHeader(row, headerIndex, "acronimo");
                String fuente = getByHeader(row, headerIndex, "fuente");
                String edicion = getByHeader(row, headerIndex, "edicion");
                String url = getByHeader(row, headerIndex, "url");
                String urlVariable = getByHeader(row, headerIndex, "urlVariable");
                String nombre = getByHeader(row, headerIndex, "nombre");

                for (String header : REQUIRED_HEADERS) {
                    if (isBlank(getByHeader(row, headerIndex, header))) {
                        errors.add(new import_excel_error_fila_dto(
                                r + 1,
                                header,
                                "Fila " + (r + 1) + ", columna " + header
                                + ": el campo esta vacio. Utiliza '-' cuando no aplique."));
                    }
                }

                if (!isBlank(edicion) && !edicion.matches("\\d{4}(/\\d{4})?")) {
                    addInvalidValueError(errors, r + 1, "edicion", edicion,
                            "utiliza un año como 2026 o un periodo como 2010/2011");
                }

                Boolean flagMdea = validateBooleanValue(
                        getByHeader(row, headerIndex, "mdea"), r + 1, "mdea", errors);
                Boolean flagOds = validateBooleanValue(
                        getByHeader(row, headerIndex, "ods"), r + 1, "ods", errors);

                validateContribution(row, headerIndex, r + 1, "contribucionMdea", errors);
                validateContribution(row, headerIndex, r + 1, "contribucionOds", errors);
                validateContribution(row, headerIndex, r + 1, "contribucionPertinencia", errors);

                validateUrl(url, r + 1, "url", errors);
                validateUrl(urlVariable, r + 1, "urlVariable", errors);

                if (!isBlank(idS) && !isBlank(acronimo)
                        && !idS.toLowerCase().startsWith(acronimo.toLowerCase() + "-")) {
                    addInvalidValueError(errors, r + 1, "id_s", idS,
                            "debe comenzar con el acronimo '" + acronimo + "-'");
                }

                if (!isBlank(idS) && !isBlank(edicion)) {
                    String idA = buildIdA(idS, edicion);

                    if (idsARevisados.add(normalize(idA))
                            && variablesRepo.existsByIdA(idA)) {
                        errors.add(new import_excel_error_fila_dto(
                                r + 1,
                                "id_s",
                                "Fila " + (r + 1) + ": la variable '" + idA
                                + "' ya esta registrada en la base de datos."));
                    }

                    if (flagMdea != null && flagOds != null) {
                        String relationKey = buildRelationKey(
                                row, headerIndex, idA, flagMdea, flagOds);

                        if (!relacionesArchivo.add(relationKey)) {
                            errors.add(new import_excel_error_fila_dto(
                                    r + 1,
                                    "id_s",
                                    "Fila " + (r + 1) + ": la variable '" + idA
                                    + "' repite exactamente la misma relacion MDEA y ODS."));
                        }
                    }
                }
            }

            if (filasConDatos == 0) {
                errors.add(new import_excel_error_fila_dto(
                        2,
                        "-",
                        "El archivo no contiene registros para importar. Pegue informacion desde la fila 2."));

                return import_excel_validacion_dto.fail("Archivo sin datos.", errors);
            }

            if (!errors.isEmpty()) {
                return import_excel_validacion_dto.fail(
                        "Se encontraron errores en el archivo.",
                        errors);
            }

            return import_excel_validacion_dto.ok("Archivo valido. Ya puedes importar.");

        } catch (Exception e) {
            errors.add(new import_excel_error_fila_dto(
                    0,
                    "-",
                    "No se pudo leer el archivo Excel: " + e.getMessage()));

            return import_excel_validacion_dto.fail("Error al leer el Excel.", errors);
        }
    }
    
        @Transactional
    public import_excel_import_result_dto importar(
            MultipartFile file,
            Principal principal) {
        int fuentesInsertadas = 0;
        int fuentesActualizadas = 0;
        int variablesInsertadas = 0;
        int variablesActualizadas = 0;
        int mdeaInsertados = 0;
        int mdeaOmitidos = 0;
        int odsInsertados = 0;
        int odsOmitidos = 0;
        int pertinenciaInsertadas = 0;
        int pertinenciaActualizadas = 0;

        List<import_excel_error_fila_dto> errors =
                new ArrayList<import_excel_error_fila_dto>();

        int filasTotales = 0;
        int filasConDatos = 0;
        int filasImportadas = 0;

        try {
            getResponsableIdFromSession(principal);
        } catch (RuntimeException ex) {
            errors.add(new import_excel_error_fila_dto(
                    0,
                    "-",
                    ex.getMessage()));

            return import_excel_import_result_dto.fail(
                    "No se pudo importar.",
                    0,
                    0,
                    0,
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0,
                    errors);
        }

        import_excel_validacion_dto validacion = validar(file);

        if (!validacion.isOk()) {
            return import_excel_import_result_dto.fail(
                    "No se importo porque el archivo tiene errores. Corrige y vuelve a intentar.",
                    0,
                    0,
                    0,
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0,
                    0, 0,
                    validacion.getErrors());
        }

        try (InputStream is = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getNumberOfSheets() > 0
                    ? workbook.getSheetAt(0)
                    : null;

            if (sheet == null) {
                errors.add(new import_excel_error_fila_dto(
                        0,
                        "-",
                        "El archivo Excel no contiene hojas."));

                return import_excel_import_result_dto.fail(
                        "Excel sin hojas.",
                        0,
                        0,
                        0,
                        0, 0,
                        0, 0,
                        0, 0,
                        0, 0,
                        0, 0,
                        errors);
            }

            Map<String, Integer> headerIndex =
                    buildHeaderIndex(sheet.getRow(0));
            filasTotales = sheet.getLastRowNum();

            Map<String, fuentes_enty> fuentesPorIdSeleccion =
                    new LinkedHashMap<String, fuentes_enty>();
            Map<String, variables_enty> variablesPorIdA =
                    new LinkedHashMap<String, variables_enty>();
            Map<String, pertinencia_enty> pertinenciaPorIdA =
                    new LinkedHashMap<String, pertinencia_enty>();

            Set<String> mdeaKeysArchivo = new HashSet<String>();
            Set<String> odsKeysArchivo = new HashSet<String>();

            Map<String, Integer> filaFuente = new HashMap<String, Integer>();
            Map<String, Integer> filaVariable = new HashMap<String, Integer>();

            Map<String, comentarios_pp_seleccion_enty> comentariosPorAcronimo =
                    new LinkedHashMap<String, comentarios_pp_seleccion_enty>();

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);

                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                filasConDatos++;

                String idS = getByHeader(row, headerIndex, "id_s");
                String acronimo = getByHeader(row, headerIndex, "acronimo");
                String comentarioSProceso =
                        getByHeader(row, headerIndex, "comentario_sProceso");
                String nombre = getByHeader(row, headerIndex, "nombre");
                String definicion = getByHeader(row, headerIndex, "definicion");
                String urlVariable = getByHeader(row, headerIndex, "urlVariable");
                String comentarioSVariable =
                        getByHeader(row, headerIndex, "comentario_sVariable");

                Boolean flagMdea = parseBoolean(
                        getByHeader(row, headerIndex, "mdea"));
                Boolean flagOds = parseBoolean(
                        getByHeader(row, headerIndex, "ods"));

                String fuenteNombre = getByHeader(row, headerIndex, "fuente");
                String urlFuente = getByHeader(row, headerIndex, "url");
                String edicion = getByHeader(row, headerIndex, "edicion");

                String idA = buildIdA(idS, edicion);
                String idFuenteSeleccion =
                        buildIdFuente(acronimo, fuenteNombre, edicion, urlFuente);

                if (isBlank(idS)
                        || isBlank(acronimo)
                        || isBlank(fuenteNombre)
                        || isBlank(edicion)
                        || isBlank(urlFuente)
                        || isBlank(nombre)) {
                    errors.add(new import_excel_error_fila_dto(
                            r + 1,
                            "-",
                            "Fila incompleta. Requiere id_s, acronimo y nombre."));

                    throw new ImportExcelAbortException(
                            "El archivo tiene filas incompletas. Se cancelo la importacion.",
                            errors);
                }

                if (!isBlank(comentarioSProceso)) {
                    comentarios_pp_seleccion_enty comentario =
                            new comentarios_pp_seleccion_enty();
                    comentario.setAcronimo(acronimo);
                    comentario.setComentarioS(comentarioSProceso);
                    comentariosPorAcronimo.put(acronimo, comentario);
                }

                String comentarioSFuente =
                        getByHeader(row, headerIndex, "comentario_sFuente");

                fuentes_enty fuente = fuentesPorIdSeleccion.get(idFuenteSeleccion);

                if (fuente == null) {
                    fuente = new fuentes_enty();
                    fuente.setIdFuenteSeleccion(idFuenteSeleccion);
                    fuentesPorIdSeleccion.put(idFuenteSeleccion, fuente);
                    filaFuente.put(idFuenteSeleccion, r + 1);
                }

                fuente.setAcronimo(acronimo);
                fuente.setFuente(isBlank(fuenteNombre) ? "SIN_FUENTE" : fuenteNombre);
                fuente.setUrl(isBlank(urlFuente) ? "" : urlFuente);
                fuente.setEdicion(isBlank(edicion) ? "" : edicion);
                fuente.setComentarioS(isBlank(comentarioSFuente) ? null : comentarioSFuente);

                variables_enty variable = variablesPorIdA.get(idA);

                if (variable == null) {
                    variable = new variables_enty();
                    variable.setIdA(idA);
                    variable.setRevisada(false);
                    variable.setPrioridad(1);
                    variable.setFechaRevision(null);
                    variable.setResponsableRevision(null);
                    variablesPorIdA.put(idA, variable);
                    filaVariable.put(idA, r + 1);
                }

                variable.setIdFuente(idFuenteSeleccion);
                variable.setIdS(idS);
                variable.setAcronimo(acronimo);
                variable.setNombre(nombre);
                variable.setDefinicion(isBlank(definicion) ? "" : definicion);
                variable.setUrl(isBlank(urlVariable) ? "" : urlVariable);
                variable.setComentarioS(isBlank(comentarioSVariable) ? "" : comentarioSVariable);
                variable.setMdea(Boolean.TRUE.equals(flagMdea));
                variable.setOds(Boolean.TRUE.equals(flagOds));

                String pertinenciaTxt =
                        getByHeader(row, headerIndex, "pertinencia");

                if (!isBlank(pertinenciaTxt)) {
                    String contribucionP =
                            getByHeader(row, headerIndex, "contribucionPertinencia");
                    String viabilidad =
                            getByHeader(row, headerIndex, "viabilidad");
                    String propuesta =
                            getByHeader(row, headerIndex, "propuesta");
                    String comentarioSP =
                            getByHeader(row, headerIndex, "comentario_sPertinencia");

                    pertinencia_enty pertinencia = pertinenciaPorIdA.get(idA);

                    if (pertinencia == null) {
                        pertinencia = new pertinencia_enty();
                        pertinencia.setIdA(idA);
                        pertinenciaPorIdA.put(idA, pertinencia);
                    }

                    pertinencia.setIdS(idS);
                    pertinencia.setPertinencia(pertinenciaTxt);
                    pertinencia.setContribucion(normalizeContribution(contribucionP));
                    pertinencia.setViabilidad(isBlank(viabilidad) ? "" : viabilidad);
                    pertinencia.setPropuesta(isBlank(propuesta) ? "" : propuesta);
                    pertinencia.setComentarioS(isBlank(comentarioSP) ? "" : comentarioSP);
                }

                if (Boolean.TRUE.equals(flagMdea)) {
                    String componente = extractTrailingNumber(
                            clean(blankToNull(getByHeader(row, headerIndex, "componente"))));
                    String subcomponente = extractTrailingCodeWithoutDots(
                            clean(blankToNull(getByHeader(row, headerIndex, "subcomponente"))));
                    String tema = extractTrailingCodeWithoutDots(
                            clean(blankToNull(getByHeader(row, headerIndex, "tema"))));
                    String estadistica1 = buildLetterCode(
                            tema,
                            clean(blankToNull(getByHeader(row, headerIndex, "estadistica1"))));
                    String estadistica2 = buildNumberCode(
                            estadistica1,
                            clean(blankToNull(getByHeader(row, headerIndex, "estadistica2"))));

                    String key = joinKey(
                            normalize(idA),
                            normalize(String.valueOf(componente)),
                            normalize(String.valueOf(subcomponente)),
                            normalize(String.valueOf(tema)),
                            normalize(String.valueOf(estadistica1)),
                            normalize(String.valueOf(estadistica2)));

                    if (!mdeaKeysArchivo.add(key)) {
                        mdeaOmitidos++;
                    }
                }

                if (Boolean.TRUE.equals(flagOds)) {
                    String objetivo = extractTrailingNumberOrDash(
                            clean(blankToNull(getByHeader(row, headerIndex, "objetivo"))));
                    String meta = extractTrailingCodeWithoutDotsOrDash(
                            clean(blankToNull(getByHeader(row, headerIndex, "meta"))));
                    String indicador = extractTrailingCodeWithoutDotsOrDash(
                            clean(blankToNull(getByHeader(row, headerIndex, "indicador"))));

                    String key = joinKey(
                            normalize(idA),
                            normalize(String.valueOf(objetivo)),
                            normalize(String.valueOf(meta)),
                            normalize(String.valueOf(indicador)));

                    if (!odsKeysArchivo.add(key)) {
                        odsOmitidos++;
                    }
                }

                filasImportadas++;
            }

            if (filasConDatos == 0) {
                errors.add(new import_excel_error_fila_dto(
                        2,
                        "-",
                        "El archivo no contiene registros para importar. Pegue informacion desde la fila 2."));

                return import_excel_import_result_dto.fail(
                        "Archivo sin datos.",
                        filasTotales,
                        0,
                        0,
                        0, 0,
                        0, 0,
                        0, 0,
                        0, 0,
                        0, 0,
                        errors);
            }
                        for (Map.Entry<String, comentarios_pp_seleccion_enty> entry
                    : comentariosPorAcronimo.entrySet()) {
                String acronimo = entry.getKey();
                comentarios_pp_seleccion_enty data = entry.getValue();

                try {
                    comentarios_pp_seleccion_enty existente =
                            comentariosPpRepo.findById(acronimo).orElse(null);

                    if (existente == null) {
                        comentariosPpRepo.save(data);
                    } else {
                        existente.setComentarioS(data.getComentarioS());
                        comentariosPpRepo.save(existente);
                    }

                } catch (Exception ex) {
                    Throwable root = getRootCause(ex);

                    errors.add(new import_excel_error_fila_dto(
                            0,
                            "comentario_sProceso",
                            "Error guardando comentario del proceso ("
                            + acronimo + "): " + root.getMessage()));

                    throw new ImportExcelAbortException(
                            "Error guardando COMENTARIOS_PP. Se cancelo la importacion.",
                            errors);
                }
            }

            for (Map.Entry<String, fuentes_enty> entry
                    : fuentesPorIdSeleccion.entrySet()) {
                String idFuenteSeleccion = entry.getKey();
                fuentes_enty data = entry.getValue();
                int fila = filaFuente.get(idFuenteSeleccion) == null
                        ? 0
                        : filaFuente.get(idFuenteSeleccion);

                try {
                    fuentes_enty existente =
                            fuentesRepo.findById(idFuenteSeleccion).orElse(null);

                    if (existente == null) {
                        fuentesRepo.save(data);
                        fuentesInsertadas++;
                    } else {
                        existente.setAcronimo(data.getAcronimo());
                        existente.setFuente(data.getFuente());
                        existente.setUrl(data.getUrl());
                        existente.setEdicion(data.getEdicion());
                        existente.setComentarioS(data.getComentarioS());

                        fuentesRepo.save(existente);
                        fuentesActualizadas++;
                    }

                } catch (Exception ex) {
                    Throwable root = getRootCause(ex);

                    errors.add(new import_excel_error_fila_dto(
                            fila,
                            "fuentes",
                            "Error guardando fuente (" + idFuenteSeleccion
                            + "): " + root.getMessage()));

                    throw new ImportExcelAbortException(
                            "Error guardando FUENTES. Se cancelo la importacion.",
                            errors);
                }
            }

            for (Map.Entry<String, variables_enty> entry
                    : variablesPorIdA.entrySet()) {
                String idA = entry.getKey();
                variables_enty data = entry.getValue();
                int fila = filaVariable.get(idA) == null
                        ? 0
                        : filaVariable.get(idA);

                try {
                    variables_enty existente =
                            variablesRepo.findById(idA).orElse(null);

                    if (existente == null) {
                        data.setRevisada(false);
                        data.setPrioridad(1);
                        data.setFechaRevision(null);
                        data.setResponsableRevision(null);

                        variablesRepo.save(data);
                        variablesInsertadas++;
                    } else {
                        errors.add(new import_excel_error_fila_dto(
                                fila,
                                "id_s",
                                "La variable '" + idA
                                + "' ya esta registrada en la base de datos."));
                        throw new ImportExcelAbortException(
                                "No se permiten actualizaciones de variables mediante importacion.",
                                errors);
                    }

                } catch (Exception ex) {
                    Throwable root = getRootCause(ex);

                    errors.add(new import_excel_error_fila_dto(
                            fila,
                            "variables",
                            "Error guardando variable (" + idA + "): "
                            + root.getMessage()));

                    throw new ImportExcelAbortException(
                            "Error guardando VARIABLES. Se cancelo la importacion.",
                            errors);
                }
            }

            for (Map.Entry<String, pertinencia_enty> entry
                    : pertinenciaPorIdA.entrySet()) {
                String idA = entry.getKey();
                pertinencia_enty data = entry.getValue();
                int fila = filaVariable.get(idA) == null
                        ? 0
                        : filaVariable.get(idA);

                try {
                    pertinencia_enty existente =
                            pertinenciaRepo.findByIdA(idA).orElse(null);

                    if (existente == null) {
                        data.setIdA(idA);
                        pertinenciaRepo.save(data);
                        pertinenciaInsertadas++;
                    } else {
                        existente.setIdS(data.getIdS());
                        existente.setPertinencia(data.getPertinencia());
                        existente.setContribucion(data.getContribucion());
                        existente.setViabilidad(data.getViabilidad());
                        existente.setPropuesta(data.getPropuesta());
                        existente.setComentarioS(data.getComentarioS());

                        pertinenciaRepo.save(existente);
                        pertinenciaActualizadas++;
                    }

                } catch (Exception ex) {
                    Throwable root = getRootCause(ex);

                    errors.add(new import_excel_error_fila_dto(
                            fila,
                            "pertinencia",
                            "Error guardando pertinencia (" + idA + "): "
                            + root.getMessage()));

                    throw new ImportExcelAbortException(
                            "Error guardando PERTINENCIA. Se cancelo la importacion.",
                            errors);
                }
            }

            Set<String> mdeaInsertadosArchivo = new HashSet<String>();
            Set<String> odsInsertadosArchivo = new HashSet<String>();

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);

                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                String idS = getByHeader(row, headerIndex, "id_s");
                String edicion = getByHeader(row, headerIndex, "edicion");
                String idA = buildIdA(idS, edicion);

                Boolean flagMdea = parseBoolean(
                        getByHeader(row, headerIndex, "mdea"));
                Boolean flagOds = parseBoolean(
                        getByHeader(row, headerIndex, "ods"));

                if (Boolean.TRUE.equals(flagMdea)) {
                    try {
                        String componente = extractTrailingNumber(
                                clean(blankToNull(getByHeader(row, headerIndex, "componente"))));
                        String subcomponente = extractTrailingCodeWithoutDots(
                                clean(blankToNull(getByHeader(row, headerIndex, "subcomponente"))));
                        String tema = extractTrailingCodeWithoutDots(
                                clean(blankToNull(getByHeader(row, headerIndex, "tema"))));
                        String estadistica1 = buildLetterCode(
                                tema,
                                clean(blankToNull(getByHeader(row, headerIndex, "estadistica1"))));
                        String estadistica2 = buildNumberCode(
                                estadistica1,
                                clean(blankToNull(getByHeader(row, headerIndex, "estadistica2"))));
                        String contribucionMdea = normalizeContribution(
                                blankToNull(getByHeader(row, headerIndex, "contribucionMdea")));
                        String comentarioSMdea = clean(
                                blankToNull(getByHeader(row, headerIndex, "comentario_sMdea")));

                        String key = joinKey(
                                normalize(idA),
                                normalize(String.valueOf(componente)),
                                normalize(String.valueOf(subcomponente)),
                                normalize(String.valueOf(tema)),
                                normalize(String.valueOf(estadistica1)),
                                normalize(String.valueOf(estadistica2)));

                        if (!mdeaInsertadosArchivo.add(key)) {
                            continue;
                        }

                        boolean existe = mdeaRepo
                                .existsByIdAAndComponenteAndSubcomponenteAndTemaAndEstadistica1AndEstadistica2(
                                        idA,
                                        componente,
                                        subcomponente,
                                        tema,
                                        estadistica1,
                                        estadistica2);

                        if (!existe) {
                            mdea_enty mdea = new mdea_enty();
                            mdea.setIdA(idA);
                            mdea.setIdS(idS);
                            mdea.setComponente(componente);
                            mdea.setSubcomponente(subcomponente);
                            mdea.setTema(tema);
                            mdea.setEstadistica1(estadistica1);
                            mdea.setEstadistica2(estadistica2);
                            mdea.setContribucion(contribucionMdea);
                            mdea.setComentarioS(comentarioSMdea);

                            mdeaRepo.save(mdea);
                            mdeaInsertados++;
                        } else {
                            mdeaOmitidos++;
                        }

                    } catch (Exception ex) {
                        Throwable root = getRootCause(ex);

                        errors.add(new import_excel_error_fila_dto(
                                r + 1,
                                "mdea",
                                "Error guardando MDEA: " + root.getMessage()));

                        throw new ImportExcelAbortException(
                                "Error guardando MDEA. Se cancelo la importacion.",
                                errors);
                    }
                }

                if (Boolean.TRUE.equals(flagOds)) {
                    try {
                        String objetivo = extractTrailingNumberOrDash(
                                clean(blankToNull(getByHeader(row, headerIndex, "objetivo"))));
                        String meta = extractTrailingCodeWithoutDotsOrDash(
                                clean(blankToNull(getByHeader(row, headerIndex, "meta"))));
                        String indicador = extractTrailingCodeWithoutDotsOrDash(
                                clean(blankToNull(getByHeader(row, headerIndex, "indicador"))));
                        String contribucionOds = normalizeContribution(
                                blankToNull(getByHeader(row, headerIndex, "contribucionOds")));
                        String comentarioSOds = clean(
                                blankToNull(getByHeader(row, headerIndex, "comentario_sOds")));

                        String key = joinKey(
                                normalize(idA),
                                normalize(String.valueOf(objetivo)),
                                normalize(String.valueOf(meta)),
                                normalize(String.valueOf(indicador)));

                        if (!odsInsertadosArchivo.add(key)) {
                            continue;
                        }

                        boolean existe =
                                odsRepo.existsByIdAAndObjetivoAndMetaAndIndicador(
                                        idA,
                                        objetivo,
                                        meta,
                                        indicador);

                        if (!existe) {
                            ods_enty ods = new ods_enty();
                            ods.setIdA(idA);
                            ods.setIdS(idS);
                            ods.setObjetivo(objetivo);
                            ods.setMeta(meta);
                            ods.setIndicador(indicador);
                            ods.setContribucion(contribucionOds);
                            ods.setComentarioS(comentarioSOds);

                            odsRepo.save(ods);
                            odsInsertados++;
                        } else {
                            odsOmitidos++;
                        }

                    } catch (Exception ex) {
                        Throwable root = getRootCause(ex);

                        errors.add(new import_excel_error_fila_dto(
                                r + 1,
                                "ods",
                                "Error guardando ODS: " + root.getMessage()));

                        throw new ImportExcelAbortException(
                                "Error guardando ODS. Se cancelo la importacion.",
                                errors);
                    }
                }
            }

            return import_excel_import_result_dto.ok(
                    "Importacion completada correctamente.",
                    filasTotales,
                    filasConDatos,
                    filasImportadas,
                    fuentesInsertadas,
                    fuentesActualizadas,
                    variablesInsertadas,
                    variablesActualizadas,
                    mdeaInsertados,
                    mdeaOmitidos,
                    odsInsertados,
                    odsOmitidos,
                    pertinenciaInsertadas,
                    pertinenciaActualizadas);

        } catch (ImportExcelAbortException abort) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            return import_excel_import_result_dto.fail(
                    abort.getMessage(),
                    filasTotales,
                    filasConDatos,
                    filasImportadas,
                    fuentesInsertadas,
                    fuentesActualizadas,
                    variablesInsertadas,
                    variablesActualizadas,
                    mdeaInsertados,
                    mdeaOmitidos,
                    odsInsertados,
                    odsOmitidos,
                    pertinenciaInsertadas,
                    pertinenciaActualizadas,
                    abort.getErrors());

        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

            Throwable root = getRootCause(e);

            errors.add(new import_excel_error_fila_dto(
                    0,
                    "-",
                    "Error al importar: " + root.getMessage()));

            return import_excel_import_result_dto.fail(
                    "Error al importar. Se cancelo la importacion.",
                    filasTotales,
                    filasConDatos,
                    filasImportadas,
                    fuentesInsertadas,
                    fuentesActualizadas,
                    variablesInsertadas,
                    variablesActualizadas,
                    mdeaInsertados,
                    mdeaOmitidos,
                    odsInsertados,
                    odsOmitidos,
                    pertinenciaInsertadas,
                    pertinenciaActualizadas,
                    errors);
        }
    }
        private String blankToNull(String s) {
        if (s == null) {
            return null;
        }

        String t = s.trim();
        return t.isEmpty() ? null : t;
    }

    private Boolean parseBoolean(String raw) {
        if (raw == null) {
            return null;
        }

        String v = raw.trim().toLowerCase();
        v = v.replace("í", "i");

        if (v.isEmpty()) {
            return null;
        }

        if (v.equals("true")
                || v.equals("t")
                || v.equals("1")
                || v.equals("si")
                || v.equals("s")
                || v.equals("x")
                || v.equals("verdadero")
                || v.equals("v")) {
            return true;
        }

        if (v.equals("false")
                || v.equals("f")
                || v.equals("0")
                || v.equals("no")
                || v.equals("n")
                || v.equals("falso")
                || v.equals("-")) {
            return false;
        }

        return null;
    }

    private Boolean validateBooleanValue(
            String value,
            int row,
            String column,
            List<import_excel_error_fila_dto> errors) {
        if (isBlank(value)) {
            return null;
        }

        Boolean parsed = parseBoolean(value);

        if (parsed == null) {
            addInvalidValueError(errors, row, column, value,
                    "utiliza Si, No o una variante permitida");
        }

        return parsed;
    }

    private void validateContribution(
            Row row,
            Map<String, Integer> headerIndex,
            int rowNumber,
            String column,
            List<import_excel_error_fila_dto> errors) {
        String value = getByHeader(row, headerIndex, column);

        if (!isBlank(value)
                && !value.equals("-")
                && !value.equalsIgnoreCase("alto")
                && !value.equalsIgnoreCase("intermedio")
                && !value.equalsIgnoreCase("bajo")) {
            addInvalidValueError(errors, rowNumber, column, value,
                    "los valores permitidos son Alto, Intermedio, Bajo o -");
        }
    }

    private void validateUrl(
            String value,
            int row,
            String column,
            List<import_excel_error_fila_dto> errors) {
        if (isBlank(value) || value.equals("-")) {
            return;
        }

        try {
            java.net.URI uri = new java.net.URI(value);
            String scheme = uri.getScheme();

            if (scheme == null
                    || (!scheme.equalsIgnoreCase("http") && !scheme.equalsIgnoreCase("https"))
                    || isBlank(uri.getHost())) {
                throw new IllegalArgumentException();
            }
        } catch (Exception ex) {
            addInvalidValueError(errors, row, column, value,
                    "utiliza una URL http://, https:// o -");
        }
    }

    private String buildRelationKey(
            Row row,
            Map<String, Integer> headerIndex,
            String idA,
            Boolean flagMdea,
            Boolean flagOds) {
        List<String> parts = new ArrayList<String>();
        parts.add(normalize(idA));
        parts.add(Boolean.TRUE.equals(flagMdea) ? "mdea:true" : "mdea:false");

        if (Boolean.TRUE.equals(flagMdea)) {
            parts.add(normalize(getByHeader(row, headerIndex, "componente")));
            parts.add(normalize(getByHeader(row, headerIndex, "subcomponente")));
            parts.add(normalize(getByHeader(row, headerIndex, "tema")));
            parts.add(normalize(getByHeader(row, headerIndex, "estadistica1")));
            parts.add(normalize(getByHeader(row, headerIndex, "estadistica2")));
        }

        parts.add(Boolean.TRUE.equals(flagOds) ? "ods:true" : "ods:false");

        if (Boolean.TRUE.equals(flagOds)) {
            parts.add(normalize(getByHeader(row, headerIndex, "objetivo")));
            parts.add(normalize(getByHeader(row, headerIndex, "meta")));
            parts.add(normalize(getByHeader(row, headerIndex, "indicador")));
        }

        return String.join("|", parts);
    }

    private void addInvalidValueError(
            List<import_excel_error_fila_dto> errors,
            int row,
            String column,
            String value,
            String reason) {
        errors.add(new import_excel_error_fila_dto(
                row,
                column,
                "Fila " + row + ", columna " + column + ": el valor '"
                + value + "' no es valido; " + reason + "."));
    }

    private String normalizeContribution(String value) {
        if (isBlank(value) || value.equals("-")) {
            return isBlank(value) ? "" : "-";
        }

        if (value.equalsIgnoreCase("alto")) {
            return "Alto";
        }

        if (value.equalsIgnoreCase("intermedio")) {
            return "Intermedio";
        }

        if (value.equalsIgnoreCase("bajo")) {
            return "Bajo";
        }

        return value.trim();
    }

    private Map<String, Integer> buildHeaderIndex(Row headerRow) {
        Map<String, Integer> map = new HashMap<String, Integer>();

        short last = headerRow.getLastCellNum();

        for (int i = 0; i < last; i++) {
            String header = normalize(getCellAsString(headerRow.getCell(i)));

            if (!header.trim().isEmpty()) {
                map.put(header, i);
            }
        }

        return map;
    }

    private String getByHeader(
            Row row,
            Map<String, Integer> index,
            String header) {
        Integer col = index.get(normalize(header));

        if (col == null) {
            return "";
        }

        return getCellAsString(row.getCell(col));
    }

    private boolean isRowEmpty(Row row) {
        short last = row.getLastCellNum();

        for (int i = 0; i < last; i++) {
            if (!isBlank(getCellAsString(row.getCell(i)))) {
                return false;
            }
        }

        return true;
    }

    private String getCellAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        CellType type = cell.getCellType();

        if (type == CellType.STRING) {
            return sanitizeCellText(cell.getStringCellValue());
        }

        if (type == CellType.NUMERIC) {
            double d = cell.getNumericCellValue();

            if (d == Math.rint(d)) {
                return String.valueOf((long) d);
            }

            return String.valueOf(d);
        }

        if (type == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }

        if (type == CellType.FORMULA) {
            try {
                return sanitizeCellText(cell.getStringCellValue());
            } catch (Exception ex) {
                return String.valueOf(cell.getNumericCellValue());
            }
        }

        return "";
    }

    private String sanitizeCellText(String value) {
        return value == null
                ? ""
                : value.replaceAll("_x000D_\\r?\\n?", "\n").trim();
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private String clean(String s) {
        if (s == null) {
            return null;
        }

        return s.trim();
    }

    private String extractTrailingNumber(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        int underscore = trimmed.lastIndexOf("_");

        if (underscore >= 0 && underscore < trimmed.length() - 1) {
            String suffix = trimmed.substring(underscore + 1).trim();

            if (suffix.matches("\\d+")) {
                return suffix;
            }
        }

        if (trimmed.matches("\\d+")) {
            return trimmed;
        }

        return trimmed;
    }

    private String buildLetterCode(String parentCode, String value) {
        if (value == null) {
            return "-";
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty() || trimmed.equals("-")) {
            return "-";
        }

        int dotIndex = trimmed.indexOf(".");

        if (dotIndex > 0) {
            String letter = trimmed.substring(0, dotIndex).trim();

            if (letter.matches("[a-zA-Z]")) {
                return isBlank(parentCode)
                        ? letter.toLowerCase()
                        : clean(parentCode) + letter.toLowerCase();
            }
        }

        if (trimmed.matches("[a-zA-Z]")) {
            return isBlank(parentCode)
                    ? trimmed.toLowerCase()
                    : clean(parentCode) + trimmed.toLowerCase();
        }

        return trimmed;
    }

    private String buildNumberCode(String parentCode, String value) {
        if (value == null) {
            return "-";
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty()
                || trimmed.equals("-")
                || trimmed.equalsIgnoreCase("No cuenta con estadístico")) {
            return "-";
        }

        int dotIndex = trimmed.indexOf(".");

        if (dotIndex > 0) {
            String number = trimmed.substring(0, dotIndex).trim();

            if (number.matches("\\d+")) {
                return isBlank(parentCode)
                        ? number
                        : clean(parentCode) + number;
            }
        }

        if (trimmed.matches("\\d+")) {
            return isBlank(parentCode)
                    ? trimmed
                    : clean(parentCode) + trimmed;
        }

        return trimmed;
    }

    private String buildIdFuente(
            String acronimo,
            String fuente,
            String edicion,
            String url) {
        return clean(acronimo) + "-"
                + clean(fuente) + "-"
                + clean(edicion) + "-"
                + clean(url);
    }

    private String buildIdA(String idS, String edicion) {
        return clean(idS) + "-" + clean(edicion);
    }

    private String extractTrailingNumberOrDash(String value) {
        if (value == null) {
            return "-";
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty() || trimmed.equals("-")) {
            return "-";
        }

        String code = extractTrailingNumericCode(trimmed);

        if (code != null && code.matches("\\d+")) {
            return code;
        }

        return trimmed;
    }

    private String extractTrailingNumericCode(String value) {
        String trimmed = value.trim();
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("(\\d+(?:\\.\\d+)*)\\s*$")
                .matcher(trimmed);

        return matcher.find() ? matcher.group(1) : null;
    }

    private String extractTrailingCodeWithoutDots(String value) {
        if (value == null) {
            return null;
        }

        String code = extractTrailingAlphanumericCode(value);

        if (code != null) {
            return code.replace(".", "").toLowerCase();
        }

        return value.trim();
    }

    private String extractTrailingCodeWithoutDotsOrDash(String value) {
        if (value == null) {
            return "-";
        }

        String trimmed = value.trim();

        if (trimmed.isEmpty() || trimmed.equals("-")) {
            return "-";
        }

        String code = extractTrailingAlphanumericCode(trimmed);

        if (code != null) {
            return code.replace(".", "").toLowerCase();
        }

        return trimmed;
    }

    private String extractTrailingAlphanumericCode(String value) {
        String trimmed = value.trim();

        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("(\\d+(?:\\.[a-zA-Z0-9]+)*)\\s*$")
                .matcher(trimmed);

        return matcher.find() ? matcher.group(1) : null;
    }

    private String joinKey(String a, String b, String c, String d) {
        return a + "|" + b + "|" + c + "|" + d;
    }

    private String joinKey(
            String a,
            String b,
            String c,
            String d,
            String e,
            String f) {
        return a + "|" + b + "|" + c + "|" + d + "|" + e + "|" + f;
    }

    private Throwable getRootCause(Throwable throwable) {
        Throwable root = throwable;

        while (root.getCause() != null) {
            root = root.getCause();
        }

        return root;
    }
}
