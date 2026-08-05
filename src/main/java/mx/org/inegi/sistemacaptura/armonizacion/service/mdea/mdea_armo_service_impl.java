package mx.org.inegi.sistemacaptura.armonizacion.service.mdea;

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.mdea.mdea_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.mdea.mdea_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.mdea.mdea_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class mdea_armo_service_impl implements mdea_armo_service {

    @Autowired
    private mdea_armo_repo mdeaArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public mdea_armo_dto guardar(mdea_armo_dto dto) {
        validar(dto);
        variables_armo_enty variable = obtenerVariable(dto.getIdA());

        mdea_armo_enty entidad = convertirAEntidad(dto);
        entidad.setIdUnique(null);
        mdea_armo_enty guardada = mdeaArmoRepo.save(entidad);

        variable.setMdea(true);
        variablesArmoRepo.save(variable);
        return convertirADto(guardada);
    }

    @Override
    public mdea_armo_dto actualizar(Integer idUnique, mdea_armo_dto dto) {
        validar(dto);
        mdea_armo_enty existente = mdeaArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe la relación MDEA con id_unique: " + idUnique));

        if (!existente.getIdA().equals(dto.getIdA())) {
            throw new RuntimeException("No es posible cambiar la variable de la relación MDEA");
        }

        mdea_armo_enty actualizada = convertirAEntidad(dto);
        actualizada.setIdUnique(idUnique);
        return convertirADto(mdeaArmoRepo.save(actualizada));
    }

    @Override
    public void eliminar(Integer idUnique) {
        mdea_armo_enty relacion = mdeaArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe la relación MDEA con id_unique: " + idUnique));

        String idA = relacion.getIdA();
        mdeaArmoRepo.delete(relacion);

        if (mdeaArmoRepo.countByIdA(idA) == 0) {
            variablesArmoRepo.findById(idA).ifPresent(variable -> {
                variable.setMdea(false);
                variablesArmoRepo.save(variable);
            });
        }
    }

    @Override
    public List<mdea_armo_dto> obtenerPorIdA(String idA) {
        return mdeaArmoRepo.findByIdAOrderByIdUniqueAsc(idA).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    private variables_armo_enty obtenerVariable(String idA) {
        return variablesArmoRepo.findById(idA).orElseThrow(() -> new RuntimeException(
                "No existe la variable en armonización con id_a: " + idA));
    }

    private void validar(mdea_armo_dto dto) {
        if (dto == null || esVacio(dto.getIdA()) || esVacio(dto.getComponente())
                || esVacio(dto.getSubcomponente()) || esVacio(dto.getTema())
                || esVacio(dto.getEstadistica1()) || esVacio(dto.getEstadistica2())
                || esVacio(dto.getContribucion()) || esVacio(dto.getComentarioS())) {
            throw new RuntimeException("Todos los campos de la relación MDEA son obligatorios");
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private mdea_armo_enty convertirAEntidad(mdea_armo_dto dto) {
        mdea_armo_enty entidad = new mdea_armo_enty();
        entidad.setIdUnique(dto.getIdUnique());
        entidad.setIdA(dto.getIdA());
        entidad.setComponente(dto.getComponente());
        entidad.setSubcomponente(dto.getSubcomponente());
        entidad.setTema(dto.getTema());
        entidad.setEstadistica1(dto.getEstadistica1());
        entidad.setEstadistica2(dto.getEstadistica2());
        entidad.setContribucion(dto.getContribucion());
        entidad.setComentarioS(dto.getComentarioS());
        return entidad;
    }

    private mdea_armo_dto convertirADto(mdea_armo_enty entidad) {
        mdea_armo_dto dto = new mdea_armo_dto();
        dto.setIdUnique(entidad.getIdUnique());
        dto.setIdA(entidad.getIdA());
        dto.setComponente(entidad.getComponente());
        dto.setSubcomponente(entidad.getSubcomponente());
        dto.setTema(entidad.getTema());
        dto.setEstadistica1(entidad.getEstadistica1());
        dto.setEstadistica2(entidad.getEstadistica2());
        dto.setContribucion(entidad.getContribucion());
        dto.setComentarioS(entidad.getComentarioS());
        return dto;
    }
}
