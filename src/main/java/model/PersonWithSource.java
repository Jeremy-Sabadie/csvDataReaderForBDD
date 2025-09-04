package model;

import jakarta.persistence.*;

@Entity
@Table(name = "personne_excel")
public class PersonWithSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    private Integer age;

    @Column(nullable = false)
    private String source; 

    public PersonWithSource() { }

    public PersonWithSource(Person person, String source) {
        this.firstName = person.getFirstName();
        this.lastName  = person.getLastName();
        this.email     = person.getEmail();
        this.age       = person.getAge();
        this.source    = source;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}
