package com.coditas.SpringBatchSchedular.config;

import com.coditas.SpringBatchSchedular.model.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobFactory;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing

public class SpringBatchConfiguration {

    @Autowired
    JobBuilderFactory jobFactory;

    @Autowired
    StepBuilderFactory stepFactory;

    @Bean
    public Job job(ItemReader<User> itemReader, ItemProcessor<User,User> itemProcessor, ItemWriter<User> itemWriter)
    {
        Step step=stepFactory.get("Get-Data").<User,User>chunk(100)
                  .reader(itemReader)
                  .processor(itemProcessor)
                  .writer(itemWriter)
                  .build();
        Job job=jobFactory.get("Load-User").incrementer(new RunIdIncrementer()).start(step).build();
        return job;
    }

    @Bean
    public FlatFileItemReader<User> fileItemReader(@Value("${input}") Resource resource)
    {
        FlatFileItemReader<User> flatFileItemReader=new FlatFileItemReader<>();
        flatFileItemReader.setResource(resource);
        flatFileItemReader.setName("User Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        return flatFileItemReader;
    }

    public LineMapper<User> lineMapper() {
        DefaultLineMapper<User> defaultLineMapper=new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer(); //seprate tokens on delimeter
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id","name","sal"});
        defaultLineMapper.setLineTokenizer(lineTokenizer);

        BeanWrapperFieldSetMapper<User> fieldSetMapper=new BeanWrapperFieldSetMapper<>(); //set token to the User
        fieldSetMapper.setTargetType(User.class);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
