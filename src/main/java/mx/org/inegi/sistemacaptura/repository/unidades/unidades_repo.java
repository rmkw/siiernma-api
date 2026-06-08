/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.unidades;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import mx.org.inegi.sistemacaptura.entity.unidades.unidades_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface unidades_repo extends JpaRepository<unidades_enty, Long> {
    
}
