package com.example.wsb.jobpost;

import com.example.wsb.exception.ResourceNotFoundException;
import com.example.wsb.user.User;
import com.example.wsb.user.UserRepository;
import com.example.wsb.user.employer.EmployerRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Builder
@Service
@RequiredArgsConstructor
public class JobPostService {

    private final JobPostDao jobPostDao;
    private final EmployerRepository employerRepository;
    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;


    public CreateJobPostResponse createJobPost(JobPostCreationRequest creationRequest) throws IOException {

        JobPost jobPost = JobPost.builder()
                .title(creationRequest.title())
                .description(creationRequest.description())
                .requirements(creationRequest.requirements())
                .salary(creationRequest.salary())
                .build();

        JobPost savedJobPost = jobPostRepository.saveAndFlush(jobPost);
        return CreateJobPostResponse.builder().jobId(savedJobPost.getJobId()).build();
    }

    public void deleteJobPostById(Integer jobPostId) {
        if (!jobPostDao.existsJobPostWithId(jobPostId)) {
            throw new ResourceNotFoundException(
                    "JobPost with id [%s] not found".formatted(jobPostId)
            );
        }
        jobPostDao.deleteJobPostById(jobPostId);
    }

    public void addEmployerToJobPost(int id, int employerId) {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow();
        User employer = userRepository.findById(employerId).orElseThrow();
        jobPost.addEmployer(employer);
        jobPostRepository.saveAndFlush(jobPost);
    }

    public void removeEmployerFromJobPost(int id) {
        JobPost jobPost = jobPostRepository.findById(id).orElseThrow();
        jobPost.removeEmployer();
    }

    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }

    public JobPost getJobPost(Integer jobPostId) {
        return jobPostRepository.findById(jobPostId).orElseThrow();
    }


    public void editJobPost(Integer jobPostId,JobPostCreationRequest request) {
        JobPost jobPost = jobPostRepository.findById(jobPostId).orElseThrow();
        jobPost.setDescription(request.description());
        jobPost.setSalary(request.salary());
        jobPost.setTitle(request.title());
        jobPost.setRequirements(request.requirements());
        jobPostRepository.save(jobPost);
    }
}
