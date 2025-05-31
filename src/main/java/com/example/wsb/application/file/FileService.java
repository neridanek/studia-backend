package com.example.wsb.application.file;

import com.example.wsb.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File createFile(byte[] data) {
        return fileRepository.save(new File(data));
    }

    public Optional<File> getFileById(Integer id) {
        return fileRepository.findById(id);
    }

//    public File updateFile(Integer id, byte[] data) {
//        return fileRepository.findById(id)
//                .map(file -> {
//                    file.setData(data);
//                    return fileRepository.save(file);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("File not found"));
//    }

    public void deleteFile(Integer id) {
        fileRepository.deleteById(id);
    }
}
