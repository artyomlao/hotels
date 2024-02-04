package auth.controller;

import auth.model.dto.AuthUserDTO;
import auth.model.entity.UserEntity;
import auth.model.exception.JwtAuthenticationException;
import auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(final AuthService authService, final AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public UserEntity saveUser(final @RequestBody UserEntity user) {
        return authService.saveUser(user);
    }

    @GetMapping("/login")
    public ResponseEntity<String> getToken(final @RequestBody AuthUserDTO user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            return ResponseEntity.ok(authService.generateToken(user.getUsername()));
        } catch (final AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Invalid credentials", FORBIDDEN);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(final HttpServletRequest request) {
        try {
            return ResponseEntity.ok(authService.validateToken(request));
        } catch (final JwtAuthenticationException e) {
            return ResponseEntity.ok(false);
        }
    }
}
