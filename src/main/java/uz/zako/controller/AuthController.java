package uz.zako.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.zako.model.Result;
import uz.zako.payload.AuthTokenPayload;
import uz.zako.payload.UserPayload;
import uz.zako.repository.UserRepository;
import uz.zako.security.AuthService;
import uz.zako.security.JwtTokenProvider;
import uz.zako.security.RefreshTokenUtils;
import uz.zako.security.SecurityUtils;
import uz.zako.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtils securityUtils;
    private final RefreshTokenUtils refreshTokenUtils;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserPayload payload) {
        try {
            return ResponseEntity.ok(authService.createToken(payload));
        } catch (Exception e) {
            log.error("error in login - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(e.getMessage()));
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody AuthTokenPayload payload) {
        try {
            return ResponseEntity.ok(authService.refreshToken(payload));
        } catch (Exception e) {
            log.error("error in refresh token - {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Result.error(e.getMessage()));
        }
    }


}
