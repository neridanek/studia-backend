package com.example.wsb.jobpost;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Integer> {

    boolean existsJobPostByJobId(Integer id);
}
