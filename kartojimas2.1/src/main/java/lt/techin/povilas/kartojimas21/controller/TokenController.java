package lt.techin.povilas.kartojimas21.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
  private final JwtEncoder jwtEncoder;

  @Autowired
  public TokenController(JwtEncoder jwtEncoder) {
    this.jwtEncoder = jwtEncoder;
  }

  @PostMapping("/token")
  public String token(Authentication authentication) {
    Instant now = Instant.now();
    long expiry = 36000L;

    // cia paverciame roles i viena dideli stringa kuri jwt supras
    String scope = authentication.getAuthorities().stream()
            .map(s -> s.getAuthority())
            .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plusSeconds(expiry))
            .subject(authentication.getName())
            .claim("scope", scope)
            .build();

    return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
