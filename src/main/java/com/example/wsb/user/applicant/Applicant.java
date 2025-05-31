package com.example.wsb.user.applicant;

import com.example.wsb.application.Application;
import com.example.wsb.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("applicant")
@SuperBuilder
public class Applicant extends User {

    @OneToMany(mappedBy = "applicant")
    @ToString.Exclude
    private List<Application> applications;

}
