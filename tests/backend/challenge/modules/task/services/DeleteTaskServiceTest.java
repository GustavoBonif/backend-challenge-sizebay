package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import backend.challenge.modules.task.repositories.TaskRepository;
import kikaha.core.test.KikahaRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( KikahaRunner.class )
public class DeleteTaskServiceTest {

	private IDeleteTaskService deleteTaskService;
	private ICreateTaskService createTaskService;
	private IRetrieveTaskByIdService retrieveTaskByIdService;

	@Before
	public void init() {
		final ITaskRepository taskRepository = new TaskRepository();

		deleteTaskService = new DeleteTaskService(taskRepository);
		createTaskService = new CreateTaskService(taskRepository);
		retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
	}

	@Test
	public void shouldBeAbleToDeleteTaskById() {
		boolean wasDeleted = false;

		TaskDTO taskDto = UtilsTest.createTestTaskDTO();
		Task taskCreated = createTaskService.execute(taskDto);
		deleteTaskService.execute(taskCreated.getId());
		Task taskDeleted = retrieveTaskByIdService.execute(taskCreated.getId());

		if (taskDeleted == null) {
			wasDeleted = true;
		}

		Assert.assertTrue(wasDeleted);
	}




}