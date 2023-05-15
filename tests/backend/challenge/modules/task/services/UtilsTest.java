package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;
import backend.challenge.modules.task.dtos.TaskProgressDTO;

public class UtilsTest {
    public static TaskDTO createTestTaskDTO() {
        TaskDTO taskDto = TaskDTO.create();

        taskDto.setTitle("title test");
        taskDto.setDescription("description test");

        return taskDto;
    }

    public static TaskProgressDTO  updateTaskProgressDTO(Long taskId) {
        TaskProgressDTO taskProgressDTO = TaskProgressDTO.create();
        taskProgressDTO.setId(taskId);
        taskProgressDTO.setProgress(75);

        return taskProgressDTO;
    }
}
