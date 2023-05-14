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
public class UpdateTaskServiceTest {

	private IUpdateTaskService updateTaskService;
	private ICreateTaskService createTaskService;
	private IRetrieveTaskByIdService retrieveTaskByIdService;

	public UpdateTaskServiceTest() {
	}

	@Before
	public void init() {
		final ITaskRepository taskRepository = new TaskRepository();

		this.updateTaskService = new UpdateTaskService(taskRepository);
		this.createTaskService = new CreateTaskService(taskRepository);
		this.retrieveTaskByIdService = new RetrieveTaskByIdService(taskRepository);
	}

	@Test
	public void shouldBeAbleToUpdateTask() {
		/*
			TODO:  Para que esse teste passe, sua aplicação deve permitir que sejam
		         alterados apenas os campos `title` e `observation`.
		*/

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
		/*
			TODO: Para que esse teste passe, você deve validar na sua rota de update se
			 			o id da tarefa enviada pela url existe ou não. Caso não exista, retornar um erro com status 400.
		*/
	}

	@Test
	public void shouldNotBeAbleToUpdateTaskStatusManually() {
		/*
			TODO:  Para que esse teste passe, você não deve permitir que sua rota de update
						 altere diretamente o `status` dessa tarefa, mantendo o mesmo status que a tarefa
						 já possuía antes da atualização. Isso porque o único lugar que deve atualizar essa informação
						 é a rota responsável por alterar o progresso da tarefa.

		 */
	}


}