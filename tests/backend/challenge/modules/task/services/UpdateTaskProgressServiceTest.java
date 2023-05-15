package backend.challenge.modules.task.services;


import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.dtos.TaskProgressDTO;
import backend.challenge.modules.task.enums.TaskStatus;
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
	private IUpdateTaskStatusService updateTaskStatusService;
	private IRetrieveTaskByIdService retrieveTaskByIdService;
	private IUpdateTaskProgressService updateTaskProgressService;

	@Before
	public void init(){
		final ITaskRepository taskRepository = new TaskRepository();

		this.createTaskService = new CreateTaskService(taskRepository);
		this.updateTaskStatusService = new UpdateTaskStatus(taskRepository);
		this.retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
		this.updateTaskProgressService = new UpdateTaskProgressService(taskRepository);
	}

	@Test
	public void shouldBeAbleToUpdateTaskProgress() {
		boolean wasUpdated = false;

		TaskDTO taskDto = UtilsTest.createTestTaskDTO();
		Task taskToUpdate = this.createTaskService.execute(taskDto);

		int initialProgress = taskToUpdate.getProgress();

		TaskProgressDTO updatedTask = UtilsTest.createTaskProgressDTO(taskToUpdate.getId());

		if (initialProgress != updatedTask.getProgress() &&
			updatedTask.getId() == taskToUpdate.getId()) {
			wasUpdated = true;
		}

		Assert.assertTrue(wasUpdated);
	}

	@Test
	public void shouldBeAbleToUpdateOnlyTaskStatusWhenProgressEqualsOneHundred() {
		TaskDTO taskToBeUpdatedDTO = UtilsTest.createTestTaskDTO();
		Task taskToBeUpdated = this.createTaskService.execute(taskToBeUpdatedDTO);

		taskToBeUpdated.setProgress(75);

		taskToBeUpdated.setStatus(TaskStatus.COMPLETE);
		Task updatedTask = this.updateTaskStatusService.execute(taskToBeUpdated);

		Assert.assertEquals(TaskStatus.PROGRESS, updatedTask.getStatus());

		taskToBeUpdated.setProgress(100);

		taskToBeUpdated.setStatus(TaskStatus.COMPLETE);
		updatedTask = this.updateTaskStatusService.execute(taskToBeUpdated);

		Assert.assertEquals(TaskStatus.COMPLETE, updatedTask.getStatus());
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskProgressWhenProgressLessThanOneHundred() {
		TaskDTO taskToBeUpdatedDTO = UtilsTest.createTestTaskDTO();
		Task taskToBeUpdated = this.createTaskService.execute(taskToBeUpdatedDTO);
		TaskProgressDTO taskToBeUpdatedProgressDTO = UtilsTest.createTaskProgressDTO(taskToBeUpdated.getId());
		taskToBeUpdatedProgressDTO.setProgress(-40);
		Task updatedTask = this.updateTaskProgressService.execute(taskToBeUpdatedProgressDTO);

		Assert.assertFalse(updatedTask.getProgress() < 0);
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskProgressWhenProgressGreaterThanOneHundred() {
		TaskDTO taskToBeUpdatedDTO = UtilsTest.createTestTaskDTO();
		Task taskToBeUpdated = this.createTaskService.execute(taskToBeUpdatedDTO);
		TaskProgressDTO taskToBeUpdatedProgressDTO = UtilsTest.createTaskProgressDTO(taskToBeUpdated.getId());
		taskToBeUpdatedProgressDTO.setProgress(140);
		Task updatedTask = this.updateTaskProgressService.execute(taskToBeUpdatedProgressDTO);

		Assert.assertFalse(updatedTask.getProgress() > 100);
	}

}
