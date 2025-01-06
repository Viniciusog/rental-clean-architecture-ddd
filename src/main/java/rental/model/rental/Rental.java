package rental.model.rental;

import rental.Validation;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Rental {

    private RentalId id;
    private CustomerId customerId;
    private CarId carId;
    private DateTimeRange timeRange;
    private BigDecimal totalPrice;

    public Rental(Builder builder) {
        this.id = builder.id;
        this.customerId = Validation.required(builder.customerId, "customerId is required");
        this.carId = Validation.required(builder.carId, "carId is required");
        this.timeRange = Validation.required(builder.timeRange, "timeRange is required");
        BigDecimal totalPrice = Validation.required(builder.totalPrice, "totalPrice is required");
        validateTotalPrice(totalPrice);
        this.totalPrice = totalPrice;
    }

    public void created(RentalId id) {
        this.id = id;
    }

    public RentalId id() {
        return id;
    }

    public CustomerId customerId() {
        return customerId;
    }

    public CarId carId() {
        return carId;
    }

    public DateTimeRange timeRange() {
        return timeRange;
    }

    public BigDecimal totalPrice() {
        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void update(DateTimeRange timeRange, BigDecimal totalPrice) {
        BigDecimal newTotalPrice = Validation.required(totalPrice, "totalPrice is required");
        DateTimeRange newTimeRange = Validation.required(timeRange, "timeRange is required");
        validateTotalPrice(newTotalPrice);
        this.totalPrice = newTotalPrice;
        this.timeRange = newTimeRange;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, carId, timeRange, totalPrice());
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Rental otherRental
                && equalsCasted(otherRental);
    }

    private boolean equalsCasted(Rental other) {
        return Objects.equals(id, other.id)
                && Objects.equals(customerId, other.customerId)
                && Objects.equals(carId, other.carId)
                && Objects.equals(timeRange, other.timeRange)
                && Objects.equals(totalPrice(), other.totalPrice());
    }

    private void validateTotalPrice(BigDecimal totalPrice) {
        if (totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalPrice cannot be negative");
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private RentalId id;
        private CustomerId customerId;
        private CarId carId;
        private DateTimeRange timeRange;
        private BigDecimal totalPrice;

        private Builder() {

        }

        public Builder id(RentalId id) {
            this.id = id;
            return this;
        }

        public Builder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder carId(CarId carId) {
            this.carId = carId;
            return this;
        }

        public Builder timeRange(DateTimeRange timeRange) {
            this.timeRange = timeRange;
            return this;
        }


        public Builder totalPrice(BigDecimal totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Rental build() {
            return new Rental(this);
        }
    }
}