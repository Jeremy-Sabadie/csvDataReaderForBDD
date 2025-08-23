package config;

import javax.sql.DataSource;
import model.Person;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Person> personWriter(DataSource ds) {
        return new JdbcBatchItemWriterBuilder<Person>()
            .dataSource(ds)
            .sql("""
                INSERT INTO personne (first_name, last_name, email, age)
                VALUES (:firstName, :lastName, :email, :age)
                ON DUPLICATE KEY UPDATE
                  first_name = VALUES(first_name),
                  last_name  = VALUES(last_name),
                  age        = VALUES(age)
            """)
            .beanMapped()
            .build();
    }
}
