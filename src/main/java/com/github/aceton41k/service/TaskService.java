package com.github.aceton41k.service;

import com.github.aceton41k.dto.Task;
import com.github.aceton41k.entity.TaskEntity;
import com.github.aceton41k.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static com.github.aceton41k.constant.TaskStatus.*;

@Service
@Slf4j
public class TaskService {

    private final Map<Long, Task> taskStore = new ConcurrentHashMap<>();

    @Autowired
    private ApplicationContext applicationContext; // Контекст для получения текущего бина

    @Autowired
    private TaskRepository taskRepository;

//    private final Map<String, Task> tasks = new ConcurrentHashMap<>();
//    private final AtomicInteger progress = new AtomicInteger(0);
//    private volatile String status = "not started";

    public Long createTask(Integer duration) {
        TaskEntity savedTask = taskRepository.save(new TaskEntity().withProgress(0));
        TaskService taskService = applicationContext.getBean(TaskService.class);
        taskService.performAsyncTask(savedTask.getId(), duration); // Вызываем асинхронный метод через бин
        log.info("Task {} created", savedTask.getId());
        return savedTask.getId();
    }

    @Async
    public CompletableFuture<Void> performAsyncTask(Long taskId, Integer duration) {
        TaskEntity taskEntity = taskRepository.findById(taskId).get();
        taskEntity.setStatus(IN_PROGRESS);
        Task task = convertToDto(taskEntity);
        taskStore.put(taskId, task);
        taskRepository.save(taskEntity);
        try {
            for (int i = 0; i <= duration; i++) {
                task.setProgress(task.getProgress() + 100 / duration);
                if (task.getProgress() > 100) {
                    task.setProgress(100);
                    taskEntity.setProgress(100);
                    task.setStatus(DONE);
                    taskEntity.setStatus(DONE);
                    break;
                }
                log.info("Progress: {}", task.getProgress());
                Thread.sleep(1000);


            }

        } catch (
                InterruptedException e) {
            log.error(e.getMessage());
            task.setStatus(FAILED);
            taskEntity.setStatus(FAILED);
        } finally {
            taskStore.put(taskId, convertToDto(taskEntity));
            taskRepository.save(taskEntity);

            log.info("Task {} finished", taskId);
        }
        return CompletableFuture.completedFuture(null);
    }

    public Task getTaskStatus(Long id) {
        Task task = taskStore.get(id); // Извлекаем DTO из хранилища
        // if task finished get it from db
        return Objects.requireNonNullElseGet(task, () -> convertToDto(taskRepository.findById(id).get())); // Возвращаем текущий прогресс и статус
    }

    public List<Task> getAllTasks() {
        List<Task> tasksFromDb = taskRepository.findAll().stream().map(this::convertToDto).toList();
        for (Task task : tasksFromDb) {
            if (task.getStatus().equals("in progress"))
                task.setProgress(taskStore.get(task.getId()).getProgress());
        }
        return tasksFromDb;
    }

    private Task convertToDto(TaskEntity entity) {
        Task dto = new Task();
        dto.setStatus(entity.getStatus());
        dto.setProgress(entity.getProgress());
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

}