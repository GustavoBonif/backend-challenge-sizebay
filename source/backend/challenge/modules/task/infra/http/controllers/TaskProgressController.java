package backend.challenge.modules.task.infra.http.controllers;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.infra.http.views.TaskProgressView;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.services.*;
import kikaha.urouting.api.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Path("tasks/progress")
public class TaskProgressController {

	private final String task_not_found_alert = "Task not found!";
	private final IUpdateTaskProgressService updateTaskProgressService;
	private final IRetrieveTaskByIdService retrieveTaskByIdService;
	private final IUpdateTaskStatusService updateTaskStatusService;

	@Inject
	public TaskProgressController(
			final IUpdateTaskProgressService updateTaskProgressService,
			final IRetrieveTaskByIdService retrieveTaskByIdService,
			final IUpdateTaskStatusService updateTaskStatusService
	) {
		this.updateTaskProgressService = updateTaskProgressService;
		this.retrieveTaskByIdService = retrieveTaskByIdService;
		this.updateTaskStatusService = updateTaskStatusService;
	}

	@PUT
	@Path("single/{taskId}")
	public Response updateProgress(@PathParam("taskId") Long taskId, TaskProgressView taskProgressView) {
		try {
			Task taskToUpdate = retrieveTaskByIdService.execute(taskId);

			if (taskToUpdate == null) {
				return DefaultResponse.ok().entity(task_not_found_alert);
			}

			if (taskProgressView.getProgress() < 1 ||
				taskProgressView.getProgress() > 100 ){
				return DefaultResponse.ok().entity("The progress must be in a 0 - 100 range!");
			}

			TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
			taskProgressDTO.setProgress(taskProgressView.getProgress());
			taskProgressDTO.setId(taskId);

			if (taskProgressDTO.getProgress() == 100) {
				this.updateStatus(taskProgressDTO);
			}


			taskToUpdate = updateTaskProgressService.execute(taskProgressDTO);

			return DefaultResponse.ok().entity(taskToUpdate);
		} catch (Exception e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}
	}

	private void updateStatus(TaskProgressDTO taskProgressDTO) {
		this.updateTaskStatusService.execute(taskProgressDTO);
	}

}
