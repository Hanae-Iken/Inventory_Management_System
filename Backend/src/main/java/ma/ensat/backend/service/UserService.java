package ma.ensat.backend.service;

import ma.ensat.backend.dto.AuthResponseDto;
import ma.ensat.backend.dto.LoginDto;
import ma.ensat.backend.dto.RegisterDto;
import ma.ensat.backend.entity.User;
import ma.ensat.backend.repository.UserRepository;
import ma.ensat.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponseDto authenticate(LoginDto loginDto) {
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
                return new AuthResponseDto(token, user.getUsername(), user.getRole().name(), "Authentication successful");
            }
        }

        return new AuthResponseDto(null, null, null, "Invalid credentials");
    }

    public User createUser(String username, String password, User.Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public AuthResponseDto registerCustomer(RegisterDto registerDto) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new AuthResponseDto(null, null, null, "Username already exists");
        }

        // Créer le nouvel utilisateur customer
        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        newUser.setRole(User.Role.CUSTOMER); // Toujours CUSTOMER pour l'enregistrement public

        userRepository.save(newUser);

        // Générer le token JWT
        String token = jwtUtil.generateToken(newUser.getUsername(), newUser.getRole().name());

        return new AuthResponseDto(token, newUser.getUsername(), newUser.getRole().name(),
                "Registration successful");
    }
}