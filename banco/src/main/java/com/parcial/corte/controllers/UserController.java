package com.parcial.corte.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.parcial.corte.entity.AccountDTO;
import com.parcial.corte.entity.User;
import com.parcial.corte.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return userService.getAllAccounts();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/accounts/{numeroCuenta}")
    public AccountDTO getUserByNumeroCuenta(@PathVariable String numeroCuenta) {
        return userService.getUserAccount(numeroCuenta);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam double amount) {

        String result = userService.transfer(fromAccount, toAccount, amount);

        // Si la transferencia fue exitosa o fallida
        if (result.contains("éxito")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(result);
        }
    }

    @GetMapping("/myAccount")
    public ResponseEntity<AccountDTO> getMyAccount() {
        // Obtener el nombre de usuario actual autenticado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Buscar el usuario por nombre de cuenta (username)
        AccountDTO account = userService.getAccountByUsername(username);

        if (account != null) {
            return ResponseEntity.ok(account);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

class UserDto {
    private String username;
    private String password;
    private String role;

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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {

        this.role = role;
    }

}