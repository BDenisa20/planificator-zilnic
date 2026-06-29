package com.denisa.planificator_zilnic.service;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.repository.RoleRepository;
import com.denisa.planificator_zilnic.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testFindByUsername() {

        User user = new User();
        user.setUsername("denisa");

        when(userRepository.findByUsername("denisa")).thenReturn(Optional.of(user));

        User found = userService.findByUsername("denisa");

        assertThat(found).isNotNull();
        assertThat(found.getUsername()).isEqualTo("denisa");
    }

    @Test
    void testSaveUser() {

        Role role = new Role();
        role.setName("ROLE_USER");

        User user = new User();
        user.setUsername("denisa");
        user.setPassword("1234");
        user.setRole(role);

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.saveUser(user);

        assertThat(saved.getUsername()).isEqualTo("denisa");
        verify(userRepository, times(1)).save(user);
    }

}
