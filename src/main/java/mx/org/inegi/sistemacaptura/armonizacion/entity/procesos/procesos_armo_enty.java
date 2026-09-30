package mx.org.inegi.sistemacaptura.armonizacion.entity.procesos;

import mx.org.inegi.sistemacaptura.config.DatabaseTables;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = DatabaseTables.PROCESOS_A, schema = DatabaseTables.ARMONIZACION)
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
