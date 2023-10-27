package com.webstuffy.lmsapi.repository;

import com.webstuffy.lmsapi.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
