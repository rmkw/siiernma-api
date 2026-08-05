package mx.org.inegi.sistemacaptura.armonizacion.entity.procesos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "procesos_a", schema = "public")
public class procesos_armo_enty {

    @Id
    @Column(name = "acronimo")
    private String acronimo;

    public String getAcronimo() {
        return acronimo;
    }

    public void setAcronimo(String acronimo) {
        this.acronimo = acronimo;
    }
}
