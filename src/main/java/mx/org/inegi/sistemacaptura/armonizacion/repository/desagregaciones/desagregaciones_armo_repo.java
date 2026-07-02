package mx.org.inegi.sistemacaptura.armonizacion.repository.desagregaciones;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desagregaciones.desagregaciones_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface desagregaciones_armo_repo
        extends JpaRepository<desagregaciones_armo_enty, Integer> {

    List<desagregaciones_armo_enty>
            findByIdTabuladoOrderByIdUniqueAsc(String idTabulado);
}
