package com.denisa.planificator_zilnic.repository;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByUsername() {

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setUsername("denisa");
        user.setPassword("1234");
        user.setRole(role);
        userRepository.save(user);

        Optional<User> found = userRepository.findByUsername("denisa");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("denisa");
    }
}
