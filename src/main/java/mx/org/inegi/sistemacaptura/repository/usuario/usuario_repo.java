/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.repository.usuario;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import mx.org.inegi.sistemacaptura.entity.usuario.usuario_enty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usuario_repo extends JpaRepository<usuario_enty, Long> {
    usuario_enty findByNombre(String nombre);
}
