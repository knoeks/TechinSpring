package lt.techin.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// nurodo kad cia beanas (ensusije tiesiogiai su Spring Sec)
@Configuration
// nurodo kad cia SecurityFilterChain - Spring Sec konfiguracija
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            //ijungtas by default ir
            // uzkomentuot nepades, reikia disablint
            // DISABLE ONLY FOR DEV PURPOSES!!!!!
            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())

            // cia galime nurody kas turi prieiga prie kokiu kokiu metodu ir prie kokiu endpointu
            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")

                            // cia padaryt prieigos taska kad galetu uzsiregistruoti useris
                            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                            .anyRequest().authenticated()
            );
    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
