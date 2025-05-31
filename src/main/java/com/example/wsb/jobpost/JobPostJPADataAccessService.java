package com.example.wsb.jobpost;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JobPostJPADataAccessService implements JobPostDao{
    private final JobPostRepository jobPostRepository;

    public JobPostJPADataAccessService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @Override
    public List<JobPost> selectAllJobPosts() {
        return jobPostRepository.findAll();
    }

    @Override
    public JobPost selectJobPostById(Integer id) {
        return jobPostRepository.getById(id);
    }

    @Override
    public void insertJobPost(JobPost jobPost) {
        jobPostRepository.save(jobPost);
    }

    @Override
    public boolean existsJobPostWithId(Integer id) {
        return jobPostRepository.existsJobPostByJobId(id);
    }

    @Override
    public void deleteJobPostById(Integer jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }

    @Override
    public void updateJobPost(JobPost update) {
        jobPostRepository.save(update);
    }
}
