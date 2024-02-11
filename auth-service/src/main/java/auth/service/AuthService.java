package auth.service;

import auth.model.entity.UserEntity;
import auth.model.exception.JwtAuthenticationException;
import auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder,
            final JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserEntity saveUser(final UserEntity user) {
        return userRepository.save(user.setPassword(
                passwordEncoder.encode(user.getPassword())));
    }

    public String generateToken(final String username) {
        try {
            return jwtService.generateToken(username);
        } catch (final Exception e) {
            throw new JwtAuthenticationException("Generating token error", HttpStatus.FORBIDDEN, e);
        }
    }

    public boolean validateToken(final HttpServletRequest request) throws JwtAuthenticationException {
        return jwtService.validateToken(request);
    }
}
