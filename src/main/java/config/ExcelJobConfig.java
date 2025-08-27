package config;

import model.Person;
import model.PersonWithSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ExcelJobConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public ExcelJobConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Step excelStep(
            ItemReader<Person> reader,
            @Qualifier("excelProcessor") ItemProcessor<Person, PersonWithSource> processor,
            @Qualifier("excelJpaWriter") ItemWriter<PersonWithSource> writer
    ) {
        return new StepBuilder("excelStep", jobRepository)
                .<Person, PersonWithSource>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job importExcelJob(Step excelStep) {
        return new JobBuilder("importExcelJob", jobRepository)
                .start(excelStep)
                .build();
    }
}
