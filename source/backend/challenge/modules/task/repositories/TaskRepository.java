package backend.challenge.modules.task.repositories;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.dtos.TaskProgressDTO;
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
		Task taskToUpdate = this.index(task.getId());
		taskToUpdate.setTitle(task.getTitle());
		taskToUpdate.setDescription(task.getDescription());

		return taskToUpdate;
	}

	@Override
	public void delete(final Long taskId) {
		Task taskToBeDeleted = this.index(taskId);
		tasks.remove(taskToBeDeleted);
	}

	@Override
	public Task updateProgress(TaskProgressDTO taskProgressDTO) {
		Task taskToUpdate = this.index(taskProgressDTO.getId());

		taskToUpdate.setProgress(taskProgressDTO.getProgress());

		return taskToUpdate;
	}

	@Override
	public Task updateStatus(Task task) {
		Task taskToUpdate = this.index(task.getId());

		taskToUpdate.setStatus(TaskStatus.COMPLETE);

		return taskToUpdate;
	}

}
