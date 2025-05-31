package com.example.wsb.application;

public record ApplicationCreationRequest(
        ApplicationStatus status,
        Integer fileId
) {
}
