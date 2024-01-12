package com.wap.taskmanager.service.mapper;

import com.wap.taskmanager.entity.Task;
import com.wap.taskmanager.service.dto.TaskDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);
    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOToTask(TaskDTO taskDTO);
}
