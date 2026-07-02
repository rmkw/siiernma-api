package mx.org.inegi.sistemacaptura.armonizacion.service.desgloses;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_dto;

public interface desgloses_armo_service {

    List<desgloses_armo_dto> obtenerPorTabulado(String idTabulado);

    Optional<desgloses_armo_dto> obtenerPorId(Integer idUnique);

    desgloses_armo_dto guardarDesglose(desgloses_armo_dto dto);

    desgloses_armo_dto actualizarDesglose(
            Integer idUnique, desgloses_armo_dto dto);

    void eliminarDesglose(Integer idUnique);
}
