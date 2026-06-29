package com.denisa.planificator_zilnic.service;

import com.denisa.planificator_zilnic.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);
    User saveUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    void updateUserRole(Long userId, Long roleId);
    void deleteById(Long id);
}
