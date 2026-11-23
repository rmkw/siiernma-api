package mx.org.inegi.sistemacaptura.config;

import java.util.Arrays;
import mx.org.inegi.sistemacaptura.service.auth.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .securityContext(context -> context.securityContextRepository(securityContextRepository()))
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/api/import-excel/**")
                .access(new WebExpressionAuthorizationManager("hasRole('IMPORT') or hasRole('ADMIN') or hasRole('ROOT') or (hasRole('USER') and !hasRole('ARMO'))"))
                .requestMatchers(HttpMethod.PUT, "/api/variables/revision-prioridad/*", "/api/variables/revision-prioridad-masiva").hasRole("REVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/armo/variables/*/desde-validacion").hasRole("VALIDA")
                .requestMatchers(HttpMethod.POST, "/api/catalog/temas-subtemas/temas").hasRole("ARMO")
                .requestMatchers(HttpMethod.POST, "/api/catalog/temas-subtemas/subtemas").hasRole("ARMO")
                .requestMatchers(HttpMethod.PUT, "/api/armo/variables/*/validacion").hasRole("VALIDA")
                .requestMatchers(
                        "/api/auth/**",
                        "/api/prueba/**",
                        "/api/conexion",
                        "/api/armo/variables/**",
                        "/api/pertinencia/**",
                        "/api/fuentes/**",
                        "/api/variables/**",
                        "/api/mdea/**",
                        "/api/ods/**",
                        "/api/procesos-locales/**",
                        "/api/admin-usuarios/**",
                        "/api/usuarios/**",
                        "/api/catalog/**",
                        "/api/unidad/**",
                        "/api/procesos/**",
                        "/api/sele/**",
                        "/api/armo/**",
                        "/api/armo/fuentes/**"
                ).permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:4200",
                "http://127.0.0.1:4200",
                "http://10.200.130.27:8090",
                "http://10.109.1.27:4200"
        ));

        configuration.setAllowCredentials(true);
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
