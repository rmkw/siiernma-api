/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.procesos;

/**
 *
 * @author LUIS.CASTANEDAL
 */

import java.util.List;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_dto;
import mx.org.inegi.sistemacaptura.entity.procesos.procesos_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface  procesos_repo extends JpaRepository<procesos_enty, String> {

    List<procesos_enty> findByUnidadIgnoreCaseOrderByProcesoAsc(String unidad);

    @Query("SELECT new mx.org.inegi.sistemacaptura.entity.procesos.procesos_dto("
            + "p.acronimo, "
            + "p.proceso, "
            + "p.metodo, "
            + "p.objetivo, "
            + "p.pobjeto, "
            + "p.uobservacion, "
            + "p.unidad, "
            + "p.periodicidad, "
            + "p.iin, "
            + "p.estatus, "
            + "p.ipi, "
            + "p.inicio, "
            + "p.conclusion, "
            + "(SELECT COUNT(v) FROM variables_enty v WHERE v.acronimo = p.acronimo)"
            + ") "
            + "FROM procesos_enty p "
            + "WHERE LOWER(p.unidad) = LOWER(:unidad) "
            + "ORDER BY p.proceso ASC")
    List<procesos_dto> findProcesosConConteoVariablesByUnidad(
            @Param("unidad") String unidad);
    
}
