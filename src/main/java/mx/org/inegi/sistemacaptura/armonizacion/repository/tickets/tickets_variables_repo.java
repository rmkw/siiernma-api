package mx.org.inegi.sistemacaptura.armonizacion.repository.tickets;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tickets.tickets_variables_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tickets_variables_repo
        extends JpaRepository<tickets_variables_enty, Long> {

    List<tickets_variables_enty> findByIdAOrderByFechaCreacionDesc(String idA);

    List<tickets_variables_enty> findAllByOrderByFechaCreacionDesc();

    List<tickets_variables_enty> findByEstatusOrderByFechaCreacionDesc(
            String estatus);

    List<tickets_variables_enty>
            findByIdUsuarioAsignadoOrderByFechaCreacionDesc(Long idUsuario);
}
