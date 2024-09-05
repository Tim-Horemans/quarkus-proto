package org.acme.service;

import java.util.List;
import java.util.Optional;

import org.acme.DTO.SubTaskDTO;
import org.acme.model.SubTask;
import org.acme.model.Task;
import org.acme.repository.TaskRepository;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped 
public class TaskService {

    @Inject
    TaskRepository taskRepository;
    
    public List<Task> getTasks(){
        return taskRepository.findAll().list();
    }

    public Task getTask(Long taskId){
        return taskRepository.findById(taskId);
    }

    @Transactional
    public void saveTask(Task task) {
        taskRepository.persist(task);
    }

    @Transactional
    public Task updateCompleteStateTask(Task task){
        Optional<Task> foundTask = taskRepository.findByIdOptional(task.getId());

        Task existingTask = foundTask.orElseThrow(() -> new NotFoundException("no task with id"));

        existingTask.setCompleted(true);;

        return existingTask;
    }

    @Transactional
    public Task addSubTask(Long taskId, SubTaskDTO subTaskDTO) {
        Optional<Task> optionalTask = taskRepository.findByIdOptional(taskId);
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            SubTask subTask = mapToEntity(subTaskDTO);
            task.addChild(subTask); 
            taskRepository.persist(task);
            return task;
        } else {
            return null; 
        }
    }

    public Uni<List<SubTask>> getSubTasksForTask(Long taskId) {
        return Uni.createFrom().item(taskRepository.findByIdOptional(taskId))
            .onItem().transformToUni(optionalTask -> {
                if (optionalTask.isPresent()) {
                    Task task = optionalTask.get();
                    return Uni.createFrom().item(List.copyOf(task.getSubTasks()));
                } else {
                    return Uni.createFrom().failure(new NotFoundException("No task found with id: " + taskId));
                }
            });
    }

    private SubTask mapToEntity(SubTaskDTO dto) {
        SubTask subTask = new SubTask();
        subTask.setName(dto.getName());
        subTask.setDescription(dto.getDescription());
        return subTask;
    }
}
