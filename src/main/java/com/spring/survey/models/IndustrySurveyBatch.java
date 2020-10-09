package com.spring.survey.models;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.validation.BindException;

import com.spring.survey.batch.dao.SurveyDao;
import com.spring.survey.batch.dao.SurveyRepository;

@Configuration
public class IndustrySurveyBatch {

	Logger log = LoggerFactory.getLogger(IndustrySurveyBatch.class);
	
	@Autowired JobBuilderFactory job;
	@Autowired StepBuilderFactory step;
	@Autowired SurveyRepository repository;
	
	@Bean
	public Job job() {
		log.info("Job is created");
		return job.get("Industry_Survey_Job")
				  .incrementer(new RunIdIncrementer())
				  .flow(step())
				  .end()
				  .build();
	}

	@Bean
	public Step step() {
		log.info("Steps are created");
		return step.get("Industry_Survey_Step")
				   .<Survey,Survey>chunk(100)
				   .reader(reader())
				   .processor(processor())
				   .writer(new SurveyItemWriter())
				   .taskExecutor(new SimpleAsyncTaskExecutor())
				   .build();
	}

	public ItemProcessor<Survey, Survey> processor() {
		return new ItempProcessorImpl();
	}

	public FlatFileItemReader<Survey> reader() {
		return new FlatFileItemReaderBuilder<Survey>()
				.name("Industrial_Survey_Reader")
				.resource(new ClassPathResource("/Industrial_Survey.csv"))
				.delimited()
				.names(new String[] {"Year", "Industry_aggregation_NZSIOC","Industry_code_NZSIOC", 
						"Industry_name_NZSIOC","Units","Variable_code","Variable_name",
						"Variable_category","Value","Industry_code_ANZSIC06"})
				.fieldSetMapper(new FieldMapper())
				.build();
	}
	

	class SurveyItemWriter implements ItemWriter<Survey>{
		@Override
		public void write(List<? extends Survey> items) throws Exception {
			log.info("writting to database");
			List<SurveyDao> surveyDaoItems = new ArrayList<>();
			items.forEach(survey -> {
				SurveyDao dao = new SurveyDao();
				dao.setUnit(survey.getUnit());
				dao.setIndustryAgg2(survey.getIndustryAgg2());
				dao.setIndustryCode(survey.getIndustryCode());
				dao.setIndustryName(survey.getIndustryName());
				dao.setInsdustryAgg(survey.getInsdustryAgg());
				dao.setValue(survey.getValue());
				dao.setVariableCategory(survey.getVariableCategory());
				dao.setVariableCode(survey.getVariableCode());
				dao.setVariableName(survey.getVariableName());
				surveyDaoItems.add(dao);
			});
			repository.saveAll(surveyDaoItems);
		}
	}
	
	class FieldMapper implements FieldSetMapper<Survey>{
		@Override
		public Survey mapFieldSet(FieldSet fieldSet) throws BindException {
			log.info("mapFieldSet is called");
			Survey survey = new Survey();
			try {
				survey.setYear(fieldSet.readInt(0));
				survey.setInsdustryAgg(fieldSet.readString(1));
				survey.setIndustryCode(fieldSet.readString(2));
				survey.setIndustryName(fieldSet.readString(3));
				survey.setUnit(fieldSet.readString(4));
				survey.setVariableCode(fieldSet.readString(5));
				survey.setVariableName(fieldSet.readString(6));
				survey.setVariableCategory(fieldSet.readString(7));
				survey.setValue(fieldSet.readString(8));
				survey.setIndustryAgg2(fieldSet.readString(9));
			}
			catch(Exception e) {
				log.error("Error Occue while updating...");
			}
			log.info("Survey object data is :" + survey);
			return survey;
		}
	}
	
	class ItempProcessorImpl implements ItemProcessor<Survey, Survey>{
		@Override
		public Survey process(Survey item) throws Exception {
			log.info("Item Processor Implementation is called");
			return item;
		}
	}
}
