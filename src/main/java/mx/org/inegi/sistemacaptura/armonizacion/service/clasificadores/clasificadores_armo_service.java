package mx.org.inegi.sistemacaptura.armonizacion.service.clasificadores;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.clasificadores_armo_dto;

public interface clasificadores_armo_service {

    clasificadores_armo_dto guardarClasificador(clasificadores_armo_dto dto);

    clasificadores_armo_dto actualizarClasificador(Integer idUnique,
            clasificadores_armo_dto dto);

    void eliminarClasificador(Integer idUnique);

    List<clasificadores_armo_dto> obtenerPorIdA(String idA);
}
