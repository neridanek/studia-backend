package com.example.wsb.user.admin;

import com.example.wsb.user.User;
import com.example.wsb.user.moderator.Moderator;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@DiscriminatorValue("admin")
public class Admin extends User {


}
