package com.workspace.batch.jobs.inactive;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import com.workspace.batch.domain.User;
import com.workspace.batch.domain.enums.UserStatus;
import com.workspace.batch.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class InactiveItemTasklet implements Tasklet {
	
	private UserRepository userRepository;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		// reader
		Date nowDate = (Date) chunkContext.getStepContext().getJobParameters().get("nowDate");
		LocalDateTime now = LocalDateTime.ofInstant(nowDate.toInstant(), ZoneId.systemDefault());
		List<User> inactiveUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(now.minusYears(1), UserStatus.ACTIVE, null);
		
		// processor
		inactiveUsers = inactiveUsers.stream()
							.map(User::setInactive)
							.collect(Collectors.toList());
		
		// Writer
		userRepository.saveAll(inactiveUsers);
		
		return RepeatStatus.FINISHED;
	}

}
