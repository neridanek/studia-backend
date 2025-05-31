package com.example.wsb.application;

public record ApplicationDTO(
        Integer applicationId,
        ApplicationStatus status,
        Integer userId,
        Integer jobId,
        String fileName
) {
    public static ApplicationDTO createFrom(Application application) {
        return new ApplicationDTO(
                application.getApplicationId(),
                application.getStatus(),
                application.getApplicant().getUserId(),
                application.getJobPost().getJobId(),
                application.getFile().getFileName()

        );
    }
}
