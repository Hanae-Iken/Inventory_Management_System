package ma.ensat.backend.controller;

import jakarta.validation.Valid;
import ma.ensat.backend.dto.AuthResponseDto;
import ma.ensat.backend.dto.LoginDto;
import ma.ensat.backend.dto.RegisterDto;
import ma.ensat.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponseDto response = userService.authenticate(loginDto);

        if (response.getToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Authentication endpoint is working!");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {
        try {
            AuthResponseDto response = userService.registerCustomer(registerDto);
            if (response.getToken() != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new AuthResponseDto(null, null, null, e.getMessage()));
        }
    }
}