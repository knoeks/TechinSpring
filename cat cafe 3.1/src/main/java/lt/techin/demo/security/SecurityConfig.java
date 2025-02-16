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

// Nesusiję tiesiogiai su Spring Security; reikalinga nurodyti Spring'ui, jog čia yra Bean'ų
@Configuration

// Reikalinga tam, kad nurodyti jog čia yra SecurityFilterChain; konfigūracija Spring Security
@EnableWebSecurity
public class SecurityConfig {

  // Sukuriame Bean'ą kontekste (IoC container)
  // Nenaudojant šios anotacijos, neveiks mūsų custom filtras
  @Bean

  // filterChain - kuriame savo, custom filtrą. Apsauginis tikrintojas - nepraleis prie Controller,
  // jei neatitinka šios apsaugos
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            // Cross-site request forgery
            // Customizer - funkcinis interfeisas
            // Užkomentuoti .csrf neužteks - reikia lambdos
            // Galima išjungti, tik development tikslams
            .csrf(c -> c.disable())

            // Basic auth funkcionalumas
            .httpBasic(Customizer.withDefaults())

            // Forma kurią galime matyti. Galima išjungti arba palikti
            // .formLogin(Customizer.withDefaults())

            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")

                    // Gali būti ir kitų taisyklių
                    .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")

                    // Leidžiama daryti bet kam, net neautintifikuotui klientui, į šį endpoint
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                    // Visi kiti endpoint'ai uždari
                    .anyRequest().authenticated()
            );

    return http.build();
  }

  @Bean
  // Reikia įgyvendinti, norint patenkinti
  // 1-ą kontraktą
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}