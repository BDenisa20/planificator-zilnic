package com.denisa.planificator_zilnic.controller;

import com.denisa.planificator_zilnic.config.SecurityConfig;
import com.denisa.planificator_zilnic.model.Role;
import com.denisa.planificator_zilnic.model.User;
import com.denisa.planificator_zilnic.service.RoleService;
import com.denisa.planificator_zilnic.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminUserController.class)
@Import(SecurityConfig.class)
class AdminUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminUsers_shouldReturnAdminUsersView() throws Exception {

        Mockito.when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/users"));
    }

    @Test
    @WithMockUser(username = "denisa", roles = {"USER"})
    void adminUsers_shouldDenyAccessForNormalUser() throws Exception {

        mockMvc.perform(get("/admin/users"))
                .andExpect(status().isForbidden());
    }
}
