package mx.org.inegi.sistemacaptura.controller.home;

import java.util.LinkedHashMap;
import java.util.Map;
import mx.org.inegi.sistemacaptura.armonizacion.repository.variables.variables_armo_repo;
import mx.org.inegi.sistemacaptura.repository.fuentes.fuentes_repo;
import mx.org.inegi.sistemacaptura.repository.procesos.procesos_repo;
import mx.org.inegi.sistemacaptura.repository.variables.variables_repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/resumen")
public class resumen_count_controller {

    @Autowired private procesos_repo procesosRepo;
    @Autowired private fuentes_repo fuentesRepo;
    @Autowired private variables_repo variablesRepo;
    @Autowired private variables_armo_repo variablesArmoRepo;

    @GetMapping("/count")
    @Transactional(readOnly = true)
    public Map<String, Long> contar() {
        Map<String, Long> totales = new LinkedHashMap<>();
        totales.put("procesos", procesosRepo.count());
        totales.put("fuentes", fuentesRepo.count());
        totales.put("variables", variablesRepo.count());
        totales.put("prioritarias", variablesRepo.countByPrioridad(1));
        totales.put("armonizadas", variablesArmoRepo.count());
        return totales;
    }
}
