package com.parcial.corte.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.parcial.corte.entity.User;
import com.parcial.corte.services.JwtUtil;
import com.parcial.corte.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest) {
        // Buscar usuario por nombre de usuario
        Optional<User> userOptional = userService.findByUsername(authRequest.getUsername());

        // Verificar si el usuario existe y si la contraseña es correcta
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean passwordValid = userService.checkPassword(user, authRequest.getPassword());

            if (passwordValid) {
                // Generar token JWT si la autenticación es exitosa
                String token = jwtUtil.generateToken(authRequest.getUsername());
                return ResponseEntity.ok(token);
            }
        }

        // Si la autenticación falla
        return ResponseEntity.status(401).body("Invalid username or password");
    }


}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
