package backend.challenge.modules.task.repositories;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.enums.TaskStatus;
import backend.challenge.modules.task.models.Task;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Singleton
public class TaskRepository implements ITaskRepository {

	private static List<Task> tasks = new ArrayList<>();

	@Override
	public Task index(final Long taskId) {

		for (Task task : tasks) {
			if (task.getId().equals(taskId)) {
				return task;
			}
		}
		return null;
	}

	@Override
	public List<Task> show() {
		List<Task> allTasks = this.tasks;

		return allTasks;
	}

	@Override
	public Task create(final TaskDTO taskDTO) {
		Task task = new Task();
		task.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
		task.setTitle(taskDTO.getTitle());
		task.setDescription(taskDTO.getDescription());
		task.setProgress(0);
		task.setStatus(TaskStatus.PROGRESS);
		task.setCreatedAt(new Date());
		tasks.add(task);

		return task;
	}

	@Override
	public Task update(final Task task) {
		// TODO: Criar método responsável por atualizar uma tarefa

		return null;
	}

	@Override
	public void delete(final Long taskId) {
 		// TODO: Criar método responsável por deletar tarefa por id

	}

}
