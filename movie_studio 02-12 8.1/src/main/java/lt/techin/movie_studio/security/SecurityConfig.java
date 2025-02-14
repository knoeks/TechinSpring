package lt.techin.movie_studio.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("classpath:app.pub")
  private RSAPublicKey publicKey;

  @Value("classpath:app.key")
  private RSAPrivateKey privateKey;

  @Bean

  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
            .csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults())

            .authorizeHttpRequests(authorize ->
                    authorize
                            .requestMatchers(HttpMethod.GET, "/api/movies").hasAuthority("SCOPE_ROLE_USER")

                            .requestMatchers(HttpMethod.GET, "/api/actors").hasAuthority("SCOPE_ROLE_USER")

                            .requestMatchers(HttpMethod.GET, "/api/screenings").hasAuthority("SCOPE_ROLE_USER")

                            .requestMatchers(HttpMethod.POST, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/movies").hasAuthority("SCOPE_ROLE_ADMIN")

                            .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("SCOPE_ROLE_ADMIN")

                            .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("SCOPE_ROLE_USER")
                            .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("SCOPE_ROLE_USER")
                            .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("SCOPE_ROLE_USER")

                            .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                            .anyRequest().authenticated())
            .oauth2ResourceServer(o -> o.jwt(Customizer.withDefaults()))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions

                    //sitie priklauso tik java 17 versijai
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
            );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
    JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));

    return new NimbusJwtEncoder(jwkSource);
  }
}
