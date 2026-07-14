/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuente_save_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.fuentes.fuentes_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.fuentes.fuentes_armo_repository;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.repository.procesos.procesos_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class fuentes_armo_service {

    @Autowired
    private fuentes_armo_repository repository;

    @Autowired
    private procesos_repo procesosRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Transactional
    public fuentes_armo_enty createFuente(fuente_save_dto dto) {
        validarDto(dto);

        String acronimo = dto.getAcronimo().trim();
        validarAcronimoExisteEnProcesos(acronimo);

        String fuente = dto.getFuente().trim();
        String url = normalizarTexto(dto.getUrl());
        String edicion = normalizarTexto(dto.getEdicion());
        String comentarioS = normalizarTexto(dto.getComentarioS());
        String comentarioA = normalizarTexto(dto.getComentarioA());
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        String idFuente = construirIdFuente(acronimo, fuente, edicion, url);

        if (idFuenteSeleccion == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccion es obligatorio");
        }

        Optional<fuentes_armo_enty> fuentePorSeleccion =
                repository.findByIdFuenteSeleccion(idFuenteSeleccion);
        if (fuentePorSeleccion.isPresent()) {
            fuentes_armo_enty fuenteExistente = fuentePorSeleccion.get();
            if (idFuente.equals(fuenteExistente.getIdFuente())) {
                return fuenteExistente;
            }

            if (esIdFuenteSeleccionPorVariable(idFuenteSeleccion)) {
                Optional<fuentes_armo_enty> fuenteCanonica =
                        repository.findByIdFuente(idFuente);
                if (fuenteCanonica.isPresent()) {
                    fuentes_armo_enty fuenteReutilizada = fuenteCanonica.get();
                    fuenteReutilizada.setReutilizada(true);
                    return fuenteReutilizada;
                }

                repository.updateFuenteByIdFuenteSeleccion(
                        acronimo,
                        fuente,
                        url,
                        edicion,
                        comentarioS,
                        comentarioA,
                        idFuenteSeleccion);

                return repository.findByIdFuenteSeleccion(idFuenteSeleccion)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "La fuente se actualizo pero no pudo recuperarse"));
            }

            return fuenteExistente;
        }

        Optional<fuentes_armo_enty> fuenteCanonica =
                repository.findByIdFuente(idFuente);
        if (fuenteCanonica.isPresent()) {
            fuentes_armo_enty fuenteExistente = fuenteCanonica.get();
            fuenteExistente.setReutilizada(true);
            return fuenteExistente;
        }

        repository.insertFuente(
                acronimo,
                fuente,
                url,
                edicion,
                comentarioS,
                comentarioA,
                idFuenteSeleccion);

        return repository.findByIdFuenteSeleccion(idFuenteSeleccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "La fuente se inserto pero no pudo recuperarse"));
    }

    public Optional<fuentes_armo_enty> getFuenteById(String idFuente) {
        return repository.findByIdFuente(idFuente);
    }

    public boolean existsFuenteById(String idFuente) {
        return repository.existsByIdFuente(idFuente);
    }

    public boolean existsFuenteByIdFuenteSeleccion(String idFuenteSeleccion) {
        return repository.existsByIdFuenteSeleccion(idFuenteSeleccion);
    }

    public Optional<fuentes_armo_enty> getFuenteByIdFuenteSeleccion(
            String idFuenteSeleccion) {
        return repository.findByIdFuenteSeleccion(idFuenteSeleccion);
    }

    public List<fuentes_armo_enty> getFuentesByAcronimo(String acronimo) {
        String acronimoLimpio = normalizarTexto(acronimo);
        if (acronimoLimpio == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        return repository.findByAcronimoOrderByFuenteAsc(acronimoLimpio);
    }

    public long countVariablesByIdFuente(String idFuente) {
        String idFuenteLimpio = normalizarTexto(idFuente);
        if (idFuenteLimpio == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuente es obligatorio");
        }

        return variablesArmoRepo.countByIdFuente(idFuenteLimpio);
    }

    @Transactional
    public void deleteFuenteByIdFuente(String idFuente) {
        String idFuenteLimpio = normalizarTexto(idFuente);
        if (idFuenteLimpio == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuente es obligatorio");
        }

        fuentes_armo_enty fuente = repository.findByIdFuente(idFuenteLimpio)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Fuente no encontrada en armonizacion"));

        repository.delete(fuente);
    }

    @Transactional
    public fuentes_armo_enty updateFuente(fuente_save_dto dto) {
        validarDto(dto);

        String acronimo = dto.getAcronimo().trim();
        validarAcronimoExisteEnProcesos(acronimo);

        String fuente = dto.getFuente().trim();
        String url = normalizarTexto(dto.getUrl());
        String edicion = normalizarTexto(dto.getEdicion());
        String comentarioS = normalizarTexto(dto.getComentarioS());
        String comentarioA = normalizarTexto(dto.getComentarioA());
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());
        String idFuente = construirIdFuente(acronimo, fuente, edicion, url);

        if (idFuenteSeleccion == null || idFuenteSeleccion.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "idFuenteSeleccion es obligatorio para actualizar");
        }

        Optional<fuentes_armo_enty> fuenteCanonica =
                repository.findByIdFuente(idFuente);
        if (fuenteCanonica.isPresent()
                && !idFuenteSeleccion.equals(
                        fuenteCanonica.get().getIdFuenteSeleccion())) {
            fuentes_armo_enty fuenteExistente = fuenteCanonica.get();
            fuenteExistente.setReutilizada(true);
            return fuenteExistente;
        }

        int updated = repository.updateFuenteByIdFuenteSeleccion(
                acronimo,
                fuente,
                url,
                edicion,
                comentarioS,
                comentarioA,
                idFuenteSeleccion);

        if (updated == 0) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Fuente no encontrada en armonizacion");
        }

        return repository.findByIdFuenteSeleccion(idFuenteSeleccion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "La fuente se actualizo pero no pudo recuperarse"));
    }

    public String construirIdFuentePublic(fuente_save_dto dto) {
        validarDto(dto);

        return construirIdFuente(
                dto.getAcronimo(),
                dto.getFuente(),
                dto.getEdicion(),
                dto.getUrl());
    }

    public boolean existsFuenteByData(fuente_save_dto dto) {
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        if (idFuenteSeleccion != null) {
            return repository.existsByIdFuenteSeleccion(idFuenteSeleccion);
        }

        String idFuente = construirIdFuentePublic(dto);
        return repository.existsByIdFuente(idFuente);
    }

    public Optional<fuentes_armo_enty> getFuenteByData(fuente_save_dto dto) {
        String idFuenteSeleccion = normalizarTexto(dto.getIdFuenteSeleccion());

        if (idFuenteSeleccion != null) {
            Optional<fuentes_armo_enty> fuente =
                    repository.findByIdFuenteSeleccion(idFuenteSeleccion);
            if (fuente.isPresent()) {
                return fuente;
            }
        }

        String idFuente = construirIdFuentePublic(dto);
        return repository.findByIdFuente(idFuente);
    }

    private void validarDto(fuente_save_dto dto) {
        if (dto == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Debe proporcionar los datos de la fuente");
        }

        if (dto.getAcronimo() == null || dto.getAcronimo().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        if (dto.getFuente() == null || dto.getFuente().trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fuente es obligatoria");
        }
    }

    private String construirIdFuente(
            String acronimo,
            String fuente,
            String edicion,
            String url) {
        return String.format(
                "%s-%s-%s-%s",
                limpiarSegmento(acronimo),
                limpiarSegmento(fuente),
                limpiarSegmento(edicion),
                limpiarSegmento(url));
    }

    private String limpiarSegmento(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

    private String normalizarTexto(String valor) {
        if (valor == null) {
            return null;
        }

        String limpio = valor.trim();
        return limpio.isEmpty() ? null : limpio;
    }

    private boolean esIdFuenteSeleccionPorVariable(String idFuenteSeleccion) {
        return idFuenteSeleccion != null && idFuenteSeleccion.contains("::");
    }

    private void validarAcronimoExisteEnProcesos(String acronimo) {
        if (acronimo == null || acronimo.trim().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo es obligatorio");
        }

        String acronimoLimpio = acronimo.trim();

        if (!procesosRepo.existsById(acronimoLimpio)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El acronimo '" + acronimoLimpio
                    + "' no existe en la tabla de procesos de produccion");
        }
    }
}
