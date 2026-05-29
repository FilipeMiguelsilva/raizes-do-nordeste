package com.raizes_do_nordeste.api.controller;

import com.raizes_do_nordeste.api.infrastructure.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

//recebe email e senha e devolve o token
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public record LoginResponse(String accessToken, String tokenType, String role) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credenciais.get("email"),
                            credenciais.get("senha")
                    )
            );

            String role = auth.getAuthorities()
                    .iterator().next()
                    .getAuthority()
                    .replace("ROLE_", "");

            String token = jwtService.gerarToken(auth.getName(), role);

            return ResponseEntity.ok(new LoginResponse(token, "Bearer", role));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", "CREDENCIAIS_INVALIDAS",
                    "message", "E-mail ou senha inválidos.",
                    "path", "/auth/login"
            ));
        }
    }
}