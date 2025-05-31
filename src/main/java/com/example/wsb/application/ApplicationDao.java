package com.example.wsb.application;

import java.util.List;

public interface ApplicationDao {
    List<Application> selectAllApplications();
    Application selectApplicationById(Integer id);
    //List<Application> selectApplicationsByUserId(Integer userId);
    void insertApplication(Application application);
    boolean existsApplicationWithId(Integer id);
    void deleteApplicationById(Integer applicationId);
    void updateApplication(Application update);
}
