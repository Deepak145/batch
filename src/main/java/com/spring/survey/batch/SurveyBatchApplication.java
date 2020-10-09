package com.spring.survey.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = com.spring.survey.models.IndustrySurveyBatch.class)
@EnableBatchProcessing
public class SurveyBatchApplication implements CommandLineRunner {
	@Autowired JobLauncher jobLuncher;
	@Autowired Job job;
	public static void main(String[] args) {
		SpringApplication.run(SurveyBatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters param = new JobParameters();
		jobLuncher.run(job, param);
	}
}
