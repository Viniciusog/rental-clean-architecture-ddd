package rental.model.exception;

import rental.model.rental.RentalId;

public class RentalNotFoundException extends AbstractNotFoundException{
    public RentalNotFoundException(RentalId rentalId) {
        super("Rental not found with id: " + rentalId.value());
    }
}