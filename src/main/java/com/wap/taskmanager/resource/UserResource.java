package com.wap.taskmanager.resource;

import com.wap.taskmanager.service.UserService;
import com.wap.taskmanager.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        List<UserDTO> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.findById(userId);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UserDTO> saveOrUpdateUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.saveOrUpdate(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
