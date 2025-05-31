package com.example.wsb.jobpost;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/jobposts")
@RequiredArgsConstructor
public class JobPostController {
    private final JobPostService jobPostService;

    @GetMapping
    public List<JobPostDTO> getJobPosts(){
        return jobPostService.getAllJobPosts().stream().map(JobPostDTO::createFrom).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public JobPostDTO getJobPost(
            @PathVariable("id") Integer jobPostId
    ){
        return JobPostDTO.createFrom(jobPostService.getJobPost(jobPostId));
    }

    @PostMapping
    public CreateJobPostResponse createJobPost(
            @RequestBody JobPostCreationRequest request) throws IOException {
        return jobPostService.createJobPost(request);

    }

    @DeleteMapping("/{id}")
    public void deleteJobPost(
            @PathVariable("id") Integer jobPostId
    ){
        jobPostService.deleteJobPostById(jobPostId);
    }

    @PutMapping("/{id}")
    public ResponseEntity editJobPost(
            @PathVariable("id") Integer jobPostId,
            @RequestBody JobPostCreationRequest request
    ){
       jobPostService.editJobPost(jobPostId,request);
       return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/employer/{employerId}")
    public ResponseEntity addEmployerToJobPost(
            @PathVariable int id, @PathVariable int employerId
    ){
        jobPostService.addEmployerToJobPost(id,employerId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/employer")
    public ResponseEntity removeEmployerFromJobPost(
            @PathVariable int id
    ){
        jobPostService.removeEmployerFromJobPost(id);
        return ResponseEntity.ok().build();
    }

}
