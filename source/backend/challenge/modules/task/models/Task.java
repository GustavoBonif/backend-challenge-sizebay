package backend.challenge.modules.task.models;

import backend.challenge.modules.task.enums.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
public class Task {

	private Long id;
	private String title;
	private String description;
	private int progress;
	private TaskStatus status;
	private Date createdAt;

	// Sobreescrevendo setter de status para verificação de progresso antes da mudança
	public void setStatus(TaskStatus status) {
		if (this.getProgress() == 100) {
			this.status = status;
		} else {
			this.status = TaskStatus.PROGRESS;
		}
	}

	public void setProgress(int progress) {
		if (progress >= 1 &&
			progress <= 100)
		{
			this.progress = progress;
		}
	}
}
