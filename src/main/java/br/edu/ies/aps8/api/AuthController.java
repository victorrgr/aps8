package br.edu.ies.aps8.api;

import br.edu.ies.aps8.config.security.JwtService;
import br.edu.ies.aps8.dto.*;
import br.edu.ies.aps8.exception.InvalidCredentialsException;
import br.edu.ies.aps8.model.Role;
import br.edu.ies.aps8.model.User;
import br.edu.ies.aps8.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @ExceptionHandler({InvalidCredentialsException.class})
    public ResponseEntity<StandardResponse> handler(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(StandardResponse.builder()
                        .message(e.getMessage())
                        .build());
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> singIn(@RequestBody SignInRequest signInRequest,
                                                 HttpServletResponse httpResponse) throws InvalidCredentialsException {
        var user = userRepository.findByUsername(signInRequest.getUsername());
        if (user.isEmpty()) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        try {
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get(), signInRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        JwtToken jwtToken = jwtService.generateToken(user.get());

        log.info("Expiration Time: {}", jwtToken.expirationTime());
        log.info("Bearer {}", jwtToken.getToken());

        String encodedToken = URLEncoder.encode("Bearer " + jwtToken.getToken(), StandardCharsets.UTF_8);
        Cookie authCookie = new Cookie(HttpHeaders.AUTHORIZATION, encodedToken);
        authCookie.setMaxAge((int) jwtToken.expirationTime().toSeconds());
        authCookie.setPath("/api");
        authCookie.setHttpOnly(true);
        httpResponse.addCookie(authCookie);

        return ResponseEntity.ok(SignInResponse.builder()
                .roles(user.get().getRoles())
                .token(jwtToken.getToken())
                .issuedAt(jwtToken.getIssuedAt())
                .expiresIn(jwtToken.getExpiresIn())
                .build());
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) throws InvalidCredentialsException {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            throw new InvalidCredentialsException("There's already an user with the informed username");
        }

        userRepository.save(User.builder()
                .username(signUpRequest.getUsername())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .roles(List.of(Role.USER))
                .build());

        return ResponseEntity.created(URI.create("/api/auth/signin"))
                .build();
    }

}
