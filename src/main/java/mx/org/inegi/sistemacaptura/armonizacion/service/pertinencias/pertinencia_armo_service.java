package mx.org.inegi.sistemacaptura.armonizacion.service.pertinencias;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias.pertinencia_armo_dto;

public interface pertinencia_armo_service {

    pertinencia_armo_dto guardar(pertinencia_armo_dto dto);

    pertinencia_armo_dto actualizar(Integer idUnique, pertinencia_armo_dto dto);

    void eliminar(Integer idUnique);

    Optional<pertinencia_armo_dto> obtenerPorIdA(String idA);
}
