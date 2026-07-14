package mx.org.inegi.sistemacaptura.armonizacion.repository.variables_tabulados;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.variables_tabulados.variables_tabulados_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface variables_tabulados_armo_repo
        extends JpaRepository<variables_tabulados_armo_enty, Integer> {

    List<variables_tabulados_armo_enty>
            findByIdTabuladoOrderByIdUniqueAsc(String idTabulado);

    List<variables_tabulados_armo_enty>
            findByIdAOrderByIdUniqueAsc(String idA);

    boolean existsByIdAAndIdTabulado(String idA, String idTabulado);
}
