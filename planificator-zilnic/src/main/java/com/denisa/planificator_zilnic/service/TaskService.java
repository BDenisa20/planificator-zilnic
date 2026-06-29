package com.denisa.planificator_zilnic.service;

import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;

import java.util.List;
public interface TaskService {
    List<Task> getTasksForUser(User user);
    Task getTaskById(Long id);
    Task saveTask(Task task);
    void deleteTask(Long id);
}
