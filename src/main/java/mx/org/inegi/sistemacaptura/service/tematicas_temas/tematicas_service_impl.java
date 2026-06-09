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
import mx.org.inegi.sistemacaptura.entity.tematicas_temas.tematicas_enty;
import mx.org.inegi.sistemacaptura.repository.tematicas_temas.tematicas_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class tematicas_service_impl implements tematicas_service {

    @Autowired
    private tematicas_repo tematicasRepo;

    @Override
    public List<tematicas_enty> obtenerPorAcronimo(String acronimo) {
        return tematicasRepo.findByAcronimoOrderByTematicaAsc(acronimo);
    }
}