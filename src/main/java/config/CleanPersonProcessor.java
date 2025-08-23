package config;

import java.util.Locale;
import java.util.regex.Pattern;

import model.Person;
import org.springframework.batch.item.ItemProcessor;

public class CleanPersonProcessor implements ItemProcessor<Person, Person> {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    @Override
    public Person process(Person p) throws Exception {
        if (p == null) return null;

        // 1) Normalisation basique
        String first = safeTrim(p.getFirstName());
        String last  = safeTrim(p.getLastName());
        String email = safeTrim(p.getEmail());
        Integer age  = p.getAge();

        // Casse : Prénom/Nom en "Title Case" simple
        first = toTitleCase(first);
        last  = toTitleCase(last);

        // Email en minuscules
        if (email != null) {
            email = email.toLowerCase(Locale.ROOT);
        }

        // 2) Validations simples (filtrage)
        if (isBlank(first) || isBlank(last) || isBlank(email)) {
            // retourne null => l’item est filtré (non écrit)
            return null;
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            return null;
        }
        if (age == null || age < 0 || age > 120) {
            return null;
        }

        // 3) Réinjecte les valeurs nettoyées
        p.setFirstName(first);
        p.setLastName(last);
        p.setEmail(email);
        p.setAge(age);

        return p;
    }

    // --- helpers ---
    private static String safeTrim(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.isBlank(); }

    private static String toTitleCase(String s) {
        if (isBlank(s)) return s;
        String lower = s.toLowerCase(Locale.ROOT);
        return Character.toTitleCase(lower.charAt(0)) + lower.substring(1);
    }
}
