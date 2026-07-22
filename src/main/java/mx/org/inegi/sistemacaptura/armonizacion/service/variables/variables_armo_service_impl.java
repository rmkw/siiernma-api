/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.variables;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_busqueda_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_detalle_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulado_detalle_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.service.clasificaciones.clasificaciones_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.desagregaciones.desagregaciones_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.datosabiertos.datos_abiertos_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.desgloses.desgloses_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.microdatos.microdatos_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.tabulados.tabulados_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.service.variables_tabulados.variables_tabulados_armo_service;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.produccion.mdea_repo;
import mx.org.inegi.sistemacaptura.repository.ods.produccion.ods_repo;
import mx.org.inegi.sistemacaptura.repository.pertinencias.pertinencia_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class variables_armo_service_impl implements variables_armo_service {

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Autowired
    private clasificaciones_armo_service clasificacionesService;
    @Autowired
    private microdatos_armo_service microdatosService;
    @Autowired
    private datos_abiertos_armo_service datosAbiertosService;
    @Autowired
    private variables_tabulados_armo_service variablesTabuladosService;
    @Autowired
    private tabulados_armo_service tabuladosService;
    @Autowired
    private desgloses_armo_service desglosesService;
    @Autowired
    private desagregaciones_armo_service desagregacionesService;
    @Autowired
    private mdea_repo mdeaRepository;
    @Autowired
    private ods_repo odsRepository;
    @Autowired
    private pertinencia_repo pertinenciaRepository;

    @Override
    public Optional<variables_armo_dto> obtenerPorIdA(String idA) {
        return variablesArmoRepo.findById(idA).map(this::convertirA_DTO);
    }

    @Override
    public variables_detalle_armo_dto obtenerDetallePorIdA(String idA) {
        variables_armo_dto variable = obtenerPorIdA(idA).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No existe la variable en armonizacion con id_a: " + idA));

        variables_detalle_armo_dto detalle = new variables_detalle_armo_dto();
        detalle.setVariable(variable);
        detalle.setClasificaciones(clasificacionesService.obtenerPorIdA(idA));
        detalle.setMicrodatos(microdatosService.obtenerPorIdA(idA));
        detalle.setDatosAbiertos(datosAbiertosService.obtenerPorIdA(idA));
        detalle.setTabulados(variablesTabuladosService.obtenerPorVariable(idA)
                .stream()
                .map(this::construirDetalleTabulado)
                .collect(Collectors.toList()));
        detalle.setMdeas(mdeaRepository.findArmonizacionByIdA(idA));
        detalle.setOdsList(odsRepository.findArmonizacionByIdA(idA));
        detalle.setPertinencia(
                pertinenciaRepository.findArmonizacionByIdA(idA).orElse(null));
        return detalle;
    }

    private tabulado_detalle_armo_dto construirDetalleTabulado(
            variables_tabulados_armo_dto relacion) {
        tabulado_detalle_armo_dto detalle = new tabulado_detalle_armo_dto();
        detalle.setIdUnique(relacion.getIdUnique());
        detalle.setIdA(relacion.getIdA());
        detalle.setIdTabulado(relacion.getIdTabulado());
        detalle.setComentarioRelacion(relacion.getComentarioA());
        detalle.setTabulado(tabuladosService.obtenerPorId(relacion.getIdTabulado())
                .orElse(null));
        detalle.setDesgloses(
                desglosesService.obtenerPorTabulado(relacion.getIdTabulado()));
        detalle.setDesagregaciones(desagregacionesService
                .obtenerPorTabulado(relacion.getIdTabulado()));
        return detalle;
    }

    @Override
    public variables_armo_dto actualizarValidacion(String idA, Boolean validada) {
        variables_armo_enty variable = variablesArmoRepo.findById(idA)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No existe la variable en armonizacion con id_a: " + idA));

        variable.setValidada(validada);
        return convertirA_DTO(variablesArmoRepo.save(variable));
    }

    @Override
    public List<variables_armo_dto> obtenerPorIdFuente(String idFuente) {
        return variablesArmoRepo.findByIdFuenteOrderByIdAAsc(idFuente)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<variables_busqueda_armo_dto> buscarPorIdONombre(
            String termino) {
        if (termino == null || termino.trim().length() < 2) {
            throw new RuntimeException(
                    "El término debe tener al menos 2 caracteres");
        }
        return variablesArmoRepo.buscarPorIdONombre(termino.trim())
                .stream()
                .map(variable -> new variables_busqueda_armo_dto(
                variable.getIdA(),
                variable.getVariableA(),
                variable.getVariableS()))
                .collect(Collectors.toList());
    }

    @Override
    public List<variables_busqueda_armo_dto> obtenerPorProceso(
            String acronimo) {
        if (acronimo == null || acronimo.trim().isEmpty()) {
            throw new RuntimeException("El acrónimo es obligatorio");
        }
        return variablesArmoRepo
                .findByAcronimoOrderByIdAAsc(acronimo.trim().toUpperCase())
                .stream()
                .map(variable -> new variables_busqueda_armo_dto(
                variable.getIdA(),
                variable.getVariableA(),
                variable.getVariableS()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorIdA(String idA) {
        return variablesArmoRepo.existsById(idA);
    }

    @Override
    public variables_armo_dto guardarVariable(variables_armo_dto dto) {
        if (dto.getValidada() == null) {
            dto.setValidada(false);
        }
        variables_armo_enty entity = convertirA_Entity(dto);
        variables_armo_enty guardada = variablesArmoRepo.save(entity);
        return convertirA_DTO(guardada);
    }

    @Override
    public variables_armo_dto actualizarVariable(String idA, variables_armo_dto dto) {
        variables_armo_enty existente = variablesArmoRepo.findById(idA)
                .orElseThrow(() -> new RuntimeException(
                        "La variable no existe en armonizacion con id_a: " + idA));

        existente.setIdFuente(dto.getIdFuente());
        existente.setAcronimo(dto.getAcronimo());
        existente.setIdS(dto.getIdS());
        existente.setVariableS(dto.getVariableS());
        existente.setVariableA(dto.getVariableA());
        existente.setUrl(dto.getUrl());
        existente.setPregunta(dto.getPregunta());
        existente.setDefinicion(dto.getDefinicion());
        existente.setUniverso(dto.getUniverso());
        existente.setAnioReferencia(dto.getAnioReferencia());
        existente.setTematica(dto.getTematica());
        existente.setTema1(dto.getTema1());
        existente.setSubtema1(dto.getSubtema1());
        existente.setTema2(dto.getTema2());
        existente.setSubtema2(dto.getSubtema2());
        existente.setTabulados(dto.getTabulados());
        existente.setClasificacion(dto.getClasificacion());
        existente.setMicrodatos(dto.getMicrodatos());
        existente.setDatosabiertos(dto.getDatosabiertos());
        existente.setMdea(dto.getMdea());
        existente.setOds(dto.getOds());
        existente.setComentarioS(dto.getComentarioS());
        existente.setComentarioA(dto.getComentarioA());

        variables_armo_enty actualizada = variablesArmoRepo.save(existente);
        return convertirA_DTO(actualizada);
    }

    @Override
    public void eliminarVariable(String idA) {
        if (!variablesArmoRepo.existsById(idA)) {
            throw new RuntimeException("No existe la variable con id_a: " + idA);
        }

        variablesArmoRepo.deleteById(idA);
    }

    @Override
    public Long contarVariablesArmonizadas() {
        return variablesArmoRepo.count();
    }

    private variables_armo_dto convertirA_DTO(variables_armo_enty entity) {
        return new variables_armo_dto(
                entity.getIdA(),
                entity.getIdFuente(),
                entity.getAcronimo(),
                entity.getIdS(),
                entity.getVariableS(),
                entity.getVariableA(),
                entity.getUrl(),
                entity.getPregunta(),
                entity.getDefinicion(),
                entity.getUniverso(),
                entity.getAnioReferencia(),
                entity.getTematica(),
                entity.getTema1(),
                entity.getSubtema1(),
                entity.getTema2(),
                entity.getSubtema2(),
                entity.getTabulados(),
                entity.getClasificacion(),
                entity.getMicrodatos(),
                entity.getDatosabiertos(),
                entity.getMdea(),
                entity.getOds(),
                entity.getComentarioS(),
                entity.getComentarioA(),
                entity.getValidada());
    }

    private variables_armo_enty convertirA_Entity(variables_armo_dto dto) {
        return new variables_armo_enty(
                dto.getIdA(),
                dto.getIdFuente(),
                dto.getAcronimo(),
                dto.getIdS(),
                dto.getVariableS(),
                dto.getVariableA(),
                dto.getUrl(),
                dto.getPregunta(),
                dto.getDefinicion(),
                dto.getUniverso(),
                dto.getAnioReferencia(),
                dto.getTematica(),
                dto.getTema1(),
                dto.getSubtema1(),
                dto.getTema2(),
                dto.getSubtema2(),
                dto.getTabulados(),
                dto.getClasificacion(),
                dto.getMicrodatos(),
                dto.getDatosabiertos(),
                dto.getMdea(),
                dto.getOds(),
                dto.getComentarioS(),
                dto.getComentarioA(),
                dto.getValidada());
    }
}
