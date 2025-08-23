package config;

import model.Compte;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class CompteBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager tx;

    public CompteBatchConfig(JobRepository jobRepository, PlatformTransactionManager tx) {
        this.jobRepository = jobRepository;
        this.tx = tx;
    }

    @Bean
    public Job importCompteJob(Step importCompteStep) {
        return new JobBuilder("importCompteJob", jobRepository)
                .start(importCompteStep)
                .build();
    }

    @Bean
    public Step importCompteStep(
            ItemReader<Compte> compteReader,               // fourni par CompteCsvReaderConfig
            ItemProcessor<Compte, Compte> compteProcessor, // défini juste dessous
            ItemWriter<Compte> compteWriter                // fourni par CompteWriterConfig (JdbcBatchItemWriter<Compte>)
    ) {
        return new StepBuilder("importCompteStep", jobRepository)
                .<Compte, Compte>chunk(100, tx)
                .reader(compteReader)
                .processor(compteProcessor)
                .writer(compteWriter)
                .build();
    }

    @Bean
    public ItemProcessor<Compte, Compte> compteProcessor() {
        return c -> c; // pas de transformation
    }
}
