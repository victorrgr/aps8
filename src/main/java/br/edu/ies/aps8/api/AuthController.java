package br.edu.ies.aps8.api;

import br.edu.ies.aps8.config.security.JwtService;
import br.edu.ies.aps8.dto.JwtToken;
import br.edu.ies.aps8.dto.SignInRequest;
import br.edu.ies.aps8.dto.SignInResponse;
import br.edu.ies.aps8.dto.SignUpRequest;
import br.edu.ies.aps8.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/signin")
    public ResponseEntity<Object> singIn(@RequestBody SignInRequest signInRequest,
                                         HttpServletResponse httpResponse) {
        var username = signInRequest.getUsername();
        var password = signInRequest.getPassword();
        Map.Entry<String, String> invalidCredentials = Map.entry("message", "Invalid Credentials");
        var user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.status(401)
                    .body(invalidCredentials);
        }
        try {
            var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.get(), password));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(invalidCredentials);
        }

        JwtToken jwtToken = jwtService.generateToken(user.get());

        log.info("Expiration Time: {}", jwtToken.expirationTime());
        log.info("Bearer {}", jwtToken.getToken());

        String encodedToken = URLEncoder.encode("Bearer " + jwtToken.getToken(), StandardCharsets.UTF_8);
        Cookie authCookie = new Cookie(HttpHeaders.AUTHORIZATION, encodedToken);
        authCookie.setMaxAge((int)jwtToken.expirationTime().toSeconds());
        authCookie.setPath("/api");
        authCookie.setHttpOnly(true);
        httpResponse.addCookie(authCookie);

        SignInResponse response = SignInResponse.builder()
                .roles(user.get().getRoles())
                .token(jwtToken.getToken())
                .issuedAt(jwtToken.getIssuedAt())
                .expiresIn(jwtToken.getExpiresIn())
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) {
        // TODO: Implement signUp logic
        return ResponseEntity.ok(null);
    }

}
