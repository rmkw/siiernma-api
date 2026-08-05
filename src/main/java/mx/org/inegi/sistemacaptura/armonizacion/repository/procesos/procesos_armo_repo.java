package mx.org.inegi.sistemacaptura.armonizacion.repository.procesos;

import mx.org.inegi.sistemacaptura.armonizacion.entity.procesos.procesos_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface procesos_armo_repo
        extends JpaRepository<procesos_armo_enty, String> {
}
