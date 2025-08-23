package config;

import javax.sql.DataSource;
import model.Compte;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompteWriterConfig {

    @Bean
    public JdbcBatchItemWriter<Compte> compteWriter(DataSource ds) {
        return new JdbcBatchItemWriterBuilder<Compte>()
            .dataSource(ds)
            .sql("""
                INSERT INTO compte (iban, balance)
                VALUES (:iban, :balance)
                ON DUPLICATE KEY UPDATE
                  balance = VALUES(balance)
            """)
            .beanMapped()
            .build();
    }
}
