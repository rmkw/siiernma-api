package mx.org.inegi.sistemacaptura.armonizacion.repository.mdea;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.mdea.mdea_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface mdea_armo_repo extends JpaRepository<mdea_armo_enty, Integer> {

    List<mdea_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);

    long countByIdA(String idA);
}
