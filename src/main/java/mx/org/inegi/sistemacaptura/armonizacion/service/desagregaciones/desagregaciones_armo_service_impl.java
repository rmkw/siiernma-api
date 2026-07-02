package mx.org.inegi.sistemacaptura.armonizacion.service.desagregaciones;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.desagregaciones.desagregaciones_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tabulados.tabulados_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class desagregaciones_armo_service_impl
        implements desagregaciones_armo_service {

    @Autowired
    private desagregaciones_armo_repo desagregacionesArmoRepo;

    @Autowired
    private tabulados_armo_repo tabuladosArmoRepo;

    @Override
    public List<desagregaciones_armo_dto> obtenerPorTabulado(
            String idTabulado) {
        validarCampo(idTabulado, "id_tabulado");
        return desagregacionesArmoRepo
                .findByIdTabuladoOrderByIdUniqueAsc(idTabulado)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<desagregaciones_armo_dto> obtenerPorId(Integer idUnique) {
        return desagregacionesArmoRepo.findById(idUnique)
                .map(this::convertirA_DTO);
    }

    @Override
    public desagregaciones_armo_dto guardarDesagregacion(
            desagregaciones_armo_dto dto) {
        validarDesagregacion(dto);
        validarTabulado(dto.getIdTabulado());
        dto.setIdUnique(null);
        return convertirA_DTO(desagregacionesArmoRepo.save(
                convertirA_Entity(dto)));
    }

    @Override
    public desagregaciones_armo_dto actualizarDesagregacion(
            Integer idUnique, desagregaciones_armo_dto dto) {
        validarDesagregacion(dto);
        validarTabulado(dto.getIdTabulado());

        desagregaciones_armo_enty existente
                = desagregacionesArmoRepo.findById(idUnique)
                        .orElseThrow(() -> new RuntimeException(
                        "No existe la desagregación con id_unique: "
                        + idUnique));

        existente.setIdTabulado(dto.getIdTabulado());
        existente.setCoberturaDesagregacion(
                dto.getCoberturaDesagregacion());
        existente.setComentarioA(dto.getComentarioA());
        return convertirA_DTO(desagregacionesArmoRepo.save(existente));
    }

    @Override
    public void eliminarDesagregacion(Integer idUnique) {
        if (!desagregacionesArmoRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe la desagregación con id_unique: " + idUnique);
        }
        desagregacionesArmoRepo.deleteById(idUnique);
    }

    private void validarDesagregacion(desagregaciones_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("La desagregación no puede ser nula");
        }
        validarCampo(dto.getIdTabulado(), "id_tabulado");
        validarCampo(dto.getCoberturaDesagregacion(),
                "cobertura_desagregacion");
        validarCampo(dto.getComentarioA(), "comentario_a");
    }

    private void validarTabulado(String idTabulado) {
        if (!tabuladosArmoRepo.existsById(idTabulado)) {
            throw new RuntimeException(
                    "No existe el tabulado con id_tabulado: " + idTabulado);
        }
    }

    private void validarCampo(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo " + campo + " es obligatorio");
        }
    }

    private desagregaciones_armo_dto convertirA_DTO(
            desagregaciones_armo_enty entity) {
        return new desagregaciones_armo_dto(
                entity.getIdUnique(),
                entity.getIdTabulado(),
                entity.getCoberturaDesagregacion(),
                entity.getComentarioA());
    }

    private desagregaciones_armo_enty convertirA_Entity(
            desagregaciones_armo_dto dto) {
        return new desagregaciones_armo_enty(
                dto.getIdUnique(),
                dto.getIdTabulado(),
                dto.getCoberturaDesagregacion(),
                dto.getComentarioA());
    }
}
