package com.example.wsb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJPADataAccessService implements UserDao {

    private final UserRepository userRepository;

    @Override
    public User selectUserById(Integer id) {
        return userRepository.getById(id);
    }

    @Override
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existsPersonWithEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public void deleteUserById(Integer userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsPersonWithId(Integer id) {
        return userRepository.existsUserByUserId(id);
    }

    @Override
    public void updateUser(User update) {
        userRepository.save(update);
    }

    @Override
    public List<User> findAllUsersWithLeftJoinFetch() {
        return userRepository.findAll();
    }


}
