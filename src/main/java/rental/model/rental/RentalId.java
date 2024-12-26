package rental.model.rental;

import rental.SurrogateId;

public class RentalId extends SurrogateId {
    protected RentalId(Long value) {
        super(value);
    }

    public static RentalId of(Long value) {
        return new RentalId(value);
    }
}