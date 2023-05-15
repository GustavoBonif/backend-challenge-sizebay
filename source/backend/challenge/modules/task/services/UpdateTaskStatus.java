package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskProgressDTO;
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
    public Task execute(TaskProgressDTO taskProgressDTO) {
        Task taskUpdated = this.taskRepository.updateStatus(taskProgressDTO);
        return taskUpdated;
    }
}
