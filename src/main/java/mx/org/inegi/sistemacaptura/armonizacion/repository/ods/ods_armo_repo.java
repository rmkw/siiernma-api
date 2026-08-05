package mx.org.inegi.sistemacaptura.armonizacion.repository.ods;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.ods.ods_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ods_armo_repo extends JpaRepository<ods_armo_enty, Integer> {

    List<ods_armo_enty> findByIdAOrderByIdUniqueAsc(String idA);

    long countByIdA(String idA);
}
