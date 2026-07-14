package mx.org.inegi.sistemacaptura.armonizacion.service.variables_tabulados;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables.variables_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tabulados.tabulados_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables_tabulados.variables_tabulados_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class variables_tabulados_armo_service_impl
        implements variables_tabulados_armo_service {

    @Autowired
    private variables_tabulados_armo_repo relacionesRepo;
    @Autowired
    private variables_armo_repo variablesRepo;
    @Autowired
    private tabulados_armo_repo tabuladosRepo;

    @Override
    public List<variables_tabulados_armo_dto> obtenerPorTabulado(
            String idTabulado) {
        validarCampo(idTabulado, "id_tabulado");
        return relacionesRepo.findByIdTabuladoOrderByIdUniqueAsc(idTabulado)
                .stream().map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<variables_tabulados_armo_dto> obtenerPorVariable(String idA) {
        validarCampo(idA, "id_a");
        return relacionesRepo.findByIdAOrderByIdUniqueAsc(idA)
                .stream().map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<variables_tabulados_armo_dto> obtenerPorId(
            Integer idUnique) {
        return relacionesRepo.findById(idUnique).map(this::convertirA_DTO);
    }

    @Override
    public variables_tabulados_armo_dto guardar(
            variables_tabulados_armo_dto dto) {
        validarRelacion(dto);
        validarReferencias(dto);
        if (relacionesRepo.existsByIdAAndIdTabulado(
                dto.getIdA(), dto.getIdTabulado())) {
            throw new RuntimeException(
                    "La variable ya está relacionada con este tabulado");
        }
        dto.setIdUnique(null);
        return convertirA_DTO(relacionesRepo.save(convertirA_Entity(dto)));
    }

    @Override
    public variables_tabulados_armo_dto actualizar(
            Integer idUnique, variables_tabulados_armo_dto dto) {
        validarRelacion(dto);
        validarReferencias(dto);
        variables_tabulados_armo_enty existente
                = relacionesRepo.findById(idUnique)
                        .orElseThrow(() -> new RuntimeException(
                        "No existe la relación con id_unique: " + idUnique));

        boolean cambioLlaves = !existente.getIdA().equals(dto.getIdA())
                || !existente.getIdTabulado().equals(dto.getIdTabulado());
        if (cambioLlaves && relacionesRepo.existsByIdAAndIdTabulado(
                dto.getIdA(), dto.getIdTabulado())) {
            throw new RuntimeException(
                    "La variable ya está relacionada con este tabulado");
        }

        existente.setIdA(dto.getIdA());
        existente.setIdTabulado(dto.getIdTabulado());
        existente.setComentarioA(dto.getComentarioA());
        return convertirA_DTO(relacionesRepo.save(existente));
    }

    @Override
    public void eliminar(Integer idUnique) {
        if (!relacionesRepo.existsById(idUnique)) {
            throw new RuntimeException(
                    "No existe la relación con id_unique: " + idUnique);
        }
        relacionesRepo.deleteById(idUnique);
    }

    private void validarRelacion(variables_tabulados_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("La relación no puede ser nula");
        }
        validarCampo(dto.getIdA(), "id_a");
        validarCampo(dto.getIdTabulado(), "id_tabulado");
        validarCampo(dto.getComentarioA(), "comentario_a");
    }

    private void validarReferencias(variables_tabulados_armo_dto dto) {
        if (!variablesRepo.existsById(dto.getIdA())) {
            throw new RuntimeException(
                    "No existe la variable con id_a: " + dto.getIdA());
        }
        if (!tabuladosRepo.existsById(dto.getIdTabulado())) {
            throw new RuntimeException(
                    "No existe el tabulado con id_tabulado: "
                    + dto.getIdTabulado());
        }
    }

    private void validarCampo(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo " + campo + " es obligatorio");
        }
    }

    private variables_tabulados_armo_dto convertirA_DTO(
            variables_tabulados_armo_enty entity) {
        variables_armo_enty variable = variablesRepo
                .findById(entity.getIdA()).orElse(null);
        return new variables_tabulados_armo_dto(
                entity.getIdUnique(),
                entity.getIdA(),
                entity.getIdTabulado(),
                entity.getComentarioA(),
                variable != null ? variable.getVariableA() : null,
                variable != null ? variable.getVariableS() : null);
    }

    private variables_tabulados_armo_enty convertirA_Entity(
            variables_tabulados_armo_dto dto) {
        return new variables_tabulados_armo_enty(
                dto.getIdUnique(),
                dto.getIdA(),
                dto.getIdTabulado(),
                dto.getComentarioA());
    }
}
