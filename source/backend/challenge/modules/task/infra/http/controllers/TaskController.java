package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.infra.http.views.TaskView;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Path("tasks")
public class TaskController {

	private final ICreateTaskService createTaskService;
	private final IDeleteTaskService deleteTaskService;
	private final IRetrieveAllTasksService retrieveAllTasksService;
	private final IRetrieveTaskByIdService retrieveTaskByIdService;
	private final IUpdateTaskService updateTaskService;

	@Inject
	public TaskController(
		final ICreateTaskService createTaskService,
		final IDeleteTaskService deleteTaskService,
		final IRetrieveAllTasksService retrieveAllTasksService,
		final IRetrieveTaskByIdService retrieveTaskByIdService
	) {
		this.createTaskService = createTaskService;
		this.deleteTaskService = deleteTaskService;
		this.retrieveAllTasksService = retrieveAllTasksService;
		this.retrieveTaskByIdService = retrieveTaskByIdService;
		this.updateTaskService = null;
	}

	@GET
	public Response show() {
		try {
			List<Task> allTasks = retrieveAllTasksService.execute();
			return DefaultResponse.ok().entity(allTasks);
		} catch (Exception e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}
	}

	@GET
	@Path("single/{taskId}")
	public Response index(@PathParam("taskId") Long taskId) {
		try {
			Task requestedTask = retrieveTaskByIdService.execute(taskId);
			return DefaultResponse.ok().entity(requestedTask);
		}
		catch (Exception e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}
	}

	@POST
	public Response create(TaskView task) {
		try {
			TaskDTO taskDto = TaskDTO.create();
			taskDto.setTitle(task.getTitle());
			taskDto.setDescription(task.getDescription());
			Task newTask = createTaskService.execute(taskDto);
			return DefaultResponse.created().entity(newTask);
		} catch (Exception e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}
	}

	@PUT
	@Path("single/{taskId}")
	public Response update(@PathParam("taskId") Long taskId, Task task) {
		/*
			TODO:  A rota deve alterar apenas o title e description da tarefa
			 			 que possua o id igual ao id correspondente nos parâmetros da rota.
		 */

		return DefaultResponse.ok().entity("Hello world");
	}

	@DELETE
	@Path("single/{taskId}")
	public Response delete(@PathParam("taskId") Long taskId) {
		// TODO: A rota deve deletar a tarefa com o id correspondente nos parâmetros da rota

		return DefaultResponse.ok().entity("Hello world");
	}

}
