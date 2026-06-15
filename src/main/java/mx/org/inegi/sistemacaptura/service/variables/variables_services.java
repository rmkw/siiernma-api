/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;
import mx.org.inegi.sistemacaptura.entity.variables.variable_revision_masiva_update_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variable_revision_prioridad_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variable_revision_update_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variable_tabla_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import mx.org.inegi.sistemacaptura.entity.variables.variables_relacion_dto;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_componente_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_estadistico1_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_estadistico2_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_subcomponente_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_tema_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.produccion.mdea_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_indicador_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_meta_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_objetivo_repo;
import mx.org.inegi.sistemacaptura.repository.ods.produccion.ods_repo;
import mx.org.inegi.sistemacaptura.repository.pertinencias.pertinencia_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class variables_services {

    @Autowired
    private variables_repo repository;

    @Autowired
    private mdea_repo mdeaRepository;

    @Autowired
    private ods_repo odsRepository;

    @Autowired
    private pertinencia_repo pertinenciaRepository;

    @Autowired
    private cat_componente_repo catComponenteRepository;

    @Autowired
    private cat_subcomponente_repo catSubcomponenteRepository;

    @Autowired
    private cat_tema_repo catTemaRepository;

    @Autowired
    private cat_estadistico1_repo catEstadistico1Repository;

    @Autowired
    private cat_estadistico2_repo catEstadistico2Repository;

    @Autowired
    private cat_objetivo_repo catObjetivoRepository;

    @Autowired
    private cat_meta_repo catMetaRepository;

    @Autowired
    private cat_indicador_repo catIndicadorRepository;

    public variables_enty crearVariable(variables_enty variable) {
        if (variable == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La variable es obligatoria");
        }

        if (repository.existsByIdA(variable.getIdA())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una variable registrada con ese idA");
        }

        if (repository.existsByIdSAndIdFuente(
                variable.getIdS(),
                variable.getIdFuente())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Ya existe una variable con ese id_s y esa fuente");
        }

        if (variable.getRevisada() == null) {
            variable.setRevisada(false);
        }

        if (variable.getPrioridad() == null) {
            variable.setPrioridad(null);
        }

        if (variable.getFechaRevision() == null) {
            variable.setFechaRevision(null);
        }

        if (variable.getResponsableRevision() == null) {
            variable.setResponsableRevision(null);
        }

        if (variable.getMdea() == null) {
            variable.setMdea(false);
        }

        if (variable.getOds() == null) {
            variable.setOds(false);
        }

        try {
            variables_enty nueva = repository.save(variable);
            System.out.println("Nuevo ID registrado: " + nueva.getIdA());
            return nueva;
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Conflicto al guardar la variable. Revisa duplicados, id_fuente, acronimo o campos obligatorios.",
                    e);
        }
    }

    public void deleteByIdA(String idA) {
        if (!repository.existsById(idA)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Variable no encontrada");
        }

        repository.deleteById(idA);
    }

    public List<variables_relacion_dto> getWithRelationsByIdS(String idS) {
        List<variables_enty> variables = repository.findByIdS(idS);
        List<variables_relacion_dto> respuesta =
                new ArrayList<variables_relacion_dto>();

        for (variables_enty var : variables) {
            variables_relacion_dto dto = new variables_relacion_dto();
            BeanUtils.copyProperties(var, dto);

            dto.setMdeas(mdeaRepository.findByIdA(var.getIdA()));
            dto.setOdsList(odsRepository.findByIdA(var.getIdA()));
            dto.setPertinencia(
                    pertinenciaRepository.findByIdA(var.getIdA()).orElse(null));

            respuesta.add(dto);
        }

        return respuesta;
    }

    @Transactional
    public Map<String, Object> deleteVariableAndCascade(String idA) {
        Optional<variables_enty> optionalVar = repository.findById(idA);

        if (!optionalVar.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Variable no encontrada");
        }

        pertinenciaRepository.deleteByIdA(idA);
        odsRepository.deleteByIdA(idA);
        mdeaRepository.deleteByIdA(idA);

        repository.deleteById(idA);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Variable y relaciones eliminadas correctamente");
        return response;
    }

    @Transactional
    public Map<String, Object> editarVariable(String idA, variables_relacion_dto dto) {
        variables_enty variable = repository.findById(idA)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        variable.setNombre(dto.getNombre());
        variable.setDefinicion(dto.getDefinicion());
        variable.setUrl(dto.getUrl());
        variable.setComentarioS(dto.getComentarioS());
        variable.setMdea(Boolean.TRUE.equals(dto.getMdea()));
        variable.setOds(Boolean.TRUE.equals(dto.getOds()));

        if (!Boolean.TRUE.equals(dto.getMdea())) {
            List<mdea_enty> relacionesMdea = mdeaRepository.findByIdA(idA);
            mdeaRepository.deleteAll(relacionesMdea);
        }

        if (!Boolean.TRUE.equals(dto.getOds())) {
            List<ods_enty> relacionesOds = odsRepository.findByIdA(idA);
            odsRepository.deleteAll(relacionesOds);
        }

        repository.save(variable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Variable actualizada correctamente");
        return response;
    }

    public variables_relacion_dto getWithRelationsByIdA(String idA) {
        Optional<variables_enty> variableOpt = repository.findById(idA);

        if (!variableOpt.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Variable no encontrada");
        }

        variables_enty variable = variableOpt.get();

        variables_relacion_dto dto = new variables_relacion_dto();
        dto.setIdA(variable.getIdA());
        dto.setIdS(variable.getIdS());
        dto.setIdFuente(variable.getIdFuente());
        dto.setAcronimo(variable.getAcronimo());
        dto.setNombre(variable.getNombre());
        dto.setDefinicion(variable.getDefinicion());
        dto.setUrl(variable.getUrl());
        dto.setComentarioS(variable.getComentarioS());
        dto.setMdea(variable.getMdea());
        dto.setOds(variable.getOds());
        dto.setPrioridad(variable.getPrioridad());
        dto.setRevisada(variable.getRevisada());
        dto.setFechaRevision(variable.getFechaRevision());
        dto.setResponsableRevision(variable.getResponsableRevision());
        dto.setMdeas(mdeaRepository.findByIdA(idA));
        dto.setOdsList(odsRepository.findByIdA(idA));
        dto.setPertinencia(
                pertinenciaRepository.findByIdA(idA).orElse(null));

        return dto;
    }

    private Integer parseInteger(String value) {
        try {
            if (value == null || value.trim().isEmpty() || "-".equals(value)) {
                return null;
            }

            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String normalizarTexto(String value) {
        if (value == null) {
            return null;
        }

        String limpio = value.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private mdea_traduccion_dto traducirMdea(mdea_enty mdea) {
        Integer componenteId = parseInteger(mdea.getComponente());
        Integer subcomponenteUniqueId = parseInteger(mdea.getSubcomponente());
        Integer temaUniqueId = parseInteger(mdea.getTema());
        String estadistica1UniqueId = normalizarTexto(mdea.getEstadistica1());
        String estadistica2UniqueId = normalizarTexto(mdea.getEstadistica2());

        String componenteNombre = null;
        if (componenteId != null) {
            componenteNombre = catComponenteRepository.findByIdComponente(componenteId)
                    .map(c -> c.getNombre())
                    .orElse(null);
        }

        String subcomponenteNombre = null;
        if (subcomponenteUniqueId != null) {
            subcomponenteNombre = catSubcomponenteRepository.findByUniqueId(subcomponenteUniqueId)
                    .map(s -> s.getNombre())
                    .orElse(null);
        }

        String temaNombre = null;
        if (temaUniqueId != null) {
            temaNombre = catTemaRepository.findByUniqueId(temaUniqueId)
                    .map(t -> t.getNombre())
                    .orElse(null);
        }

        String estadistica1Nombre = null;
        if (estadistica1UniqueId != null && !"-".equals(estadistica1UniqueId)) {
            estadistica1Nombre = catEstadistico1Repository.findByUniqueId(estadistica1UniqueId)
                    .map(e -> e.getNombre())
                    .orElse(null);
        }

        String estadistica2Nombre = null;
        if (estadistica2UniqueId != null && !"-".equals(estadistica2UniqueId)) {
            estadistica2Nombre = catEstadistico2Repository.findByUniqueId(estadistica2UniqueId)
                    .map(e -> e.getNombre())
                    .orElse(null);
        }

        mdea_traduccion_dto dto = new mdea_traduccion_dto();
        dto.setIdUnique(mdea.getIdUnique());
        dto.setIdA(mdea.getIdA());
        dto.setIdS(mdea.getIdS());
        dto.setComponente(mdea.getComponente());
        dto.setComponenteNombre(componenteNombre);
        dto.setSubcomponente(mdea.getSubcomponente());
        dto.setSubcomponenteNombre(subcomponenteNombre);
        dto.setTema(mdea.getTema());
        dto.setTemaNombre(temaNombre);
        dto.setEstadistica1(mdea.getEstadistica1());
        dto.setEstadistica1Nombre(estadistica1Nombre);
        dto.setEstadistica2(mdea.getEstadistica2());
        dto.setEstadistica2Nombre(estadistica2Nombre);
        dto.setContribucion(mdea.getContribucion());
        dto.setComentarioS(mdea.getComentarioS());

        return dto;
    }

    private ods_traduccion_dto traducirOds(ods_enty ods) {
        Integer objetivoId = parseInteger(ods.getObjetivo());
        String metaUniqueId = normalizarTexto(ods.getMeta());
        String indicadorUniqueId = normalizarTexto(ods.getIndicador());

        String objetivoNombre = null;
        if (objetivoId != null) {
            objetivoNombre = catObjetivoRepository.findByIdObjetivo(objetivoId)
                    .map(o -> o.getObjetivo())
                    .orElse(null);
        }

        String metaNombre = null;
        if (metaUniqueId != null && !"-".equals(metaUniqueId)) {
            metaNombre = catMetaRepository.findByUniqueId(metaUniqueId)
                    .map(m -> m.getMeta())
                    .orElse(null);
        }

        String indicadorNombre = null;
        if (indicadorUniqueId != null && !"-".equals(indicadorUniqueId)) {
            indicadorNombre = catIndicadorRepository.findByUniqueId(indicadorUniqueId)
                    .map(i -> i.getIndicador())
                    .orElse(null);
        }

        ods_traduccion_dto dto = new ods_traduccion_dto();
        dto.setIdUnique(ods.getIdUnique());
        dto.setIdA(ods.getIdA());
        dto.setIdS(ods.getIdS());
        dto.setObjetivo(ods.getObjetivo());
        dto.setObjetivoNombre(objetivoNombre);
        dto.setMeta(ods.getMeta());
        dto.setMetaNombre(metaNombre);
        dto.setIndicador(ods.getIndicador());
        dto.setIndicadorNombre(indicadorNombre);
        dto.setContribucion(ods.getContribucion());
        dto.setComentarioS(ods.getComentarioS());

        return dto;
    }

    public List<variable_revision_prioridad_dto> getVariablesByFuentes(
            List<String> idFuentes) {
        if (idFuentes == null || idFuentes.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar al menos un id_fuente");
        }

        List<variables_enty> variables =
                repository.findByIdFuenteInOrderByIdFuenteDescIdAAsc(idFuentes);

        List<variable_revision_prioridad_dto> respuesta =
                new ArrayList<variable_revision_prioridad_dto>();

        for (variables_enty var : variables) {
            variable_revision_prioridad_dto dto =
                    new variable_revision_prioridad_dto();

            dto.setIdA(var.getIdA());
            dto.setIdS(var.getIdS());
            dto.setIdFuente(var.getIdFuente());
            dto.setAcronimo(var.getAcronimo());
            dto.setNombre(var.getNombre());
            dto.setUrl(var.getUrl());
            dto.setDefinicion(var.getDefinicion());
            dto.setPrioridad(var.getPrioridad());
            dto.setRevisada(var.getRevisada());
            dto.setFechaRevision(var.getFechaRevision());
            dto.setResponsableRevision(var.getResponsableRevision());
            dto.setMdea(var.getMdea());
            dto.setOds(var.getOds());

            List<mdea_traduccion_dto> mdeas =
                    new ArrayList<mdea_traduccion_dto>();
            for (mdea_enty mdea : mdeaRepository.findByIdA(var.getIdA())) {
                mdeas.add(traducirMdea(mdea));
            }
            dto.setMdeas(mdeas);

            List<ods_traduccion_dto> odsList =
                    new ArrayList<ods_traduccion_dto>();
            for (ods_enty ods : odsRepository.findByIdA(var.getIdA())) {
                odsList.add(traducirOds(ods));
            }
            dto.setOdsList(odsList);

            dto.setPertinencia(
                    pertinenciaRepository.findByIdA(var.getIdA()).orElse(null));

            respuesta.add(dto);
        }

        return respuesta;
    }

    @Transactional
    public Map<String, Object> actualizarRevisionPrioridad(
            String idA,
            variable_revision_update_dto dto) {
        variables_enty variable = repository.findById(idA)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        variable.setPrioridad(dto.getPrioridad());
        variable.setRevisada(dto.getRevisada());
        variable.setResponsableRevision(dto.getResponsableRevision());
        variable.setFechaRevision(LocalDateTime.now());

        repository.save(variable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Revision de prioridad actualizada correctamente");
        response.put("idA", variable.getIdA());
        response.put("prioridad", variable.getPrioridad());
        response.put("revisada", variable.getRevisada());

        return response;
    }

    @Transactional
    public Map<String, Object> actualizarRevisionPrioridadMasiva(
            variable_revision_masiva_update_dto dto) {
        if (dto.getIdsA() == null || dto.getIdsA().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar al menos un idA");
        }

        if (dto.getPrioridad() == null
                || (dto.getPrioridad() != 1 && dto.getPrioridad() != 2)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La prioridad debe ser 1 o 2");
        }

        if (dto.getResponsableRevision() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar responsableRevision");
        }

        List<variables_enty> variables = repository.findAllById(dto.getIdsA());

        if (variables.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "No se encontraron variables para actualizar");
        }

        LocalDateTime ahora = LocalDateTime.now();

        for (variables_enty variable : variables) {
            variable.setPrioridad(dto.getPrioridad());
            variable.setRevisada(Boolean.TRUE.equals(dto.getRevisada()));
            variable.setResponsableRevision(dto.getResponsableRevision());
            variable.setFechaRevision(ahora);
        }

        repository.saveAll(variables);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Revision de prioridad masiva actualizada correctamente");
        response.put("totalActualizadas", variables.size());
        response.put("prioridad", dto.getPrioridad());
        response.put("revisada", dto.getRevisada());

        return response;
    }

    public List<variable_tabla_dto> getVariablesTablaByFuentes(
            List<String> idFuentes) {
        if (idFuentes == null || idFuentes.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar al menos un id_fuente");
        }

        List<variables_enty> variables =
                repository.findByIdFuenteInOrderByIdFuenteDescIdAAsc(idFuentes);

        return convertirVariablesTabla(variables);
    }

    public Long contarVariables() {
        return repository.count();
    }

    public Long contarVariablesPrioritarias() {
        return repository.countByPrioridad(1);
    }

    public List<variable_tabla_dto> getVariablesTablaByFuente(String idFuente) {
        if (idFuente == null || idFuente.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar un id_fuente");
        }

        List<variables_enty> variables = repository.findByIdFuente(idFuente);

        return convertirVariablesTabla(variables);
    }

    private List<variable_tabla_dto> convertirVariablesTabla(
            List<variables_enty> variables) {
        List<variable_tabla_dto> respuesta =
                new ArrayList<variable_tabla_dto>();

        for (variables_enty var : variables) {
            variable_tabla_dto dto = new variable_tabla_dto();

            dto.setIdA(var.getIdA());
            dto.setIdS(var.getIdS());
            dto.setIdFuente(var.getIdFuente());
            dto.setAcronimo(var.getAcronimo());
            dto.setNombre(var.getNombre());
            dto.setDefinicion(var.getDefinicion());
            dto.setUrl(var.getUrl());
            dto.setComentarioS(var.getComentarioS());
            dto.setMdea(var.getMdea());
            dto.setOds(var.getOds());
            dto.setPrioridad(var.getPrioridad());
            dto.setRevisada(var.getRevisada());
            dto.setFechaRevision(var.getFechaRevision());
            dto.setResponsableRevision(var.getResponsableRevision());

            respuesta.add(dto);
        }

        return respuesta;
    }

    @Transactional
    public Map<String, Object> editarVariableBasica(
            String idA,
            variables_enty dto) {
        variables_enty variable = repository.findById(idA)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        variable.setIdS(dto.getIdS());
        variable.setIdFuente(dto.getIdFuente());
        variable.setAcronimo(dto.getAcronimo());
        variable.setNombre(dto.getNombre());
        variable.setDefinicion(dto.getDefinicion());
        variable.setUrl(dto.getUrl());
        variable.setComentarioS(dto.getComentarioS());
        variable.setMdea(Boolean.TRUE.equals(dto.getMdea()));
        variable.setOds(Boolean.TRUE.equals(dto.getOds()));
        variable.setPrioridad(dto.getPrioridad());
        variable.setRevisada(Boolean.TRUE.equals(dto.getRevisada()));
        variable.setFechaRevision(dto.getFechaRevision());
        variable.setResponsableRevision(dto.getResponsableRevision());

        repository.save(variable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Variable actualizada correctamente");
        response.put("idA", variable.getIdA());

        return response;
    }

    @Transactional
    public Map<String, Object> deleteVariableBasica(String idA) {
        variables_enty variable = repository.findById(idA)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        repository.delete(variable);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Variable eliminada correctamente");
        response.put("idA", idA);

        return response;
    }
}