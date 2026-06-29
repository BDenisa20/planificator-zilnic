package com.denisa.planificator_zilnic.service;

import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.repository.TaskRepository;
import com.denisa.planificator_zilnic.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testGetTasksForUser() {

        User user = new User();
        user.setId(1L);

        Task t1 = new Task();
        t1.setTitle("A");

        Task t2 = new Task();
        t2.setTitle("B");

        when(taskRepository.findByUser(user)).thenReturn(Arrays.asList(t1, t2));

        List<Task> tasks = taskService.getTasksForUser(user);

        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getTitle()).isEqualTo("A");
    }

    @Test
    void testGetTaskById() {

        Task task = new Task();
        task.setId(10L);
        task.setTitle("Test");

        when(taskRepository.findById(10L)).thenReturn(Optional.of(task));

        Task found = taskService.getTaskById(10L);

        assertThat(found).isNotNull();
        assertThat(found.getTitle()).isEqualTo("Test");
    }

    @Test
    void testSaveTask() {

        Task task = new Task();
        task.setTitle("New");

        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.saveTask(task);

        assertThat(saved.getTitle()).isEqualTo("New");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTask() {

        taskService.deleteTask(5L);

        verify(taskRepository, times(1)).deleteById(5L);
    }
}
