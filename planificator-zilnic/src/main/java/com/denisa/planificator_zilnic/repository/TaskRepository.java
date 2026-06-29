package com.denisa.planificator_zilnic.repository;

import com.denisa.planificator_zilnic.model.Task;
import com.denisa.planificator_zilnic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    int countByUser(User user);

    int countByUserAndCompleted(User user, boolean completed);
}
