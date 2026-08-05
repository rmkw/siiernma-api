package mx.org.inegi.sistemacaptura.armonizacion.service.pertinencias;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias.pertinencia_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias.pertinencia_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.pertinencias.pertinencia_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class pertinencia_armo_service_impl implements pertinencia_armo_service {

    @Autowired
    private pertinencia_armo_repo pertinenciaArmoRepo;

    @Autowired
    private variables_armo_repo variablesArmoRepo;

    @Override
    public pertinencia_armo_dto guardar(pertinencia_armo_dto dto) {
        validar(dto);
        if (!variablesArmoRepo.existsById(dto.getIdA())) {
            throw new RuntimeException("No existe la variable en armonización con id_a: "
                    + dto.getIdA());
        }
        if (pertinenciaArmoRepo.findByIdA(dto.getIdA()).isPresent()) {
            throw new RuntimeException("La variable ya tiene una pertinencia registrada");
        }

        pertinencia_armo_enty entidad = convertirAEntidad(dto);
        entidad.setIdUnique(null);
        return convertirADto(pertinenciaArmoRepo.save(entidad));
    }

    @Override
    public pertinencia_armo_dto actualizar(Integer idUnique, pertinencia_armo_dto dto) {
        validar(dto);
        pertinencia_armo_enty existente = pertinenciaArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe la pertinencia con id_unique: " + idUnique));

        if (!existente.getIdA().equals(dto.getIdA())) {
            throw new RuntimeException("No es posible cambiar la variable de la pertinencia");
        }

        pertinencia_armo_enty actualizada = convertirAEntidad(dto);
        actualizada.setIdUnique(idUnique);
        return convertirADto(pertinenciaArmoRepo.save(actualizada));
    }

    @Override
    public void eliminar(Integer idUnique) {
        if (!pertinenciaArmoRepo.existsById(idUnique)) {
            throw new RuntimeException("No existe la pertinencia con id_unique: " + idUnique);
        }
        pertinenciaArmoRepo.deleteById(idUnique);
    }

    @Override
    public Optional<pertinencia_armo_dto> obtenerPorIdA(String idA) {
        return pertinenciaArmoRepo.findByIdA(idA).map(this::convertirADto);
    }

    private void validar(pertinencia_armo_dto dto) {
        if (dto == null || esVacio(dto.getIdA()) || esVacio(dto.getPertinencia())
                || esVacio(dto.getContribucion()) || esVacio(dto.getViabilidad())
                || esVacio(dto.getPropuesta()) || esVacio(dto.getComentarioS())) {
            throw new RuntimeException("Todos los campos de pertinencia son obligatorios");
        }
    }

    private boolean esVacio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    private pertinencia_armo_enty convertirAEntidad(pertinencia_armo_dto dto) {
        pertinencia_armo_enty entidad = new pertinencia_armo_enty();
        entidad.setIdUnique(dto.getIdUnique());
        entidad.setIdA(dto.getIdA());
        entidad.setPertinencia(dto.getPertinencia());
        entidad.setContribucion(dto.getContribucion());
        entidad.setViabilidad(dto.getViabilidad());
        entidad.setPropuesta(dto.getPropuesta());
        entidad.setComentarioS(dto.getComentarioS());
        return entidad;
    }

    private pertinencia_armo_dto convertirADto(pertinencia_armo_enty entidad) {
        pertinencia_armo_dto dto = new pertinencia_armo_dto();
        dto.setIdUnique(entidad.getIdUnique());
        dto.setIdA(entidad.getIdA());
        dto.setPertinencia(entidad.getPertinencia());
        dto.setContribucion(entidad.getContribucion());
        dto.setViabilidad(entidad.getViabilidad());
        dto.setPropuesta(entidad.getPropuesta());
        dto.setComentarioS(entidad.getComentarioS());
        return dto;
    }
}
