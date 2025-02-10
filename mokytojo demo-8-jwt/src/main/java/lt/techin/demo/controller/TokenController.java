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

  @Autowired
  public TokenController(JwtEncoder encoder) {
    this.encoder = encoder;
  }

  @PostMapping("/token")
  public String token(Authentication authentication) {
    // Dabartinis laikas
    Instant now = Instant.now();
    long expiry = 36000L;

    // Rolės pateiktos vientisu string'u. Tai reikalinga tam, kad serveris nuskaitytu roles iš JWT
    String scope = authentication.getAuthorities().stream()
            .map(s -> s.getAuthority())
            .collect(Collectors.joining(" "));

    JwtClaimsSet claims = JwtClaimsSet.builder()
            // Norodome, kad mūsu serveris išdavė JWT
            // Alternativos OAuth2 provideriai
            .issuer("self")
            // Išdavimo laikas
            .issuedAt(now)
            // Galiojimo laikas
            .expiresAt(now.plusSeconds(expiry))
            // Vartotoja identifikuojantis info
            .subject(authentication.getName())
            // Vartotojo informacija, galima papildyti prirašant daugiau .claim("key", value)
            .claim("scope", scope)
            .build();

    return encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }
}
