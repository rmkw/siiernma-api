/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.org.inegi.sistemacaptura.controller;

/**
 *
 * @author LUIS.CASTANEDAL
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conexion")
public class ConexionController {
     @Autowired
    private DataSource dataSource;

    @GetMapping
    public Map<String, Object> probarConexion() throws Exception {
        Map<String, Object> respuesta = new LinkedHashMap<String, Object>();

        try (Connection conexion = dataSource.getConnection();
             Statement sentencia = conexion.createStatement();
             ResultSet resultado = sentencia.executeQuery("SELECT 1")) {

            resultado.next();
            respuesta.put("ok", resultado.getInt(1) == 1);
            respuesta.put("mensaje", "Conexion a PostgreSQL exitosa");
        }

        return respuesta;
    }
}
