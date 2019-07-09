package com.coditas.SpringBatchSchedular;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class SpringBatchSchedularApplication {

	@Autowired
	JobLauncher jobLauncher;
	@Autowired
	Job job;
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchSchedularApplication.class, args);
	}

	@Scheduled(fixedRate = 5000)
	public void perform() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
		SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");
		System.out.println("Execution time  "+sdf.format(new Date()));
		JobParameters param=new JobParametersBuilder().addString("JobId",String.valueOf(System.currentTimeMillis()))
				           .toJobParameters();
		jobLauncher.run(job,param);
	}


}
