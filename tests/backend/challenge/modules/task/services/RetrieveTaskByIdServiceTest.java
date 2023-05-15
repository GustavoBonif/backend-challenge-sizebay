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
public class RetrieveTaskByIdServiceTest {

	private ICreateTaskService createTaskService;
	private IRetrieveTaskByIdService retrieveTaskByIdService;

	@Before
	public void init() {
		final ITaskRepository taskRepository = new TaskRepository();

		createTaskService = new CreateTaskService(taskRepository);
		retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
	}
	@Test
	public void shouldBeAbleToListTheTaskById() {

		boolean isTheRequiredTask = false;

		TaskDTO taskDTO = UtilsTest.createTestTaskDTO();
		Task createdTask = createTaskService.execute(taskDTO);

		Task requiredTask = retrieveTaskByIdService.execute(createdTask.getId());

		if (createdTask.getId() == requiredTask.getId()) {
			isTheRequiredTask = true;
		}

		Assert.assertTrue(isTheRequiredTask);
	}

}
