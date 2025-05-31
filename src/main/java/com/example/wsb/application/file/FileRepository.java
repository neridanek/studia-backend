package com.example.wsb.application.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {

    boolean existsFileByFileId(Integer id);
}
