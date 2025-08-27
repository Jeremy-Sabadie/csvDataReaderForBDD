package config;

import model.Person;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.Iterator;

@Configuration
public class ExcelReaderConfig {

    @Bean
    public ItemReader<Person> excelReader() {
        return new ItemReader<Person>() {

            private Iterator<Row> rowIterator;

            {
                try {
                    InputStream is = new ClassPathResource("input/people_from_csv.xlsx").getInputStream();
                    Workbook workbook = new XSSFWorkbook(is);
                    Sheet sheet = workbook.getSheetAt(0);
                    rowIterator = sheet.iterator();
                    rowIterator.next(); // skip header
                } catch (Exception e) {
                    throw new RuntimeException("Erreur lors de la lecture du fichier Excel", e);
                }
            }

            @Override
            public Person read() {
                if (rowIterator != null && rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    Cell ageCell = row.getCell(3);
                    if (ageCell != null && ageCell.getNumericCellValue() <= 18) {
                        return read(); // skip
                    }

                    Person person = new Person();
                    person.setFirstName(row.getCell(0).getStringCellValue());
                    person.setLastName(row.getCell(1).getStringCellValue());
                    person.setEmail(row.getCell(2).getStringCellValue());
                    person.setAge((int) row.getCell(3).getNumericCellValue());

                    return person;
                }
                return null;
            }
        };
    }
}
