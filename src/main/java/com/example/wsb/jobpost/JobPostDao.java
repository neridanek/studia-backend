package com.example.wsb.jobpost;

import java.util.List;

public interface JobPostDao {
    List<JobPost> selectAllJobPosts();
    JobPost selectJobPostById(Integer id);
    void insertJobPost(JobPost jobPost);
    boolean existsJobPostWithId(Integer id);
    void deleteJobPostById(Integer jobPostId);
    void updateJobPost(JobPost update);
}
