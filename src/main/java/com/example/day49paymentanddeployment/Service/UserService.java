package com.example.day49paymentanddeployment.Service;

import com.example.day49paymentanddeployment.Api.ApiException;
import com.example.day49paymentanddeployment.Model.User;
import com.example.day49paymentanddeployment.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer userId, User user) {
        User tempUserObject = userRepository.findUserById(userId);
        if (tempUserObject == null) throw new ApiException("User not found.");
        tempUserObject.setName(user.getName());
        tempUserObject.setBalance(user.getBalance());
        userRepository.save(tempUserObject);
    }

    public void deleteUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) throw new ApiException("User not found.");
        userRepository.delete(user);
    }
}
