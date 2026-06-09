/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.pertinencias;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;
import mx.org.inegi.sistemacaptura.repository.pertinencias.pertinencia_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class pertinencia_services {
     @Autowired
    private pertinencia_repo repository;

    public pertinencia_enty guardar(pertinencia_enty pertinencia) {
        return repository.save(pertinencia);
    }

    public Optional<pertinencia_enty> buscarPorIdA(String idA) {
        return repository.findByIdA(idA);
    }

    @Transactional
    public Map<String, Object> editarPertinencia(String idA, pertinencia_enty dto) {
        pertinencia_enty existente = repository.findByIdA(idA)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pertinencia no encontrada"));

        existente.setPertinencia(dto.getPertinencia());
        existente.setContribucion(dto.getContribucion());
        existente.setViabilidad(dto.getViabilidad());
        existente.setPropuesta(dto.getPropuesta());
        existente.setComentarioS(dto.getComentarioS());

        repository.save(existente);

        Map<String, Object> response = new HashMap<String, Object>();
        response.put("message", "Pertinencia actualizada correctamente");
        return response;
    }
}
