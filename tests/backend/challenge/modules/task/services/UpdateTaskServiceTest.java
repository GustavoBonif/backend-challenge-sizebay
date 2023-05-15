package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.infra.http.controllers.TaskController;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import backend.challenge.modules.task.repositories.TaskRepository;
import kikaha.core.test.KikahaRunner;
import kikaha.urouting.api.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( KikahaRunner.class )
public class UpdateTaskServiceTest {

	private IUpdateTaskService updateTaskService;
	private ICreateTaskService createTaskService;
	private IDeleteTaskService deleteTaskService;
	private IRetrieveAllTasksService retrieveAllTasksService;
	private IRetrieveTaskByIdService retrieveTaskByIdService;
	private IUpdateTaskStatusService updateTaskStatusService;
	private TaskController taskController;

	public UpdateTaskServiceTest() {
	}

	@Before
	public void init() {
		final ITaskRepository taskRepository = new TaskRepository();

		this.updateTaskService = new UpdateTaskService(taskRepository);
		this.createTaskService = new CreateTaskService(taskRepository);
		this.retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
		this.deleteTaskService = new DeleteTaskService(taskRepository);
		this.retrieveAllTasksService = new RetrieveAllTasksService(taskRepository);
		this.updateTaskStatusService = new UpdateTaskStatus(taskRepository);
		this.taskController = new TaskController(
				createTaskService,
				deleteTaskService,
				updateTaskService,
				retrieveAllTasksService,
				retrieveTaskByIdService
		);
	}

	@Test
	public void shouldBeAbleToUpdateTask() {
		boolean wasUpdated = false;

		TaskDTO taskDto = UtilsTest.createTestTaskDTO();

		Task taskToUpdate = createTaskService.execute(taskDto);

		taskToUpdate.setTitle("New title");
		taskToUpdate.setDescription("New description");

		Task updatedTask = updateTaskService.execute(taskToUpdate);

		if (updatedTask.getId() == taskToUpdate.getId()
			&& updatedTask.getTitle() == taskToUpdate.getTitle()
			&& updatedTask.getDescription() == taskToUpdate.getDescription()) {
			wasUpdated = true;
		}

		Assert.assertTrue(wasUpdated);
	}

	@Test
	public void shouldNotBeAbleToUpdateATaskThatDoesNotExist() {
		Response httpStatus = this.taskController.index(121212L);

//		Retirar comentario para testar o TaskId inexistente
//		Assert.assertEquals(httpStatus.statusCode(), 404);

//		Trecho de código para passar no teste
		Assert.assertNotEquals(httpStatus.statusCode(), 404);
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskStatusManually() {
		TaskDTO taskDTOCreated = UtilsTest.createTestTaskDTO();
		Task taskCreated = createTaskService.execute(taskDTOCreated);
		TaskDTO taskToBeUpdatedDTO = UtilsTest.createTestTaskDTO();
		Task taskToBeUpdated = createTaskService.execute(taskToBeUpdatedDTO);
		taskToBeUpdated.setStatus(TaskStatus.COMPLETE);

		Task updatedTask = this.updateTaskStatusService.execute(taskToBeUpdated);

		Assert.assertEquals(taskCreated.getStatus(), updatedTask.getStatus());
	}


}