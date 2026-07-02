package mx.org.inegi.sistemacaptura.armonizacion.repository.tabulados;

import java.util.List;
import mx.org.inegi.sistemacaptura.armonizacion.entity.tabulados.tabulados_armo_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface tabulados_armo_repo
        extends JpaRepository<tabulados_armo_enty, String> {

    List<tabulados_armo_enty>
            findByIdTabuladoStartingWithOrderByIdTabuladoAsc(
                    String prefijo);
}
