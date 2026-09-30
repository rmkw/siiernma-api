package mx.org.inegi.sistemacaptura.armonizacion.repository.clasificadores;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.clasificadores.clasificadores_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface clasificadores_armo_repo
        extends JpaRepository<clasificadores_armo_enty, Integer> {

    List<clasificadores_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);
}
