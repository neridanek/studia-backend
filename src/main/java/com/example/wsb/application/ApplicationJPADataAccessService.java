package com.example.wsb.application;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationJPADataAccessService implements ApplicationDao{

    private final ApplicationRepository applicationRepository;

    public ApplicationJPADataAccessService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public List<Application> selectAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application selectApplicationById(Integer id) {
        return applicationRepository.getById(id);
    }



    @Override
    public void insertApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public boolean existsApplicationWithId(Integer id) {
        return applicationRepository.existsApplicationByApplicationId(id);
    }

    @Override
    public void deleteApplicationById(Integer applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    @Override
    public void updateApplication(Application update) {
        applicationRepository.save(update);
    }
}
