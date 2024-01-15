package com.wap.taskmanager.repository;


import com.wap.taskmanager.entity.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,  Long> {
    Optional<Task> findById(@NotNull Long id);

}
