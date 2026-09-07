package mx.org.inegi.sistemacaptura.entity.usuario;

import java.util.Set;

public class usuario_admin_request_dto {
    private String nombre;
    private String aka;
    private String contrasena;
    private Set<String> roles;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getAka() { return aka; }
    public void setAka(String aka) { this.aka = aka; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
