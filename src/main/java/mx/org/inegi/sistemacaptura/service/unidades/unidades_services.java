/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.service.unidades;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import mx.org.inegi.sistemacaptura.entity.unidades.unidades_enty;
import mx.org.inegi.sistemacaptura.repository.unidades.unidades_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class unidades_services {
    @Autowired
    private unidades_repo repo;

    public List<unidades_enty> getAllDir() {
        return repo.findAll(Sort.by("idUnidad"));
    }
}
