/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.mdea;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_componente_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_estadistico1_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_estadistico2_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_subcomponente_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.catalogo.cat_tema_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_enty;
import mx.org.inegi.sistemacaptura.entity.mdea.produccion.mdea_traduccion_dto;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_componente_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_estadistico1_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_estadistico2_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_subcomponente_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.catalogo.cat_tema_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.produccion.mdea_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class mdea_services {

    @Autowired
    private cat_componente_repo repo_comp;

    @Autowired
    private cat_subcomponente_repo repo_subc;

    @Autowired
    private cat_tema_repo repo_tema;

    @Autowired
    private cat_estadistico1_repo repo_est1;

    @Autowired
    private cat_estadistico2_repo repo_est2;

    @Autowired
    private mdea_repo repo_mdea;

    @Autowired
    private variables_repo repo_variables;

    public List<cat_componente_enty> obtenerTodos() {
        return repo_comp.findAll();
    }

    public List<cat_subcomponente_enty> getSubcomponentesByCompId(Integer idComponente) {
        return repo_subc.findByIdComponente(idComponente);
    }

    public List<cat_tema_enty> getTopicosByCompAndSub(
            Integer idComponente,
            Integer idSubcomponente) {
        return repo_tema.findByIdComponenteAndIdSubcomponenteOrderByUniqueId(
                idComponente,
                idSubcomponente);
    }

    public List<cat_estadistico1_enty> getVariablesByCompSubTop(
            Integer idComponente,
            Integer idSubcomponente,
            Integer idTema) {
        return repo_est1.findByIdComponenteAndIdSubcomponenteAndIdTema(
                idComponente,
                idSubcomponente,
                idTema);
    }

    public List<cat_estadistico2_enty> getEstadisticosByCompSubTopVar(
            Integer idComponente,
            Integer idSubcomponente,
            Integer idTema,
            String idEstadistico1) {
        return repo_est2.findByIdComponenteAndIdSubcomponenteAndIdTemaAndIdEstadistico1OrderByUniqueId(
                idComponente,
                idSubcomponente,
                idTema,
                idEstadistico1);
    }

    @Transactional
    public mdea_enty save(mdea_enty relation) {
        if (relation == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La relacion MDEA es obligatoria");
        }

        if (isBlank(relation.getIdA())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta idA");
        }

        if (isBlank(relation.getIdS())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Falta idS");
        }

        if (isBlank(relation.getComponente())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar un componente");
        }

        boolean exists = repo_mdea.existsByIdAAndComponenteAndSubcomponenteAndTemaAndEstadistica1AndEstadistica2(
                relation.getIdA(),
                relation.getComponente(),
                relation.getSubcomponente(),
                relation.getTema(),
                relation.getEstadistica1(),
                relation.getEstadistica2());

        if (exists) {
            throw new IllegalArgumentException("Ya existe una relacion con estos valores para esta variable.");
        }

        mdea_enty nueva = repo_mdea.save(relation);

        variables_enty variable = repo_variables.findById(relation.getIdA())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Variable no encontrada"));

        variable.setMdea(true);
        repo_variables.save(variable);

        return nueva;
    }

    public List<mdea_enty> getByIdA(String idA) {
        return repo_mdea.findByIdA(idA);
    }

    @Transactional
    public void deleteById(Integer idUnique) {
        mdea_enty relacion = repo_mdea.findById(idUnique)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Relacion MDEA no encontrada"));

        String idA = relacion.getIdA();

        repo_mdea.deleteById(idUnique);

        List<mdea_enty> restantes = repo_mdea.findByIdA(idA);

        if (restantes.isEmpty()) {
            Optional<variables_enty> variableOpt = repo_variables.findById(idA);
            if (variableOpt.isPresent()) {
                variables_enty variable = variableOpt.get();
                variable.setMdea(false);
                repo_variables.save(variable);
            }
        }
    }

    public List<mdea_traduccion_dto> getTablaByIdA(String idA) {
        List<mdea_enty> relaciones = repo_mdea.findByIdA(idA);
        if (relaciones.isEmpty()) {
            relaciones = repo_mdea.findArmonizacionByIdA(idA);
        }
        List<mdea_traduccion_dto> respuesta = new ArrayList<mdea_traduccion_dto>();

        for (mdea_enty relacion : relaciones) {
            respuesta.add(traducirRelacionMdea(relacion));
        }

        return respuesta;
    }

    private mdea_traduccion_dto traducirRelacionMdea(mdea_enty rel) {
        mdea_traduccion_dto dto = new mdea_traduccion_dto();

        dto.setIdUnique(rel.getIdUnique());
        dto.setIdA(rel.getIdA());
        dto.setIdS(rel.getIdS());
        dto.setContribucion(rel.getContribucion());
        dto.setComentarioS(rel.getComentarioS());

        traducirComponente(rel, dto);
        traducirSubcomponente(rel, dto);
        traducirTema(rel, dto);
        traducirEstadistica1(rel, dto);
        traducirEstadistica2(rel, dto);

        return dto;
    }

    private void traducirComponente(mdea_enty rel, mdea_traduccion_dto dto) {
        if (rel.getComponente() == null || "-".equals(rel.getComponente())) {
            dto.setComponente("-");
            dto.setComponenteNombre("-");
            return;
        }

        try {
            Integer idComponente = Integer.valueOf(rel.getComponente());
            Optional<cat_componente_enty> compOpt = repo_comp.findByIdComponente(idComponente);

            if (compOpt.isPresent()) {
                cat_componente_enty comp = compOpt.get();
                dto.setComponente(String.valueOf(comp.getIdComponente()));
                dto.setComponenteNombre(comp.getNombre());
            } else {
                dto.setComponente(rel.getComponente());
                dto.setComponenteNombre("-");
            }
        } catch (NumberFormatException e) {
            dto.setComponente(rel.getComponente());
            dto.setComponenteNombre("-");
        }
    }

    private void traducirSubcomponente(mdea_enty rel, mdea_traduccion_dto dto) {
        if (rel.getSubcomponente() == null || "-".equals(rel.getSubcomponente())) {
            dto.setSubcomponente("-");
            dto.setSubcomponenteNombre("-");
            return;
        }

        try {
            Integer uniqueId = Integer.valueOf(rel.getSubcomponente());
            Optional<cat_subcomponente_enty> subOpt = repo_subc.findByUniqueId(uniqueId);

            if (subOpt.isPresent()) {
                cat_subcomponente_enty sub = subOpt.get();
                dto.setSubcomponente(String.valueOf(sub.getIdSubcomponente()));
                dto.setSubcomponenteNombre(sub.getNombre());
            } else {
                dto.setSubcomponente(rel.getSubcomponente());
                dto.setSubcomponenteNombre("-");
            }
        } catch (NumberFormatException e) {
            dto.setSubcomponente(rel.getSubcomponente());
            dto.setSubcomponenteNombre("-");
        }
    }

    private void traducirTema(mdea_enty rel, mdea_traduccion_dto dto) {
        if (rel.getTema() == null || "-".equals(rel.getTema())) {
            dto.setTema("-");
            dto.setTemaNombre("-");
            return;
        }

        try {
            Integer uniqueId = Integer.valueOf(rel.getTema());
            Optional<cat_tema_enty> temaOpt = repo_tema.findByUniqueId(uniqueId);

            if (temaOpt.isPresent()) {
                cat_tema_enty tema = temaOpt.get();
                dto.setTema(String.valueOf(tema.getIdTema()));
                dto.setTemaNombre(tema.getNombre());
            } else {
                dto.setTema(rel.getTema());
                dto.setTemaNombre("-");
            }
        } catch (NumberFormatException e) {
            dto.setTema(rel.getTema());
            dto.setTemaNombre("-");
        }
    }

    private void traducirEstadistica1(mdea_enty rel, mdea_traduccion_dto dto) {
        if (rel.getEstadistica1() == null || "-".equals(rel.getEstadistica1())) {
            dto.setEstadistica1("-");
            dto.setEstadistica1Nombre("-");
            return;
        }

        Optional<cat_estadistico1_enty> estOpt = repo_est1.findByUniqueId(rel.getEstadistica1());

        if (estOpt.isPresent()) {
            cat_estadistico1_enty est = estOpt.get();
            dto.setEstadistica1(est.getIdEstadistico1());
            dto.setEstadistica1Nombre(est.getNombre());
        } else {
            dto.setEstadistica1(rel.getEstadistica1());
            dto.setEstadistica1Nombre("-");
        }
    }

    private void traducirEstadistica2(mdea_enty rel, mdea_traduccion_dto dto) {
        if (rel.getEstadistica2() == null || "-".equals(rel.getEstadistica2())) {
            dto.setEstadistica2("-");
            dto.setEstadistica2Nombre("-");
            return;
        }

        Optional<cat_estadistico2_enty> estOpt = repo_est2.findByUniqueId(rel.getEstadistica2());

        if (estOpt.isPresent()) {
            cat_estadistico2_enty est = estOpt.get();
            dto.setEstadistica2(String.valueOf(est.getIdEstadistico2()));
            dto.setEstadistica2Nombre(est.getNombre());
        } else {
            dto.setEstadistica2(rel.getEstadistica2());
            dto.setEstadistica2Nombre("-");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
