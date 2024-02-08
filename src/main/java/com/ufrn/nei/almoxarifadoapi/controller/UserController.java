package com.ufrn.nei.almoxarifadoapi.controller;

import com.ufrn.nei.almoxarifadoapi.dto.mapper.UserMapper;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserCreateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserPasswordUpdateDTO;
import com.ufrn.nei.almoxarifadoapi.dto.user.UserResponseDTO;
import com.ufrn.nei.almoxarifadoapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserCreateDTO createDTO) {
        UserResponseDTO response = userService.save(createDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        UserResponseDTO response = UserMapper.toResponseDTO(userService.findById(id));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/query")
    public ResponseEntity<UserResponseDTO> findByEmail(@RequestParam String email) {
        UserResponseDTO user = userService.findByEmail(email);

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@RequestBody @Valid UserPasswordUpdateDTO passwordUpdateDTO,
                                               @PathVariable Long id) {
        userService.updatePassword(passwordUpdateDTO.getCurrentPassword(),
                passwordUpdateDTO.getNewPassword(), passwordUpdateDTO.getConfirmPassword(), id);

        return ResponseEntity.ok().build();
    }
}
