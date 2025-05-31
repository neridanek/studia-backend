package com.example.wsb.user.moderator;

import com.example.wsb.user.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@DiscriminatorValue("moderator")
public class Moderator extends User {

}
