/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.pertinencias;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.pertinencias.pertinencia_enty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface pertinencia_repo extends JpaRepository<pertinencia_enty, Integer> {

    Optional<pertinencia_enty> findByIdA(String idA);

    void deleteByIdA(String idA);
}