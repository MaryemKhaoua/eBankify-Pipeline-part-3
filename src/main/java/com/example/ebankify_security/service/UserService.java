package com.example.ebankify_security.service;

import com.example.ebankify_security.domain.entities.Role;
import com.example.ebankify_security.dto.UserAuthDto;
import com.example.ebankify_security.dto.UserDto;
import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.requests.LoginRequest;
import com.example.ebankify_security.domain.requests.RegisterRequest;
import com.example.ebankify_security.domain.requests.UserRequest;
import com.example.ebankify_security.exeption.EmailAlreadyInUseException;
import com.example.ebankify_security.exeption.InvalidCredentialsException;
import com.example.ebankify_security.repository.RoleRepository;
import com.example.ebankify_security.security.JwtService;
import com.example.ebankify_security.exeption.UserNotFoundException;
import com.example.ebankify_security.mapper.UserMapper;
import com.example.ebankify_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    public UserAuthDto register(RegisterRequest registerRequest) {
        try {
            System.out.println("Registering user: " + registerRequest);
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
                throw new EmailAlreadyInUseException("Email already in use");
            }

            User user = User.builder()
                    .name(registerRequest.getName())
                    .age(registerRequest.getAge())
                    .email(registerRequest.getEmail())
                    .active(registerRequest.isActive())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .monthlyIncome(registerRequest.getMonthlyIncome())
                    .creditScore((registerRequest.getCreditScore()))
                    .roles(new HashSet<>())
                    .build();

            Role roles = roleRepository.findById(registerRequest.getRoles()).isPresent() ? roleRepository.findById(registerRequest.getRoles()).get():null;


            user.getRoles().add(roles);
            User savedUser = userRepository.save(user);
            Map<String, Object> extraClaims = Map.of("userId", savedUser.getId());
            String token = jwtService.generateToken(extraClaims, savedUser);
            UserAuthDto userDto = userMapper.toUserAuthDto(savedUser);
            userDto.setToken(token);
            return userDto;
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
            throw new RuntimeException("Registration failed", e);
        }
    }
    public UserAuthDto login(LoginRequest loginRequest)  {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        User user = userOptional.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password.");
        }
        Map<String, Object> extraClaims = Map.of("userId", user.getId());
        String token = jwtService.generateToken(extraClaims, user);

        UserAuthDto userDto = userMapper.toUserAuthDto(user);
        userDto.setToken(token);

        return userDto;

    }

    public UserDto save(UserRequest userRequest) {
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("Email already in use");
        }
        userRequest.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        userRequest.setActive(true);
        User user = userMapper.toEntity(userRequest);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }

    public UserDto findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return userMapper.toDto(userOptional.get());
    }

    public void deleteById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        System.out.println("id pour supprimer "+id);
        userRepository.deleteById(id);
    }

    public UserDto update(Long id, UserRequest userRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.setEmail(userRequest.getEmail());
        user.setActive(userRequest.isActive());
        user.setMonthlyIncome(userRequest.getMonthlyIncome());
        user.setCreditScore(userRequest.getCreditScore());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(userRequest.getPassword(), BCrypt.gensalt()));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public void blockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        user.setActive(false);
        userRepository.save(user);
    }

    public void unblockUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOptional.get();
        user.setActive(true);
        userRepository.save(user);
    }
}