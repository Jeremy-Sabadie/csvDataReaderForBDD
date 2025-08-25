package config;

import jakarta.persistence.EntityManagerFactory;
import model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job importPersonJob(Step importPersonStep) {
        return new JobBuilder("importPersonJob", jobRepository)
                .start(importPersonStep)
                .build();
    }

    @Bean
    public Step importPersonStep(ItemReader<Person> reader,
                                 ItemProcessor<Person, Person> processor,
                                 ItemWriter<Person> writer) {
        return new StepBuilder("importPersonStep", jobRepository)
                .<Person, Person>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipPolicy(new DuplicateEmailSkipPolicy()) //Skip si email dupliqué
                .build();
    }

    @Bean
    public ItemProcessor<Person, Person> processor() {
        return person -> person; // pas de transformation particulière
    }

    @Bean
    public JpaItemWriter<Person> writer(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<Person> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
