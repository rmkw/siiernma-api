/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.datosabiertos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.datosabiertos.datos_abiertos_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.datosabiertos.datos_abiertos_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class datos_abiertos_armo_service_impl
        implements datos_abiertos_armo_service {

    @Autowired
    private datos_abiertos_armo_repo datosAbiertosArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public datos_abiertos_armo_dto guardarDatoAbierto(
            datos_abiertos_armo_dto dto) {
        validarDatoAbierto(dto);

        if (!variablesArmoRepo.existsById(dto.getIdA())) {
            throw new RuntimeException(
                    "No existe la variable en armonizacion con id_a: "
                    + dto.getIdA());
        }

        if (datosAbiertosArmoRepo.contarDuplicados(
                dto.getIdA(),
                dto.getUrlAcceso(),
                dto.getUrlDescarga(),
                dto.getDescriptor(),
                dto.getTabla(),
                dto.getCampo(),
                dto.getComentarioA()) > 0) {
            throw new RuntimeException(
                    "Este dato abierto ya está registrado para la variable. "
                    + "Modifica al menos uno de sus campos antes de "
                    + "intentarlo nuevamente.");
        }

        datos_abiertos_armo_enty entity = convertirA_Entity(dto);
        entity.setIdUnique(null);

        datos_abiertos_armo_enty guardado
                = datosAbiertosArmoRepo.save(entity);

        return convertirA_DTO(guardado);
    }

    @Override
    public void eliminarDatoAbierto(Integer idUnique) {
        if (!datosAbiertosArmoRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe el dato abierto con id_unique: " + idUnique);
        }

        datosAbiertosArmoRepo.deleteById(idUnique);
    }

    @Override
    public List<datos_abiertos_armo_dto> obtenerPorIdA(String idA) {
        return datosAbiertosArmoRepo.findByIdAOrderByIdUniqueAsc(idA)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    private void validarDatoAbierto(datos_abiertos_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El dato abierto no puede ser nulo");
        }

        if (dto.getIdA() == null || dto.getIdA().trim().isEmpty()) {
            throw new RuntimeException("El campo id_a es obligatorio");
        }

        if (dto.getUrlAcceso() == null
                || dto.getUrlAcceso().trim().isEmpty()) {
            throw new RuntimeException("El campo url_acceso es obligatorio");
        }

        if (dto.getUrlDescarga() == null
                || dto.getUrlDescarga().trim().isEmpty()) {
            throw new RuntimeException("El campo url_descarga es obligatorio");
        }

        if (dto.getDescriptor() == null
                || dto.getDescriptor().trim().isEmpty()) {
            throw new RuntimeException("El campo descriptor es obligatorio");
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

    private datos_abiertos_armo_dto convertirA_DTO(
            datos_abiertos_armo_enty entity) {
        return new datos_abiertos_armo_dto(
                entity.getIdUnique(),
                entity.getIdA(),
                entity.getUrlAcceso(),
                entity.getUrlDescarga(),
                entity.getDescriptor(),
                entity.getTabla(),
                entity.getCampo(),
                entity.getComentarioA());
    }

    private datos_abiertos_armo_enty convertirA_Entity(
            datos_abiertos_armo_dto dto) {
        return new datos_abiertos_armo_enty(
                dto.getIdUnique(),
                dto.getIdA(),
                dto.getUrlAcceso(),
                dto.getUrlDescarga(),
                dto.getDescriptor(),
                dto.getTabla(),
                dto.getCampo(),
                dto.getComentarioA());
    }
}
