package mx.org.inegi.sistemacaptura.armonizacion.service.mdea;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.mdea.mdea_armo_dto;

public interface mdea_armo_service {

    mdea_armo_dto guardar(mdea_armo_dto dto);

    mdea_armo_dto actualizar(Integer idUnique, mdea_armo_dto dto);

    void eliminar(Integer idUnique);

    List<mdea_armo_dto> obtenerPorIdA(String idA);
}
