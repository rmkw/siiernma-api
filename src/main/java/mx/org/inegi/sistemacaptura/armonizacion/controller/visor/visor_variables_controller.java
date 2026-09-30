package mx.org.inegi.sistemacaptura.armonizacion.controller.visor;

import java.util.Map;
import mx.org.inegi.sistemacaptura.armonizacion.service.visor.visor_variables_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/armo/variables/visor")
public class visor_variables_controller {
    @Autowired
    private visor_variables_service service;

    @GetMapping
    public Map<String, Object> listar(@RequestParam Map<String, String> parametros) {
        return service.listar(parametros);
    }

    @GetMapping("/filtros")
    public Map<String, Object> filtros(@RequestParam Map<String, String> parametros) {
        return service.filtros(parametros);
    }
}
