package backend.challenge.modules.task.services;

import backend.challenge.modules.task.dtos.TaskDTO;

public class UtilsTest {
    public static TaskDTO createTestTaskDTO() {
        TaskDTO taskDto = TaskDTO.create();

        taskDto.setTitle("title test");
        taskDto.setDescription("description test");

        return taskDto;
    }
}
