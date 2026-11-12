/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.tematicas_temas;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import java.util.Locale;
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;
import mx.org.inegi.sistemacaptura.repository.tematicas_temas.temas_subtemas_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class temas_subtemas_service_impl implements temas_subtemas_service {

    @Autowired
    private temas_subtemas_repo temasSubtemasRepo;

    @Override
    public List<String> obtenerTemas() {
        return temasSubtemasRepo.findTemasDistinct();
    }

    @Override
    public String crearTema(String nombre) {
        String tema = nombre == null ? "" : nombre.trim().replaceAll("\\s+", " ");
        if (tema.isEmpty() || tema.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El tema debe tener entre 1 y 255 caracteres");
        }
        String normalizado = tema.toLowerCase(Locale.ROOT);
        if (temasSubtemasRepo.existsByNombreNormalizado(normalizado)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El tema ya existe");
        }
        temas_subtemas_enty nuevo = new temas_subtemas_enty();
        nuevo.setTema(tema);
        nuevo.setSubtema(null);
        return temasSubtemasRepo.saveAndFlush(nuevo).getTema();
    }

    @Override
    @Transactional
    public temas_subtemas_enty crearSubtema(String nombreTema, String nombreSubtema) {
        String tema = nombreTema == null ? "" : nombreTema.trim().replaceAll("\\s+", " ");
        String subtema = nombreSubtema == null ? "" : nombreSubtema.trim().replaceAll("\\s+", " ");
        if (tema.isEmpty() || subtema.isEmpty() || subtema.length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tema y el subtema son obligatorios");
        }
        String temaNormalizado = tema.toLowerCase(Locale.ROOT);
        if (!temasSubtemasRepo.existsByNombreNormalizado(temaNormalizado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El tema no existe");
        }
        if (temasSubtemasRepo.existsSubtemaNormalizado(temaNormalizado, subtema.toLowerCase(Locale.ROOT))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El subtema ya existe en ese tema");
        }
        temas_subtemas_enty registro = temasSubtemasRepo.findFirstByTemaAndSubtemaIsNull(tema)
                .orElseGet(temas_subtemas_enty::new);
        registro.setTema(tema);
        registro.setSubtema(subtema);
        return temasSubtemasRepo.saveAndFlush(registro);
    }

    @Override
    public List<temas_subtemas_enty> obtenerSubtemasPorTema(String tema) {
        return temasSubtemasRepo.findByTemaOrderBySubtemaAsc(tema);
    }
}
