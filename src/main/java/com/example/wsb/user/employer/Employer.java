package com.example.wsb.user.employer;

import com.example.wsb.jobpost.JobPost;
import com.example.wsb.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@DiscriminatorValue("employer")
public class Employer extends User {

    @OneToMany(mappedBy = "employer")
    private List<JobPost> jobPosts;

}
