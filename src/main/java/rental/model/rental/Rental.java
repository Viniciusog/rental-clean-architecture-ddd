package rental.model.rental;

import rental.Validation;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Rental {

    private RentalId id;
    private CustomerId customerId;
    private CarId carId;
    private final LocalDate initialDate;
    private final LocalDate endDate;
    private final BigDecimal totalPrice;

    public Rental(Builder builder) {
        this.id = builder.id;
        this.customerId = Validation.required(builder.customerId, "customerId is required");
        this.carId = Validation.required(builder.carId, "carId is required");
        this.initialDate = Validation.required(builder.initialDate, "initialDate is required");
        this.endDate = Validation.required(builder.endDate, "endDate is required");
        this.totalPrice = Validation.required(builder.totalPrice, "totalPrice is required");
        validateDates();
        validateTotalPrice();
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

    public LocalDate initialDate() {
        return initialDate;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public BigDecimal totalPrice() {
        return totalPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    private void validateDates() {
        if (initialDate.isAfter(endDate)) {
            throw new IllegalArgumentException("initialDate must be less than or equal to endDate");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, carId, initialDate, endDate, totalPrice());
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
                && Objects.equals(initialDate, other.initialDate)
                && Objects.equals(endDate, other.endDate)
                && Objects.equals(totalPrice(), other.totalPrice());
    }

    private void validateTotalPrice() {
        if (this.totalPrice.compareTo(BigDecimal.ZERO) < 0) {
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
        private LocalDate initialDate;
        private LocalDate endDate;
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

        public Builder initialDate(LocalDate initialDate) {
            this.initialDate = initialDate;
            return this;
        }

        public Builder endDate(LocalDate endDate) {
            this.endDate = endDate;
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