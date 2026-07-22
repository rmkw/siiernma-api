/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.microdatos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.microdatos.microdatos_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.microdatos.microdatos_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class microdatos_armo_service_impl implements microdatos_armo_service {

    @Autowired
    private microdatos_armo_repo microdatosArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public microdatos_armo_dto guardarMicrodato(microdatos_armo_dto dto) {
        validarMicrodato(dto);

        if (!variablesArmoRepo.existsById(dto.getIdA())) {
            throw new RuntimeException(
                    "No existe la variable en armonizacion con id_a: "
                    + dto.getIdA());
        }

        if (microdatosArmoRepo.contarDuplicados(
                dto.getIdA(),
                dto.getUrlAcceso(),
                dto.getDescriptor(),
                dto.getUrlDescriptor(),
                dto.getTabla(),
                dto.getCampo(),
                dto.getComentarioA()) > 0) {
            throw new RuntimeException(
                    "Este microdato ya está registrado para la variable. "
                    + "Modifica al menos uno de sus campos antes de "
                    + "intentarlo nuevamente.");
        }

        microdatos_armo_enty entity = convertirA_Entity(dto);
        entity.setIdUnique(null);

        microdatos_armo_enty guardado = microdatosArmoRepo.save(entity);

        return convertirA_DTO(guardado);
    }

    @Override
    public microdatos_armo_dto actualizarMicrodato(Integer idUnique,
            microdatos_armo_dto dto) {
        validarMicrodato(dto);
        microdatos_armo_enty existente = microdatosArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe el microdato con id_unique: " + idUnique));

        if (!existente.getIdA().equals(dto.getIdA())) {
            throw new RuntimeException("No es posible cambiar la variable del microdato");
        }

        if (microdatosArmoRepo.contarDuplicadosExcluyendoId(idUnique,
                dto.getIdA(), dto.getUrlAcceso(), dto.getDescriptor(),
                dto.getUrlDescriptor(), dto.getTabla(), dto.getCampo(),
                dto.getComentarioA()) > 0) {
            throw new RuntimeException("Este microdato ya está registrado para la variable.");
        }

        microdatos_armo_enty actualizado = convertirA_Entity(dto);
        actualizado.setIdUnique(idUnique);
        return convertirA_DTO(microdatosArmoRepo.save(actualizado));
    }

    @Override
    public void eliminarMicrodato(Integer idUnique) {
        if (!microdatosArmoRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe el microdato con id_unique: " + idUnique);
        }

        microdatosArmoRepo.deleteById(idUnique);
    }

    @Override
    public List<microdatos_armo_dto> obtenerPorIdA(String idA) {
        return microdatosArmoRepo.findByIdAOrderByIdUniqueAsc(idA)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    private void validarMicrodato(microdatos_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El microdato no puede ser nulo");
        }

        if (dto.getIdA() == null || dto.getIdA().trim().isEmpty()) {
            throw new RuntimeException("El campo id_a es obligatorio");
        }

        if (dto.getUrlAcceso() == null || dto.getUrlAcceso().trim().isEmpty()) {
            throw new RuntimeException("El campo url_acceso es obligatorio");
        }

        if (dto.getDescriptor() == null || dto.getDescriptor().trim().isEmpty()) {
            throw new RuntimeException("El campo descriptor es obligatorio");
        }

        if (dto.getUrlDescriptor() == null
                || dto.getUrlDescriptor().trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo url_descriptor es obligatorio");
        }

        if (dto.getTabla() == null || dto.getTabla().trim().isEmpty()) {
            throw new RuntimeException("El campo tabla es obligatorio");
        }

        if (dto.getCampo() == null || dto.getCampo().trim().isEmpty()) {
            throw new RuntimeException("El campo campo es obligatorio");
        }

        if (dto.getComentarioA() == null
                || dto.getComentarioA().trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo comentario_a es obligatorio");
        }
    }

    private microdatos_armo_dto convertirA_DTO(microdatos_armo_enty entity) {
        return new microdatos_armo_dto(
                entity.getIdUnique(),
                entity.getIdA(),
                entity.getUrlAcceso(),
                entity.getDescriptor(),
                entity.getUrlDescriptor(),
                entity.getTabla(),
                entity.getCampo(),
                entity.getComentarioA());
    }

    private microdatos_armo_enty convertirA_Entity(microdatos_armo_dto dto) {
        return new microdatos_armo_enty(
                dto.getIdUnique(),
                dto.getIdA(),
                dto.getUrlAcceso(),
                dto.getDescriptor(),
                dto.getUrlDescriptor(),
                dto.getTabla(),
                dto.getCampo(),
                dto.getComentarioA());
    }
}
