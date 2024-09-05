package org.acme;

import java.util.List;

import org.acme.DTO.SubTaskDTO;
import org.acme.model.Task;
import org.acme.service.TaskService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;


@Path("/task")
public class TaskResource {

    @Inject
    private TaskService taskService;


   @GET
    public List<Task> getTasks() {
        return taskService.getTasks(); 
    }

    @GET
    @Path("{id}")
    public Task getTask(@PathParam("id") Long taskId) {
        return taskService.getTask(taskId);
    }

    @POST
    public Response saveTask(Task task) {
        taskService.saveTask(task);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    public Task completeTask(Task task){
        return taskService.updateCompleteStateTask(task);
    }

    @POST
    @Path("{taskId}/subtask")
    public Response addSubTask(@PathParam("taskId") Long taskId, SubTaskDTO subTask) {
        Task updatedTask = taskService.addSubTask(taskId, subTask);
        if (updatedTask != null) {
            return Response.ok(updatedTask).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("{taskId}/subtasks")
    public Uni<Response> getSubTasksForTask(@PathParam("taskId") Long taskId) {
        return taskService.getSubTasksForTask(taskId)
            .onItem().transform(subTasks -> Response.ok(subTasks).build())
            .onFailure().recoverWithItem(e -> Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build());
    }

    

}
