package mx.org.inegi.sistemacaptura.armonizacion.service.tabulados;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulados_armo_dto;

public interface tabulados_armo_service {

    List<tabulados_armo_dto> buscarPorPrefijo(String prefijo);

    Optional<tabulados_armo_dto> obtenerPorId(String idTabulado);

    tabulados_armo_dto guardarTabulado(tabulados_armo_dto dto);

    tabulados_armo_dto actualizarTabulado(
            String idTabulado, tabulados_armo_dto dto);

    void eliminarTabulado(String idTabulado);
}
