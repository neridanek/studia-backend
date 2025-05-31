package com.example.wsb.application;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public List<ApplicationDTO> getApplications(){
        return applicationService.getAllApplications().stream().map(ApplicationDTO::createFrom).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ApplicationDTO getApplication(
            @PathVariable Integer id
    ){
        return ApplicationDTO.createFrom(applicationService.getApplication(id));
    }

    @PostMapping
    public void createApplication(
            @RequestBody ApplicationCreationRequest request){
        applicationService.createApplication(request);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createApplicationWithFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") Integer userId,
            @RequestParam("jobPostId") Integer jobPostId
    ) {
        applicationService.createApplicationWithFile(file, userId, jobPostId);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(
            @PathVariable("applicationId") Integer id
    ){
        applicationService.deleteApplicationById(id);
    }

    @PatchMapping("/{id}/applicant/{applicantId}")
    public ResponseEntity addApplicantToApplication(
            @PathVariable int id, @PathVariable int applicantId
    ){
        applicationService.addApplicantToApplication(id,applicantId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/Applicant")
    public ResponseEntity removeApplicantFromApplication(
            @PathVariable int id
    ){
        applicationService.removeApplicantFromApplication(id);
        return  ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity changeStatus(
            @PathVariable Integer id,
            @RequestParam("status") ApplicationStatus status
    ) {
        applicationService.changeStatusOfApplication(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public List<ApplicationDTO> getApplicationsForUser(
            @PathVariable Integer userId
    ) {
        return applicationService.getApplicationsForUser(userId).stream().map(ApplicationDTO::createFrom).collect(Collectors.toList());
    }

}
