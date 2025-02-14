package lt.techin.demo.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

// Nesusiję tiesiogiai su Spring Security; reikalinga nurodyti Spring'ui, jog čia yra Bean'ų
@Configuration

// Reikalinga tam, kad nurodyti jog čia yra SecurityFilterChain; konfigūracija Spring Security
@EnableWebSecurity
public class SecurityConfig {

  // Used for verifying JWT tokens
  @Value("${jwt.public.key}")
  private RSAPublicKey key;

  // Used for signing JWT tokens
  @Value("${jwt.private.key}")
  private RSAPrivateKey priv;

  // Sukuriame Bean'ą kontekste (IoC container)
  // Nenaudojant šios anotacijos, neveiks mūsų custom filtras
  //  @Bean

  // filterChain - kuriame savo, custom filtrą. Apsauginis tikrintojas - nepraleis prie Controller,
  // jei neatitinka šios apsaugos
//  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//    http
//            // Cross-site request forgery
//            // Customizer - funkcinis interfeisas
//            // Užkomentuoti .csrf neužteks - reikia lambdos
//            // Galima išjungti, tik development tikslams
//            .csrf(c -> c.disable())
//
//            // Basic auth funkcionalumas
//            .httpBasic(Customizer.withDefaults())
//
//            // Forma kurią galime matyti. Galima išjungti arba palikti
//            // .formLogin(Customizer.withDefaults())
//
//            .authorizeHttpRequests(authorize -> authorize
//                    .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
//
//                    // Gali būti ir kitų taisyklių
//                    .requestMatchers(HttpMethod.GET, "/api/books").hasRole("ADMIN")
//
//                    // Leidžiama daryti bet kam, net neautintifikuotui klientui, į šį endpoint
//                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
//
//                    // Visi kiti endpoint'ai uždari
//                    .anyRequest().authenticated()
//            );
//
//    return http.build();
//  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests((authorize) -> authorize

                    // Naudojame hasAuthority, nes kuriami SCOPE. Kitaip tariant, jei naudosime
                    // hasRole, Spring Security tikėsis, jog rolės prasides žodžiu ROLE, nors iš
                    // tikrųjų gaunasi SCOPE_ROLE_ADMIN
                    .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("SCOPE_ROLE_ADMIN")
                    // ...
                    .anyRequest().authenticated()
            )
            .csrf(c -> c.disable())

            // Kodėl?
            // Tam, kad galėtume siųsti username ir password, į /token endpoint
            .httpBasic(Customizer.withDefaults())
            // deprecated, had to update below
            // .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)

            // Configures the app as an OAuth 2.0 resource server, expecting requests to have
            // JWT tokens for authentication
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))

            // Makes the app stateless by disabling session creation
            // JWT authentication does not rely on sessions (tokens contain all needed information)
            .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling((exceptions) -> exceptions

                    // BearerTokenAuthenticationEntryPoint() returns a 401 Unauthorized response
                    // when authentication fails
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())

                    // BearerTokenAccessDeniedHandler() returns a 403 Forbidden when access
                    // is denied
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );

    return http.build();
  }

  @Bean
  // Reikia įgyvendinti, norint patenkinti
  // 1-ą kontraktą; 2 kontraktas yra UserDetailsService
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Creates a JWT decoder that uses the public key to verify incoming JWTs
  // Ensures that only tokens signed with the corresponding private key are valid
  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(key).build();
  }

  // Creates a JWT encoder that signs tokens using the private key
  // 1. Builds a JSON Web Key (JWK) with the public and private keys
  // 2. Stores the key in an immutable JWK set
  // 3. Uses NimbusJwtEncoder to sign and generate JWTs
  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(key).privateKey(priv).build();
    JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwks);
  }
}