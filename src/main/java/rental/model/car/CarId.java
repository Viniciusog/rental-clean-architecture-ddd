package rental.model.car;

import rental.SurrogateId;

public class CarId extends SurrogateId {

    protected CarId(Long value) {
        super(value);
    }

    public static CarId of(Long value) {
        return new CarId(value);
    }
}