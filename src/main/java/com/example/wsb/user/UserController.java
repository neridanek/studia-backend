package com.example.wsb.user;

import com.example.wsb.security.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers().stream().map(UserDTO::createFrom).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UserDTO getUser(
            @PathVariable Integer id
    ) {
        return UserDTO.createFrom(userService.getUser(id));
    }

    @PostMapping
    public void createUser(
            @RequestBody RegisterRequest request) {
        userService.addUser(request);
    }

    @GetMapping("/{login}")
    public UserDTO findUserByLogin(
            @PathVariable String login
    ){
        return UserDTO.createFrom(userService.findUserByLogin(login).orElseThrow());
    }

    @DeleteMapping("/{login}")
    public void deleteUser(
            @PathVariable String login
    ) {
        User user = userService.findUserByLogin(login).orElseThrow();
        userService.deleteUserById(user.getUserId());
    }

    @PutMapping("/{login}")
    public void updateUser(
            @PathVariable String login,
            @RequestBody UserUpdateRequest updateRequest
    ) {
        User user = userService.findUserByLogin(login).orElseThrow();
        userService.updateUser(user.getUserId(), updateRequest);
    }

}
