package mx.org.inegi.sistemacaptura.armonizacion.service.tabulados;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulados_armo_dto;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulados_armo_enty;
import mx.org.inegi.sistemacaptura.armonizacion.repository.tabulados.tabulados_armo_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class tabulados_armo_service_impl implements tabulados_armo_service {

    @Autowired
    private tabulados_armo_repo tabuladosArmoRepo;

    @Override
    public List<tabulados_armo_dto> buscarPorPrefijo(String prefijo) {
        if (prefijo == null || prefijo.trim().length() < 2) {
            throw new RuntimeException(
                    "El prefijo debe tener al menos 2 caracteres");
        }

        String prefijoNormalizado = prefijo.trim().toUpperCase();

        return tabuladosArmoRepo
                .findByIdTabuladoStartingWithOrderByIdTabuladoAsc(
                        prefijoNormalizado)
                .stream()
                .map(this::convertirA_DTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<tabulados_armo_dto> obtenerPorId(String idTabulado) {
        return tabuladosArmoRepo.findById(idTabulado)
                .map(this::convertirA_DTO);
    }

    @Override
    public tabulados_armo_dto guardarTabulado(tabulados_armo_dto dto) {
        validarTabulado(dto);

        if (tabuladosArmoRepo.existsById(dto.getIdTabulado())) {
            throw new RuntimeException(
                    "Ya existe el tabulado con id_tabulado: "
                    + dto.getIdTabulado());
        }

        tabulados_armo_enty guardado
                = tabuladosArmoRepo.save(convertirA_Entity(dto));
        return convertirA_DTO(guardado);
    }

    @Override
    public tabulados_armo_dto actualizarTabulado(
            String idTabulado, tabulados_armo_dto dto) {
        validarTabulado(dto);

        tabulados_armo_enty existente = tabuladosArmoRepo.findById(idTabulado)
                .orElseThrow(() -> new RuntimeException(
                        "No existe el tabulado con id_tabulado: "
                        + idTabulado));

        existente.setTabulado(dto.getTabulado());
        existente.setTipo(dto.getTipo());
        existente.setHoja(dto.getHoja());
        existente.setUrlAcceso(dto.getUrlAcceso());
        existente.setUrlDescarga(dto.getUrlDescarga());
        existente.setComentarioA(dto.getComentarioA());

        return convertirA_DTO(tabuladosArmoRepo.save(existente));
    }

    @Override
    public void eliminarTabulado(String idTabulado) {
        if (!tabuladosArmoRepo.existsById(idTabulado)) {
            throw new RuntimeException(
                    "No existe el tabulado con id_tabulado: " + idTabulado);
        }

        tabuladosArmoRepo.deleteById(idTabulado);
    }

    private void validarTabulado(tabulados_armo_dto dto) {
        if (dto == null) {
            throw new RuntimeException("El tabulado no puede ser nulo");
        }

        validarCampo(dto.getIdTabulado(), "id_tabulado");
        validarCampo(dto.getTabulado(), "tabulado");
        validarCampo(dto.getTipo(), "tipo");
        validarCampo(dto.getHoja(), "hoja");
        validarCampo(dto.getUrlAcceso(), "url_acceso");
        validarCampo(dto.getUrlDescarga(), "url_descarga");
        validarCampo(dto.getComentarioA(), "comentario_a");
    }

    private void validarCampo(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new RuntimeException(
                    "El campo " + campo + " es obligatorio");
        }
    }

    private tabulados_armo_dto convertirA_DTO(tabulados_armo_enty entity) {
        return new tabulados_armo_dto(
                entity.getIdTabulado(),
                entity.getTabulado(),
                entity.getTipo(),
                entity.getHoja(),
                entity.getUrlAcceso(),
                entity.getUrlDescarga(),
                entity.getComentarioA());
    }

    private tabulados_armo_enty convertirA_Entity(tabulados_armo_dto dto) {
        return new tabulados_armo_enty(
                dto.getIdTabulado(),
                dto.getTabulado(),
                dto.getTipo(),
                dto.getHoja(),
                dto.getUrlAcceso(),
                dto.getUrlDescarga(),
                dto.getComentarioA());
    }
}
