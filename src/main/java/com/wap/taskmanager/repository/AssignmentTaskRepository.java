package com.wap.taskmanager.repository;

import com.wap.taskmanager.entity.AssignmentTask;
import com.wap.taskmanager.entity.Task;
import com.wap.taskmanager.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentTaskRepository extends JpaRepository<AssignmentTask, Long> {
    Optional<AssignmentTask> findByUser(@NotNull User user);
    Optional<AssignmentTask> findByTask(@NotNull Task task);
    Optional<AssignmentTask> findByUserAndTask(@NotNull User user, @NotNull Task task);

}
