package com.parcial.corte.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parcial.corte.entity.AccountDTO;
import com.parcial.corte.entity.User;
import com.parcial.corte.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<AccountDTO> getAllAccounts() {
        List<User> users = userRepository.findAll();

        // Mapea la lista de usuarios a la lista de AccountDTOs
        return users.stream()
                .map(user -> new AccountDTO(
                        user.getNombre(),
                        user.getApellidos(),
                        user.getNumeroCuenta(),
                        user.getSaldo()))
                .collect(Collectors.toList());
    }

    public AccountDTO getUserAccount(String numeroCuenta) {
        Optional<User> user = userRepository.getUserByNumeroCuenta(numeroCuenta);
        if (!user.isPresent()) {
            return null;
        }
        return new AccountDTO(
                user.get().getNombre(),
                user.get().getApellidos(),
                user.get().getNumeroCuenta(),
                user.get().getSaldo());

    }

    @Transactional
    public String transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        // Buscar el usuario remitente (from) por número de cuenta
        Optional<User> fromUserOpt = userRepository.getUserByNumeroCuenta(fromAccountNumber);
        // Buscar el usuario destinatario (to) por número de cuenta
        Optional<User> toUserOpt = userRepository.getUserByNumeroCuenta(toAccountNumber);

        // Verificar que ambos usuarios existen
        if (!fromUserOpt.isPresent()) {
            return "El remitente con el número de cuenta " + fromAccountNumber + " no existe.";
        }
        if (!toUserOpt.isPresent()) {
            return "El destinatario con el número de cuenta " + toAccountNumber + " no existe.";
        }

        User fromUser = fromUserOpt.get();
        User toUser = toUserOpt.get();

        // Verificar si el saldo del remitente es suficiente para la transferencia
        if (fromUser.getSaldo() < amount) {
            return "El saldo del remitente es insuficiente para realizar la transferencia.";
        }

        // Realizar la transferencia
        fromUser.setSaldo(fromUser.getSaldo() - amount);
        toUser.setSaldo(toUser.getSaldo() + amount);

        // Guardar los cambios en la base de datos
        userRepository.save(fromUser);
        userRepository.save(toUser);

        return "Transferencia realizada con éxito. " + amount + " ha sido transferido de " +
                fromUser.getNombre() + " a " + toUser.getNombre();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByNumeroCuenta(String numeroCuenta) {
        return userRepository.findByNumeroCuenta(numeroCuenta);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean checkPassword(User user, String rawPassword) {
        // Compara la contraseña en texto plano
        return rawPassword.equals(user.getPassword());
    }

    public AccountDTO getAccountByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user != null) {
            return new AccountDTO(user.get().getNombre(), user.get().getApellidos(), user.get().getNumeroCuenta(),
                    user.get().getSaldo());
        }
        return null;
    }
}
