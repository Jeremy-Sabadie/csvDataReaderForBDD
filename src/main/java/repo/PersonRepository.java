package repo;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
    boolean existsByEmail(String email);
}