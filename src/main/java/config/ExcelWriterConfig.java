package config;

import model.PersonWithSource;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.persistence.EntityManagerFactory;

@Configuration
public class ExcelWriterConfig {

    @Bean("excelJpaWriter")
    public JpaItemWriter<PersonWithSource> excelJpaWriter(EntityManagerFactory entityManagerFactory) {
        JpaItemWriter<PersonWithSource> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
