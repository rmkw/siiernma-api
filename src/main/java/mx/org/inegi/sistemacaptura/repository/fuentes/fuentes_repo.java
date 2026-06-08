/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.fuentes;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.List;
import java.util.Optional;
import mx.org.inegi.sistemacaptura.entity.fuentes.fuentes_enty;
import org.springframework.data.jpa.repository.JpaRepository;
public interface fuentes_repo extends JpaRepository<fuentes_enty, String> {

    List<fuentes_enty> findByAcronimo(String acronimo);

    List<fuentes_enty> findByAcronimoOrderByIdFuenteSeleccionDesc(String acronimo);

    Optional<fuentes_enty> findByIdFuenteSeleccion(String idFuenteSeleccion);

    boolean existsByIdFuenteSeleccion(String idFuenteSeleccion);

    Optional<fuentes_enty> findByIdFuente(String idFuente);

    boolean existsByIdFuente(String idFuente);
}
