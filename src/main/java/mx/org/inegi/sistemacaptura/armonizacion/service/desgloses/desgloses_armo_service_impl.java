package mx.org.inegi.sistemacaptura.armonizacion.service.desgloses;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.desgloses.desgloses_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tabulados.tabulados_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class desgloses_armo_service_impl implements desgloses_armo_service {

    @Autowired
    private desgloses_armo_repo desglosesArmoRepo;

    @Autowired
    private tabulados_armo_repo tabuladosArmoRepo;

    @Override
    public List<desgloses_armo_dto> obtenerPorTabulado(String idTabulado) {
        validarCampo(idTabulado, "id_tabulado");
        return desglosesArmoRepo
                .findByIdTabuladoOrderByIdUniqueAsc(idTabulado)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<desgloses_armo_dto> obtenerPorId(Integer idUnique) {
        return desglosesArmoRepo.findById(idUnique).map(this::convertirA_DTO);
    }

    @Override
    public desgloses_armo_dto guardarDesglose(desgloses_armo_dto dto) {
        validarDesglose(dto);
        validarTabulado(dto.getIdTabulado());
        dto.setIdUnique(null);
        return convertirA_DTO(
                desglosesArmoRepo.save(convertirA_Entity(dto)));
    }

    @Override
    public desgloses_armo_dto actualizarDesglose(
            Integer idUnique, desgloses_armo_dto dto) {
        validarDesglose(dto);
        validarTabulado(dto.getIdTabulado());

        desgloses_armo_enty existente = desglosesArmoRepo.findById(idUnique)
                .orElseThrow(() -> new RuntimeException(
                "No existe el desglose con id_unique: " + idUnique));

        existente.setIdTabulado(dto.getIdTabulado());
        existente.setDesglose(dto.getDesglose());
        existente.setComentarioA(dto.getComentarioA());
        return convertirA_DTO(desglosesArmoRepo.save(existente));
    }

    @Override
    public void eliminarDesglose(Integer idUnique) {
        if (!desglosesArmoRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe el desglose con id_unique: " + idUnique);
        }
        desglosesArmoRepo.deleteById(idUnique);
    }

    private void validarDesglose(desgloses_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El desglose no puede ser nulo");
        }
        validarCampo(dto.getIdTabulado(), "id_tabulado");
        validarCampo(dto.getDesglose(), "desglose");
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

    private desgloses_armo_dto convertirA_DTO(desgloses_armo_enty entity) {
        return new desgloses_armo_dto(
                entity.getIdUnique(),
                entity.getIdTabulado(),
                entity.getDesglose(),
                entity.getComentarioA());
    }

    private desgloses_armo_enty convertirA_Entity(desgloses_armo_dto dto) {
        return new desgloses_armo_enty(
                dto.getIdUnique(),
                dto.getIdTabulado(),
                dto.getDesglose(),
                dto.getComentarioA());
    }
}
