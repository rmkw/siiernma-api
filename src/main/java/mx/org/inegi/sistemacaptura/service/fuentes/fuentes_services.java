/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.ArrayList;
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_dto;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_enty;
import mx.org.inegi.sistemacaptura.entity.variables.variables_enty;
import mx.org.inegi.sistemacaptura.repository.fuentes.fuentes_repo;
import mx.org.inegi.sistemacaptura.repository.mdea.produccion.mdea_repo;
import mx.org.inegi.sistemacaptura.repository.ods.produccion.ods_repo;
import mx.org.inegi.sistemacaptura.repository.pertinencias.pertinencia_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class fuentes_services {
    @Autowired
    private fuentes_repo repo;

    @Autowired
    private variables_repo variableRepo;

    @Autowired
    private mdea_repo mdeaRepo;

    @Autowired
    private ods_repo odsRepo;

    @Autowired
    private pertinencia_repo pertinenciaRepo;

    public List<fuentes_dto> getByAcronimo(String acronimo) {
        if (isBlank(acronimo)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El acronimo es obligatorio");
        }

        List<fuentes_enty> fuentes = repo.findByAcronimoOrderByIdFuenteSeleccionDesc(acronimo.trim());
        List<fuentes_dto> respuesta = new ArrayList<fuentes_dto>();

        for (fuentes_enty f : fuentes) {
            Long totalVariables = variableRepo.countByIdFuente(f.getIdFuenteSeleccion());

            fuentes_dto dto = new fuentes_dto();
            dto.setIdFuenteSeleccion(f.getIdFuenteSeleccion());
            dto.setIdFuente(f.getIdFuente());
            dto.setAcronimo(f.getAcronimo());
            dto.setFuente(f.getFuente());
            dto.setUrl(f.getUrl());
            dto.setEdicion(f.getEdicion());
            dto.setComentarioS(f.getComentarioS());
            dto.setComentarioA(f.getComentarioA());
            dto.setTotalVariables(totalVariables);

            respuesta.add(dto);
        }

        return respuesta;
    }

    public fuentes_enty getByIdFuenteSeleccion(String idFuenteSeleccion) {
        if (isBlank(idFuenteSeleccion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idFuenteSeleccion es obligatorio");
        }

        return repo.findById(idFuenteSeleccion.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fuente no encontrada"));
    }

    @Transactional
    public fuentes_enty create(fuentes_enty fuente) {
        validarFuenteParaCrear(fuente);

        String idFuenteSeleccion = fuente.getIdFuenteSeleccion().trim();

        if (repo.existsById(idFuenteSeleccion)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una fuente con ese idFuenteSeleccion");
        }

        fuentes_enty nueva = new fuentes_enty();
        nueva.setIdFuenteSeleccion(idFuenteSeleccion);
        nueva.setAcronimo(fuente.getAcronimo().trim());
        nueva.setFuente(fuente.getFuente().trim());
        nueva.setUrl(limpiarObligatorio(fuente.getUrl()));
        nueva.setEdicion(limpiarObligatorio(fuente.getEdicion()));
        nueva.setComentarioS(limpiarNullable(fuente.getComentarioS()));
        nueva.setComentarioA(limpiarNullable(fuente.getComentarioA()));

        return repo.save(nueva);
    }

    @Transactional
    public fuentes_enty update(String idFuenteSeleccionActual, fuentes_enty nuevaFuente) {
        if (isBlank(idFuenteSeleccionActual)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccionActual es obligatorio");
        }

        fuentes_enty existente = repo.findById(idFuenteSeleccionActual.trim())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fuente no encontrada"));

        validarFuenteParaActualizar(nuevaFuente);

        existente.setAcronimo(nuevaFuente.getAcronimo().trim());
        existente.setFuente(nuevaFuente.getFuente().trim());
        existente.setUrl(limpiarObligatorio(nuevaFuente.getUrl()));
        existente.setEdicion(limpiarObligatorio(nuevaFuente.getEdicion()));
        existente.setComentarioS(limpiarNullable(nuevaFuente.getComentarioS()));
        existente.setComentarioA(limpiarNullable(nuevaFuente.getComentarioA()));

        return repo.save(existente);
    }

    @Transactional
    public void deleteByIdFuenteSeleccion(String idFuenteSeleccion) {
        if (isBlank(idFuenteSeleccion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccion es obligatorio");
        }

        String id = idFuenteSeleccion.trim();

        fuentes_enty fuente = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fuente no encontrada"));

        List<variables_enty> variables = variableRepo.findByIdFuente(id);

        for (variables_enty variable : variables) {
            String idA = variable.getIdA();

            pertinenciaRepo.deleteByIdA(idA);
            odsRepo.deleteByIdA(idA);
            mdeaRepo.deleteByIdA(idA);
        }

        variableRepo.deleteAll(variables);
        repo.delete(fuente);
    }

    public Long contarFuentes() {
        return repo.count();
    }

    private void validarFuenteParaCrear(fuentes_enty fuente) {
        if (fuente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fuente es obligatoria");
        }

        if (isBlank(fuente.getIdFuenteSeleccion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "idFuenteSeleccion es obligatorio");
        }

        validarCamposBase(fuente);
    }

    private void validarFuenteParaActualizar(fuentes_enty fuente) {
        if (fuente == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fuente es obligatoria");
        }

        validarCamposBase(fuente);
    }

    private void validarCamposBase(fuentes_enty fuente) {
        if (isBlank(fuente.getAcronimo())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El acronimo es obligatorio");
        }

        if (isBlank(fuente.getFuente())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fuente es obligatoria");
        }

        if (isBlank(fuente.getUrl())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La URL es obligatoria");
        }

        if (isBlank(fuente.getEdicion())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La edicion es obligatoria");
        }
    }

    private String limpiarObligatorio(String value) {
        return value == null ? "" : value.trim();
    }

    private String limpiarNullable(String value) {
        if (value == null) {
            return null;
        }

        String limpio = value.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
