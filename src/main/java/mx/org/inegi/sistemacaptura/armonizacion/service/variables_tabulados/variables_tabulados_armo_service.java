package mx.org.inegi.sistemacaptura.armonizacion.service.variables_tabulados;

import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_dto;

public interface variables_tabulados_armo_service {

    List<variables_tabulados_armo_dto> obtenerPorTabulado(String idTabulado);
    List<variables_tabulados_armo_dto> obtenerPorVariable(String idA);
    Optional<variables_tabulados_armo_dto> obtenerPorId(Integer idUnique);
    variables_tabulados_armo_dto guardar(
            variables_tabulados_armo_dto dto);
    variables_tabulados_armo_dto actualizar(
            Integer idUnique, variables_tabulados_armo_dto dto);
    void eliminar(Integer idUnique);
}
