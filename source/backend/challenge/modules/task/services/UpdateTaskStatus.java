package backend.challenge.modules.task.services;

import backend.challenge.modules.task.models.Task;
import backend.challenge.modules.task.repositories.ITaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UpdateTaskStatus implements IUpdateTaskStatusService{
    private final ITaskRepository taskRepository;

    @Inject
    public UpdateTaskStatus(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task execute(Task task) {
        Task taskUpdated = this.taskRepository.updateStatus(task);
        return taskUpdated;
    }
}
