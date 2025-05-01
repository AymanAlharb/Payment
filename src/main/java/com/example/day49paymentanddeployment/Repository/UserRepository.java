package com.example.day49paymentanddeployment.Repository;

import com.example.day49paymentanddeployment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserById(Integer userId);
}
