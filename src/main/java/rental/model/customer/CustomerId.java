package rental.model.customer;

import rental.SurrogateId;

public class CustomerId extends SurrogateId {

    protected CustomerId(Long value) {
        super(value);
    }

    public static CustomerId of(Long value) {
        return new CustomerId(value);
    }
}