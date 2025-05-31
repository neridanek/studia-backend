package com.example.wsb.jobpost;

import com.example.wsb.user.User;

public record JobPostDTO(
        Integer jobId,
        String title,
        String description,
        String requirements,
        Integer salary,
        User employer
) {
    public static JobPostDTO createFrom(JobPost jobPost) {
        return new JobPostDTO(
                jobPost.getJobId(),
                jobPost.getTitle(),
                jobPost.getDescription(),
                jobPost.getRequirements(),
                jobPost.getSalary(),
                jobPost.getEmployer()
        );
    }
}
