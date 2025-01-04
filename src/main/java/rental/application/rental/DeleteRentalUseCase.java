package rental.application.rental;

import rental.application.AppTransaction;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

public class DeleteRentalUseCase {

    private AppTransaction transaction;
    private RentalRepository repository;

    public DeleteRentalUseCase(AppTransaction transaction, RentalRepository repository) {
        this.transaction = transaction;
        this.repository = repository;
    }

    public void execute(RentalId rentalId) {
        transaction.execute(() -> {
            repository.deleteById(rentalId);
        });
    }
}
