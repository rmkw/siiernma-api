package mx.org.inegi.sistemacaptura.entity.usuario;

import java.util.Set;

public class usuario_admin_dto {
    private Long id;
    private String nombre;
    private String aka;
    private Set<String> roles;

    public usuario_admin_dto() {}

    public usuario_admin_dto(Long id, String nombre, String aka, Set<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.aka = aka;
        this.roles = roles;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAka() { return aka; }
    public void setAka(String aka) { this.aka = aka; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
