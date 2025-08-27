package config;

import model.Person;
import model.PersonWithSource;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelProcessorConfig {

    @Bean("excelProcessor") 
    public ItemProcessor<Person, PersonWithSource> excelProcessor() {
        return person -> new PersonWithSource(person, "EXCEL");
    }
}
