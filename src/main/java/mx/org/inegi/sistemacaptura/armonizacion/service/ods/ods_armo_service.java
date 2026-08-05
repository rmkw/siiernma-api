package mx.org.inegi.sistemacaptura.armonizacion.service.ods;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.ods.ods_armo_dto;

public interface ods_armo_service {

    ods_armo_dto guardar(ods_armo_dto dto);

    ods_armo_dto actualizar(Integer idUnique, ods_armo_dto dto);

    void eliminar(Integer idUnique);

    List<ods_armo_dto> obtenerPorIdA(String idA);
}
