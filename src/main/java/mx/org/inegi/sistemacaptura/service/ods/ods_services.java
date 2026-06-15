/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.ods;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_indicador_enty;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_meta_enty;
import mx.org.inegi.sistemacaptura.entity.ods.catalogo.cat_objetivo_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_enty;
import mx.org.inegi.sistemacaptura.entity.ods.produccion.ods_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_indicador_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_meta_repo;
import mx.org.inegi.sistemacaptura.repository.ods.catalogo.cat_objetivo_repo;
import mx.org.inegi.sistemacaptura.repository.ods.produccion.ods_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ods_services {

    @Autowired
    private ods_repo repository;

    @Autowired
    private variables_repo variablesRepository;

    @Autowired
    private cat_objetivo_repo objetivoRepository;

    @Autowired
    private cat_meta_repo metaRepository;

    @Autowired
    private cat_indicador_repo indicadorRepository;

    @Transactional
    public ods_enty save(ods_enty relacion) {
        if (relacion == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La relacion ODS es obligatoria");
        }

        if (isBlank(relacion.getIdA())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta idA");
        }

        if (isBlank(relacion.getIdS())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta idS");
        }

        if (isBlank(relacion.getObjetivo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar un objetivo");
        }

        boolean exists = repository.existsByIdAAndObjetivoAndMetaAndIndicador(
                relacion.getIdA(),
                relacion.getObjetivo(),
                relacion.getMeta(),
                relacion.getIndicador());

        if (exists) {
            throw new IllegalArgumentException("Ya existe esta relacion ODS para esta variable.");
        }

        ods_enty nueva = repository.save(relacion);

        variables_enty variable = variablesRepository.findById(relacion.getIdA())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        variable.setOds(true);
        variablesRepository.save(variable);

        return nueva;
    }

    public List<ods_enty> getByIdA(String idA) {
        return repository.findByIdA(idA);
    }

    @Transactional
    public void deleteById(Integer id) {
        ods_enty relacion = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Relacion ODS no encontrada"));

        String idA = relacion.getIdA();

        repository.deleteById(id);

        List<ods_enty> restantes = repository.findByIdA(idA);

        if (restantes.isEmpty()) {
            Optional<variables_enty> variableOpt = variablesRepository.findById(idA);
            if (variableOpt.isPresent()) {
                variables_enty variable = variableOpt.get();
                variable.setOds(false);
                variablesRepository.save(variable);
            }
        }
    }

    public List<ods_traduccion_dto> getTablaByIdA(String idA) {
        List<ods_enty> relaciones = repository.findByIdA(idA);
        List<ods_traduccion_dto> respuesta = new ArrayList<ods_traduccion_dto>();

        for (ods_enty relacion : relaciones) {
            respuesta.add(traducirRelacionOds(relacion));
        }

        return respuesta;
    }

    private ods_traduccion_dto traducirRelacionOds(ods_enty rel) {
        ods_traduccion_dto dto = new ods_traduccion_dto();

        dto.setIdUnique(rel.getIdUnique());
        dto.setIdA(rel.getIdA());
        dto.setIdS(rel.getIdS());
        dto.setContribucion(rel.getContribucion());
        dto.setComentarioS(rel.getComentarioS());

        traducirObjetivo(rel, dto);
        traducirMeta(rel, dto);
        traducirIndicador(rel, dto);

        return dto;
    }

    private void traducirObjetivo(ods_enty rel, ods_traduccion_dto dto) {
        if (rel.getObjetivo() == null || "-".equals(rel.getObjetivo())) {
            dto.setObjetivo("-");
            dto.setObjetivoNombre("-");
            return;
        }

        try {
            Integer idObjetivo = Integer.valueOf(rel.getObjetivo());
            Optional<cat_objetivo_enty> objetivoOpt =
                    objetivoRepository.findByIdObjetivo(idObjetivo);

            if (objetivoOpt.isPresent()) {
                cat_objetivo_enty objetivo = objetivoOpt.get();
                dto.setObjetivo(String.valueOf(objetivo.getIdObjetivo()));
                dto.setObjetivoNombre(objetivo.getObjetivo());
            } else {
                dto.setObjetivo(rel.getObjetivo());
                dto.setObjetivoNombre("-");
            }
        } catch (NumberFormatException e) {
            dto.setObjetivo(rel.getObjetivo());
            dto.setObjetivoNombre("-");
        }
    }

    private void traducirMeta(ods_enty rel, ods_traduccion_dto dto) {
        if (rel.getMeta() == null || "-".equals(rel.getMeta())) {
            dto.setMeta("-");
            dto.setMetaNombre("-");
            return;
        }

        Optional<cat_meta_enty> metaOpt = metaRepository.findByUniqueId(rel.getMeta());

        if (metaOpt.isPresent()) {
            cat_meta_enty meta = metaOpt.get();
            dto.setMeta(meta.getIdMeta());
            dto.setMetaNombre(meta.getMeta());
        } else {
            dto.setMeta(rel.getMeta());
            dto.setMetaNombre("-");
        }
    }

    private void traducirIndicador(ods_enty rel, ods_traduccion_dto dto) {
        if (rel.getIndicador() == null || "-".equals(rel.getIndicador())) {
            dto.setIndicador("-");
            dto.setIndicadorNombre("-");
            return;
        }

        Optional<cat_indicador_enty> indicadorOpt =
                indicadorRepository.findByUniqueId(rel.getIndicador());

        if (indicadorOpt.isPresent()) {
            cat_indicador_enty indicador = indicadorOpt.get();
            dto.setIndicador(String.valueOf(indicador.getIdIndicador()));
            dto.setIndicadorNombre(indicador.getIndicador());
        } else {
            dto.setIndicador(rel.getIndicador());
            dto.setIndicadorNombre("-");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}