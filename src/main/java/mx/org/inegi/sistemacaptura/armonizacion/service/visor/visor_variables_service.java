package mx.org.inegi.sistemacaptura.armonizacion.service.visor;

import java.util.Map;

public interface visor_variables_service {
    Map<String, Object> listar(Map<String, String> parametros);
    Map<String, Object> filtros(Map<String, String> parametros);
}
