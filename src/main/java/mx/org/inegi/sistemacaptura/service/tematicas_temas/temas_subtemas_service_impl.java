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
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.temas_subtemas_enty;
import mx.org.inegi.sistemacaptura.repository.tematicas_temas.temas_subtemas_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class temas_subtemas_service_impl implements temas_subtemas_service {

    @Autowired
    private temas_subtemas_repo temasSubtemasRepo;

    @Override
    public List<String> obtenerTemas() {
        return temasSubtemasRepo.findTemasDistinct();
    }

    @Override
    public List<temas_subtemas_enty> obtenerSubtemasPorTema(String tema) {
        return temasSubtemasRepo.findByTemaOrderBySubtemaAsc(tema);
    }
}