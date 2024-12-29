package rental.infrastructure.repository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;
import rental.application.AppTransaction;

@Component
public class JpaTransaction implements AppTransaction {

    private Boolean inTransaction = false;

    @Override
    public boolean inTransaction() {
        return inTransaction;
    }

    @Override
    @Transactional
    public void execute(Runnable runnable) {
        inTransaction = true;
        try {
            runnable.run();
        } finally {
            inTransaction = false;
        }
    }
}