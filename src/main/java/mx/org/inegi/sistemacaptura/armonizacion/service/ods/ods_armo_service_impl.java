package mx.org.inegi.sistemacaptura.armonizacion.service.ods;

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.ods.ods_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.ods.ods_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.ods.ods_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ods_armo_service_impl implements ods_armo_service {

    @Autowired
    private ods_armo_repo odsArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public ods_armo_dto guardar(ods_armo_dto dto) {
        validar(dto);
        variables_armo_enty variable = obtenerVariable(dto.getIdA());

        ods_armo_enty entidad = convertirAEntidad(dto);
        entidad.setIdUnique(null);
        ods_armo_enty guardada = odsArmoRepo.save(entidad);

        variable.setOds(true);
        variablesArmoRepo.save(variable);
        return convertirADto(guardada);
    }

    @Override
    public ods_armo_dto actualizar(Integer idUnique, ods_armo_dto dto) {
        validar(dto);
        ods_armo_enty existente = odsArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe la relación ODS con id_unique: " + idUnique));

        if (!existente.getIdA().equals(dto.getIdA())) {
            throw new RuntimeException("No es posible cambiar la variable de la relación ODS");
        }

        ods_armo_enty actualizada = convertirAEntidad(dto);
        actualizada.setIdUnique(idUnique);
        return convertirADto(odsArmoRepo.save(actualizada));
    }

    @Override
    public void eliminar(Integer idUnique) {
        ods_armo_enty relacion = odsArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe la relación ODS con id_unique: " + idUnique));

        String idA = relacion.getIdA();
        odsArmoRepo.delete(relacion);

        if (odsArmoRepo.countByIdA(idA) == 0) {
            variablesArmoRepo.findById(idA).ifPresent(variable -> {
                variable.setOds(false);
                variablesArmoRepo.save(variable);
            });
        }
    }

    @Override
    public List<ods_armo_dto> obtenerPorIdA(String idA) {
        return odsArmoRepo.findByIdAOrderByIdUniqueAsc(idA).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    private variables_armo_enty obtenerVariable(String idA) {
        return variablesArmoRepo.findById(idA).orElseThrow(() -> new RuntimeException(
                "No existe la variable en armonización con id_a: " + idA));
    }

    private void validar(ods_armo_dto dto) {
        if (dto == null || esVacio(dto.getIdA()) || esVacio(dto.getObjetivo())
                || esVacio(dto.getMeta()) || esVacio(dto.getIndicador())
                || esVacio(dto.getContribucion()) || esVacio(dto.getComentarioS())) {
            throw new RuntimeException("Todos los campos de la relación ODS son obligatorios");
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private ods_armo_enty convertirAEntidad(ods_armo_dto dto) {
        ods_armo_enty entidad = new ods_armo_enty();
        entidad.setIdUnique(dto.getIdUnique());
        entidad.setIdA(dto.getIdA());
        entidad.setObjetivo(dto.getObjetivo());
        entidad.setMeta(dto.getMeta());
        entidad.setIndicador(dto.getIndicador());
        entidad.setContribucion(dto.getContribucion());
        entidad.setComentarioS(dto.getComentarioS());
        return entidad;
    }

    private ods_armo_dto convertirADto(ods_armo_enty entidad) {
        ods_armo_dto dto = new ods_armo_dto();
        dto.setIdUnique(entidad.getIdUnique());
        dto.setIdA(entidad.getIdA());
        dto.setObjetivo(entidad.getObjetivo());
        dto.setMeta(entidad.getMeta());
        dto.setIndicador(entidad.getIndicador());
        dto.setContribucion(entidad.getContribucion());
        dto.setComentarioS(entidad.getComentarioS());
        return dto;
    }
}
