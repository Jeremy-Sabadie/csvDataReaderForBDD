package processor;

import org.springframework.batch.item.ItemProcessor;

import model.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person p) {
        if (p == null) return null;

        // Nettoyage
        if (p.getFirstName() != null) p.setFirstName(p.getFirstName().trim());
        if (p.getLastName() != null)  p.setLastName(p.getLastName().trim());
        if (p.getEmail() != null)     p.setEmail(p.getEmail().trim().toLowerCase());

        // Validation simple : ignorer si âge négatif
        if (p.getAge() != null && p.getAge() < 0) return null;

        return p;
    }
}
