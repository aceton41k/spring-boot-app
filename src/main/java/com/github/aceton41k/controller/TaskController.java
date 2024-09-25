package com.github.aceton41k.controller;

import com.github.aceton41k.dto.Task;
import com.github.aceton41k.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/create")
    public String createTask(@RequestParam Integer duration) {
        Long taskId = taskService.createTask(duration);
        return "Task created with ID: " + taskId;
    }

    @GetMapping("/status/{id}")
    public Task getTaskStatus(@PathVariable Long id) {
        return taskService.getTaskStatus(id);
    }

    @GetMapping("/status")
    public List<Task> getTaskStatus() {
        return taskService.getAllTasks();
    }
}