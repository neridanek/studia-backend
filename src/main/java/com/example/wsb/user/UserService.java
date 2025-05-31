package com.example.wsb.user;

import com.example.wsb.exception.DuplicateResourceException;
import com.example.wsb.exception.RequestValidationException;
import com.example.wsb.exception.ResourceNotFoundException;
import com.example.wsb.jobpost.JobPost;
import com.example.wsb.jobpost.JobPostRepository;
import com.example.wsb.security.auth.RegisterRequest;
import com.example.wsb.user.applicant.Applicant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserDao userDao;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userDao.findAllUsersWithLeftJoinFetch();
    }

    public User getUser(Integer id) {
        return userDao.selectUserById(id);
    }

    public void addUser(RegisterRequest userRegistrationRequest) {
        String email = userRegistrationRequest.getEmail();
        if (userDao.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("email already taken");
        }
        User user = Applicant.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .login(userRegistrationRequest.getLogin())
                .password(passwordEncoder.encode(userRegistrationRequest.getPassword()))
                .email(userRegistrationRequest.getEmail())
                .role(userRegistrationRequest.getRole())
                .build();

        userDao.insertUser(user);
    }

    public void deleteUserById(Integer userId) {
        if (!userDao.existsPersonWithId(userId)) {
            throw new ResourceNotFoundException(
                    "user with id [%s] not found".formatted(userId)
            );
        }
        userDao.deleteUserById(userId);
    }

    public void updateUser(Integer userId, UserUpdateRequest updateRequest) {
        User user = getUser(userId);

        boolean changes = false;

        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(updateRequest.getFirstName());
            changes = true;
        }

        if (updateRequest.getLastName() != null && !updateRequest.getLastName().equals(user.getLastName())) {
            user.setLastName(updateRequest.getLastName());
            changes = true;
        }

        if (updateRequest.getLogin() != null && !updateRequest.getLogin().equals(user.getUsername())) {
            user.setLogin(updateRequest.getLogin());

            changes = true;
        }

        if (updateRequest.getPassword() != null && !updateRequest.getPassword().equals(user.getPassword())) {

            String encryptedPassword = passwordEncoder.encode(updateRequest.getPassword());
            user.setPassword(encryptedPassword);

            changes = true;
        }

        if (updateRequest.getEmail() != null && !updateRequest.getEmail().equals(user.getEmail())) {
            if (userDao.existsPersonWithEmail(updateRequest.getEmail())) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }
            user.setEmail(updateRequest.getEmail());
            changes = true;
        }

        if (!changes) {
            throw new RequestValidationException("no user changes found");
        }
        userRepository.save(user);
    }


    public Optional<User> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }
}
