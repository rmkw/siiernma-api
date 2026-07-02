package mx.org.inegi.sistemacaptura.armonizacion.service.desagregaciones;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_dto;

public interface desagregaciones_armo_service {

    List<desagregaciones_armo_dto> obtenerPorTabulado(String idTabulado);

    Optional<desagregaciones_armo_dto> obtenerPorId(Integer idUnique);

    desagregaciones_armo_dto guardarDesagregacion(
            desagregaciones_armo_dto dto);

    desagregaciones_armo_dto actualizarDesagregacion(
            Integer idUnique, desagregaciones_armo_dto dto);

    void eliminarDesagregacion(Integer idUnique);
}
