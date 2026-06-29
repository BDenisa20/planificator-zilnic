package com.denisa.planificator_zilnic.repository;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindByUser() {

        Role role = new Role();
        role.setName("ROLE_USER");
        role = roleRepository.save(role);

        User user = new User();
        user.setUsername("denisa");
        user.setPassword("1234");
        user.setRole(role);
        user = userRepository.save(user);

        Task task = new Task();
        task.setTitle("Test");
        task.setDescription("desc");
        task.setDueDate(LocalDate.now());
        task.setCompleted(false);
        task.setUser(user);

        taskRepository.save(task);

        List<Task> tasks = taskRepository.findByUser(user);

        assertThat(tasks).hasSize(1);
        assertThat(tasks.get(0).getTitle()).isEqualTo("Test");
    }
}
