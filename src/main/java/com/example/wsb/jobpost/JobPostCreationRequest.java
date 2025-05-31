package com.example.wsb.jobpost;

public record JobPostCreationRequest(
        String title,
        String description,
        String requirements,
        Integer salary
) {
}
