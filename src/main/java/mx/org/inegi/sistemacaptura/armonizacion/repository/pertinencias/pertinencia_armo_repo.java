package mx.org.inegi.sistemacaptura.armonizacion.repository.pertinencias;

import java.util.Optional;
import mx.org.inegi.sistemacaptura.armonizacion.entity.pertinencias.pertinencia_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface pertinencia_armo_repo
        extends JpaRepository<pertinencia_armo_enty, Integer> {

    Optional<pertinencia_armo_enty> findByIdA(String idA);
}
