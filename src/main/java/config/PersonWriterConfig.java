package config;

import model.Person;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersonWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Person> personJdbcWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .sql("INSERT INTO person (first_name, last_name, email, age) VALUES (:firstName, :lastName, :email, :age)")
                .beanMapped() 
                .build();
    }
}
