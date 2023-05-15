package backend.challenge.modules.task.services;


import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;
import backend.challenge.modules.task.repositories.TaskRepository;
import kikaha.core.test.KikahaRunner;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith( KikahaRunner.class )
public class UpdateTaskProgressServiceTest {

	private ICreateTaskService createTaskService;

	@Before
	public void init(){
		final ITaskRepository taskRepository = new TaskRepository();

		this.createTaskService = new CreateTaskService(taskRepository);
	}

	@Test
	public void shouldBeAbleToUpdateTaskProgress() {
		boolean wasUpdated = false;

		TaskDTO taskDto = UtilsTest.createTestTaskDTO();
		Task taskToUpdate = this.createTaskService.execute(taskDto);

		int initialProgress = taskToUpdate.getProgress();

		TaskProgressDTO updatedTask = UtilsTest.updateTaskProgressDTO(taskToUpdate.getId());

		if (initialProgress != updatedTask.getProgress() &&
			updatedTask.getId() == taskToUpdate.getId()) {
			wasUpdated = true;
		}

		Assert.assertTrue(wasUpdated);
	}

	@Test
	public void shouldBeAbleToUpdateOnlyTaskStatusWhenProgressEqualsOneHundred() {
		/*
			TODO:  Para que esse teste passe, sua aplicação deve permitir que sejam
		         alterado apenas o campo `status`, quando o progresso for igual a 100.
		*/
	}

}
