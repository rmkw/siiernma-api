/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.entity.auth;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.util.Set;
public class usuario_dto {
    private Long id;
    private String nombre;
    private String aka;
    private Set<String> roles;

    public usuario_dto() {
    }

    public usuario_dto(Long id, String nombre, String aka, Set<String> roles) {
        this.id = id;
        this.nombre = nombre;
        this.aka = aka;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAka() {
        return aka;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
