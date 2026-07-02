package mx.org.inegi.sistemacaptura.armonizacion.repository.desgloses;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.desgloses.desgloses_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface desgloses_armo_repo
        extends JpaRepository<desgloses_armo_enty, Integer> {

    List<desgloses_armo_enty>
            findByIdTabuladoOrderByIdUniqueAsc(String idTabulado);
}
