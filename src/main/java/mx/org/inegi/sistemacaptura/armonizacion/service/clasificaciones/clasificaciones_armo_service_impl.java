/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.armonizacion.service.clasificaciones;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificaciones.clasificaciones_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.clasificaciones.clasificaciones_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class clasificaciones_armo_service_impl
        implements clasificaciones_armo_service {

    @Autowired
    private clasificaciones_armo_repo clasificacionesArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public clasificaciones_armo_dto guardarClasificacion(
            clasificaciones_armo_dto dto) {

        validarClasificacion(dto);

        if (!variablesArmoRepo.existsById(dto.getIdA())) {
            throw new RuntimeException(
                    "No existe la variable en armonizacion con id_a: "
                    + dto.getIdA());
        }

        clasificaciones_armo_enty entity = convertirA_Entity(dto);
        entity.setIdUnique(null);

        clasificaciones_armo_enty guardada
                = clasificacionesArmoRepo.save(entity);

        return convertirA_DTO(guardada);
    }

    @Override
    public void eliminarClasificacion(Integer idUnique) {
        if (!clasificacionesArmoRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe la clasificacion con id_unique: " + idUnique);
        }

        clasificacionesArmoRepo.deleteById(idUnique);
    }

    @Override
    public List<clasificaciones_armo_dto> obtenerPorIdA(String idA) {
        return clasificacionesArmoRepo.findByIdAOrderByIdUniqueAsc(idA)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    private void validarClasificacion(clasificaciones_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("La clasificacion no puede ser nula");
        }

        if (dto.getIdA() == null || dto.getIdA().trim().isEmpty()) {
            throw new RuntimeException("El campo id_a es obligatorio");
        }

        if (dto.getClase() == null || dto.getClase().trim().isEmpty()) {
            throw new RuntimeException("El campo clase es obligatorio");
        }

        if (dto.getComentarioA() == null
                || dto.getComentarioA().trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo comentario_a es obligatorio");
        }
    }

    private clasificaciones_armo_dto convertirA_DTO(
            clasificaciones_armo_enty entity) {
        return new clasificaciones_armo_dto(
                entity.getIdUnique(),
                entity.getIdA(),
                entity.getClase(),
                entity.getComentarioA());
    }

    private clasificaciones_armo_enty convertirA_Entity(
            clasificaciones_armo_dto dto) {
        return new clasificaciones_armo_enty(
                dto.getIdUnique(),
                dto.getIdA(),
                dto.getClase(),
                dto.getComentarioA());
    }
}
