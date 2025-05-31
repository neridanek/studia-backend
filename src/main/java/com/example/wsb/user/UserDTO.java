package com.example.wsb.user;

public record UserDTO(Integer userId,String firstName,String lastName,String login,String password,String email,Role role) {
    public static UserDTO createFrom(User user){
        return new UserDTO(user.getUserId(),user.getFirstName(),user.getLastName(),user.getLogin(),user.getPassword(),user.getEmail(),user.getRole());
    }
}
