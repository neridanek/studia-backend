package com.example.wsb.user;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserUpdateRequest{

    String firstName;
    String lastName;
    String login;
    String password;
    String email;

}


