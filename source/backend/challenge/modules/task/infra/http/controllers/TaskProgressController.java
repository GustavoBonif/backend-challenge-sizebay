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
	private final String progress_out_of_range_alert = "The progress must be in a 0 - 100 range!";
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
				return DefaultResponse.ok().entity(progress_out_of_range_alert);
			}

			TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
			taskProgressDTO.setProgress(taskProgressView.getProgress());
			taskProgressDTO.setId(taskId);

			Task taskToBeUpdated = retrieveTaskByIdService.execute(taskId);

			taskToUpdate = updateTaskProgressService.execute(taskProgressDTO);

			if (taskProgressDTO.getProgress() == 100) {
				this.updateStatus(taskToBeUpdated);
			}

			return DefaultResponse.ok().entity(taskToUpdate);
		} catch (Exception e) {
			return DefaultResponse.badRequest().entity(e.getMessage());
		}
	}

	private void updateStatus(Task task) {
		this.updateTaskStatusService.execute(task);
	}

}
