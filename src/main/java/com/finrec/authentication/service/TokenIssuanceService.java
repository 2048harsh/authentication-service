package com.finrec.authentication.service;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenIssuanceService {

    private final JwtEncoder jwtEncoder;

    // Spring auto-wires our asymmetric jwkSource directly into a native Encoder wrapper
    public TokenIssuanceService(JWKSource<SecurityContext> jwkSource) {
        this.jwtEncoder = new NimbusJwtEncoder(jwkSource);
    }

    public String createAsymmetricToken(UserDetails userDetails) {
        Instant now = Instant.now();
        long expirySeconds = 86400; // 24 hours

        // Map user authorities into standard space-separated scope string
        String scope = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://auth-service-pod") // Represents the internal identity signature of this service
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expirySeconds))
                .claim("scope", scope) // Standard OAuth2 claim name for permissions
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
