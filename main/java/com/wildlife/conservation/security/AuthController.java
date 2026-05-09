package com.wildlife.conservation.security;

import com.wildlife.conservation.dto.AuthRequest;
import com.wildlife.conservation.dto.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "Login and get JWT token")
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authManager, JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        String role = user.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
        String token = tokenProvider.generateToken(user.getUsername(), role);
        return ResponseEntity.ok(new AuthResponse(token, role));
    }
}