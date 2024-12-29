package rental.application;

public interface AppTransaction {
    void execute(Runnable runnable);
    boolean inTransaction();
}
