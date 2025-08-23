package config;

import java.sql.SQLIntegrityConstraintViolationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Politique de skip : ignore les erreurs d'unicité (email déjà présent en base).
 */
public class DuplicateEmailSkipPolicy implements SkipPolicy {

    @Override
    public boolean shouldSkip(Throwable t, long skipCount) {
        // Cas Spring (ex: DataIntegrityViolationException)
        if (t instanceof DataIntegrityViolationException) {
            return true;
        }
        // Cas Hibernate
        if (t instanceof ConstraintViolationException) {
            return true;
        }
        // Cas JDBC direct
        if (t.getCause() instanceof SQLIntegrityConstraintViolationException) {
            return true;
        }
        return false; // pour tout le reste -> on ne skip pas
    }
}
