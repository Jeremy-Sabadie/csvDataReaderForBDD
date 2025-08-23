package config;

import model.Compte;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CompteCsvReaderConfig {

    @Bean
    public FlatFileItemReader<Compte> compteReader() {
        return new FlatFileItemReaderBuilder<Compte>()
                .name("compteItemReader")
                .resource(new ClassPathResource("input/accounts.csv"))
                .linesToSkip(1)
                .encoding("UTF-8")
                .delimited().delimiter(",")
                .names("iban","balance")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Compte.class);
                }})
                .build();
    }
}
