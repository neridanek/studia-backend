package com.example.wsb.application;

import com.example.wsb.application.file.File;
import com.example.wsb.application.file.FileRepository;
import com.example.wsb.exception.ResourceNotFoundException;
import com.example.wsb.jobpost.JobPost;
import com.example.wsb.jobpost.JobPostRepository;
import com.example.wsb.user.applicant.Applicant;
import com.example.wsb.user.applicant.ApplicantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationDao applicationDao;
    private final ApplicationRepository applicationRepository;
    private final ApplicantRepository applicantRepository;
    private final FileRepository fileRepository;
    private final JobPostRepository jobPostRepository;



    public List<Application> getAllApplications() {
        return applicationDao.selectAllApplications();
    }

    public Application getApplication(Integer id) {
        return applicationDao.selectApplicationById(id);
    }

    public void createApplication(ApplicationCreationRequest creationRequest) {
        File file = fileRepository.findById(creationRequest.fileId())
                .orElseThrow(() -> new ResourceNotFoundException("File not found"));

        Application application = Application.builder()
                .status(creationRequest.status())
                .file(file)
                .build();

        applicationDao.insertApplication(application);
    }

    public void deleteApplicationById(Integer applicationId) {
        if (!applicationDao.existsApplicationWithId(applicationId)) {
            throw new ResourceNotFoundException(
                    "Application with id [%s] not found".formatted(applicationId)
            );
        }
        applicationDao.deleteApplicationById(applicationId);
    }

    public void addApplicantToApplication(int id, int applicantId){
        Application application = applicationRepository.findById(id).orElseThrow();
        Applicant applicant = applicantRepository.findById(applicantId).orElseThrow();
        application.addApplicant(applicant);
    }

    public void removeApplicantFromApplication(int id){
        Application application = applicationRepository.findById(id).orElseThrow();
        application.removeApplicant();
    }

    public void changeStatusOfApplication(int id ,ApplicationStatus applicationStatus) {
        Application application = applicationRepository.findById(id).orElseThrow();
        application.setStatus(applicationStatus);
        applicationRepository.save(application);
    }

    public void createApplicationWithFile(MultipartFile file, Integer userId, Integer jobPostId) {
        Applicant applicant = applicantRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Applicant not found"));

        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new ResourceNotFoundException("JobPost not found"));

        // hardcoded sciezka do katalogu uploads
        Path uploadDir = Paths.get("C:/Users/wikto/OneDrive/Pulpit/portalpracybackend/uploads");

        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir); //tworzenie katalogu jesli nie istnieje
            } catch (IOException e) {
                throw new RuntimeException("Nie udało się utworzyć katalogu uploads", e);
            }
        }

        // Tworzenie nazwy pliku z unikalnym prefiksem
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // Tworzymy pełną ścieżkę do pliku
        Path filePath = uploadDir.resolve(fileName);

        try {
            // Zapis pliku na dysku w katalogu uploads
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Nie udało się zapisać pliku", e);
        }

        // Utworzenie obiektu File i zapis w bazie danych
        File savedFile = new File();
        savedFile.setFileName(fileName); // Zapisanie nazwy pliku, a nie danych binarnych
        File persistedFile = fileRepository.save(savedFile);

        // Tworzenie aplikacji z przypisaniem użytkownika i statusem `PENDING`
        Application application = Application.builder()
                .status(ApplicationStatus.PENDING)
                .file(persistedFile) // Przypisanie zapisanych szczegółów pliku
                .applicant(applicant)
                .jobPost(jobPost)
                .build();

        applicationDao.insertApplication(application);
    }

    public List<Application> getApplicationsForUser(Integer userId) {
        // Używamy zaktualizowanego zapytania
        return applicationRepository.findByApplicant_UserId(userId);
    }

}
