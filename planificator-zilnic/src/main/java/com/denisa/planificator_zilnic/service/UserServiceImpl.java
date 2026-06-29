package com.denisa.planificator_zilnic.service;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.repository.RoleRepository;
import com.denisa.planificator_zilnic.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public User saveUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username deja folosit");
        }

        Role viewerRole = roleRepository.findByName("ROLE_VIEWER")
                .orElseThrow(() -> new RuntimeException("Rolul VIEWER nu există"));

        user.setRole(viewerRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() { return userRepository.findAll(); }

    @Override
    public User getUserById(Long id) { return userRepository.findById(id).orElse(null); }

    @Override
    public void updateUserRole(Long userId, Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);

        if (user != null && role != null) {
            user.setRole(role);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteById(Long id) { userRepository.deleteById(id); }
}
