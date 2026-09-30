package mx.org.inegi.sistemacaptura.armonizacion.service.clasificadores;

import java.util.List;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.clasificadores_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.clasificadores_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.clasificadores.clasificadores_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class clasificadores_armo_service_impl implements clasificadores_armo_service {

    @Autowired
    private clasificadores_armo_repo clasificadoresArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public clasificadores_armo_dto guardarClasificador(clasificadores_armo_dto dto) {
        validarClasificador(dto);

        if (!variablesArmoRepo.existsById(dto.getIdA())) {
            throw new RuntimeException(
                    "No existe la variable en armonizacion con id_a: " + dto.getIdA());
        }

        clasificadores_armo_enty entity = convertirA_Entity(dto);
        entity.setIdUnique(null);
        clasificadores_armo_enty guardado = clasificadoresArmoRepo.save(entity);
        variablesArmoRepo.sincronizarClasificacion(dto.getIdA());
        return convertirA_DTO(guardado);
    }

    @Override
    public clasificadores_armo_dto actualizarClasificador(Integer idUnique,
            clasificadores_armo_dto dto) {
        validarClasificador(dto);
        clasificadores_armo_enty existente = clasificadoresArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe el clasificador con id_unique: " + idUnique));

        if (!existente.getIdA().equals(dto.getIdA())) {
            throw new RuntimeException("No es posible cambiar la variable del clasificador");
        }

        clasificadores_armo_enty actualizado = convertirA_Entity(dto);
        actualizado.setIdUnique(idUnique);
        clasificadores_armo_enty guardado = clasificadoresArmoRepo.save(actualizado);
        variablesArmoRepo.sincronizarClasificacion(dto.getIdA());
        return convertirA_DTO(guardado);
    }

    @Override
    public void eliminarClasificador(Integer idUnique) {
        clasificadores_armo_enty existente = clasificadoresArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe el clasificador con id_unique: " + idUnique));
        clasificadoresArmoRepo.delete(existente);
        variablesArmoRepo.sincronizarClasificacion(existente.getIdA());
    }

    @Override
    @Transactional(readOnly = true)
    public List<clasificadores_armo_dto> obtenerPorIdA(String idA) {
        return clasificadoresArmoRepo.findByIdAOrderByIdUniqueAsc(idA)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    private void validarClasificador(clasificadores_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El clasificador no puede ser nulo");
        }

        if (dto.getIdA() == null || dto.getIdA().trim().isEmpty()) {
            throw new RuntimeException("El campo id_a es obligatorio");
        }
    }

    private clasificadores_armo_dto convertirA_DTO(clasificadores_armo_enty entity) {
        return new clasificadores_armo_dto(
                entity.getIdUnique(), entity.getIdA(), entity.getClasificador(),
                entity.getVersion(), entity.getUrl(), entity.getComentariosA());
    }

    private clasificadores_armo_enty convertirA_Entity(clasificadores_armo_dto dto) {
        return new clasificadores_armo_enty(
                dto.getIdUnique(), dto.getIdA(), dto.getClasificador(),
                dto.getVersion(), dto.getUrl(), dto.getComentariosA());
    }
}
