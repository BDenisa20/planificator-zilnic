package com.denisa.planificator_zilnic.controller;

import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.service.TaskService;
import com.denisa.planificator_zilnic.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "denisa", roles = {"USER"})
    void listTasks_shouldReturnTasksViewForUser() throws Exception {

        Role role = new Role();
        role.setId(2L);
        role.setName("ROLE_USER");

        User mockUser = new User();
        mockUser.setUsername("denisa");
        mockUser.setRole(role);

        Mockito.when(userService.findByUsername("denisa")).thenReturn(mockUser);

        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(view().name("tasks"));
    }
}
