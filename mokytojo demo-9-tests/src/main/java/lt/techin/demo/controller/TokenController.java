package lt.techin.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TokenController {

  private JwtEncoder encoder;

  // Injects a JwtEncoder bean, which is responsible for creating JWT tokens
  // This JwtEncoder was previously configured in SecurityConfig
  @Autowired
  public TokenController(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @PostMapping("/token")
  // Receives an Authentication object, which contains details about the currently
  // authenticated user
  // This method generates and returns a JWT token for the authenticated user
  public String token(Authentication authentication) {
    // Gets the current timestamp (Instant.now()) to track when the token was issued
    Instant now = Instant.now();

    // Sets an expiration time (36000L seconds = 10 hours) for the JWT
    long expiry = 36000L;

    // Extracts the user's roles from the Authentication object
    // Converts the roles to a space-separated string (e.g., "ROLE_USER ROLE_ADMIN")
    // This will be stored in the JWT under a custom "scope" claim
    String scope = authentication.getAuthorities().stream()
            .map(s -> s.getAuthority())
            .collect(Collectors.joining(" "));
    System.out.println("My roles: " + scope);

    // Creates a JWT payload (claims set); all metadata
    JwtClaimsSet claims = JwtClaimsSet.builder()

            // Defines who issued the token
            .issuer("self")

            // Timestamp when the token was created
            .issuedAt(now)

            // Expiration timestamp (10 hours later)
            .expiresAt(now.plusSeconds(expiry))

            // The username of the authenticated user
            .subject(authentication.getName())

            // Stores the user's roles/permissions
            .claim("scope", scope)
            .build();

    // Encodes the JWT claims using the JwtEncoder, which signs the token with the private key
    // Returns the JWT as a plain string in the response
    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
