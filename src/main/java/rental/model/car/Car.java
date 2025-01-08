package rental.model.car;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import rental.Validation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.Objects;

public class Car {

    private CarId id;
    private Model model;
    private Year year;
    private BigDecimal dailyPrice;

    public Car(Builder builder) {
        this.id = builder.id;
        this.year = validateYear(builder.year);
        this.model = validateModel(builder.model);
        this.dailyPrice = validateDailyPrice(builder.dailyPrice);
    }

    public CarId id() {
        return this.id;
    }

    public Year year() {
        return this.year;
    }

    public Model model() {
        return this.model;
    }

    public BigDecimal dailyPrice() {
        return dailyPrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    public void created(CarId id) {
        this.id = id;
    }

    public void update(Model model, Year year, BigDecimal dailyPrice) {
        this.model = validateModel(model);
        this.year = validateYear(year);
        this.dailyPrice = validateDailyPrice(dailyPrice);
    }

    private Year validateYear(Year year) {
        return Validation.required(year, "Car year is required.");
    }

    private Model validateModel(Model model) {
        return Validation.required(model, "Car model is required.");
    }

    private BigDecimal validateDailyPrice(BigDecimal dailyPrice) {
        Validation.required(dailyPrice, "Car dailyPrice is required.");
        if (dailyPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Car dailyPrice cannot be negative.");
        }
        return dailyPrice;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, model, year, dailyPrice());
    }

    @Override
    public boolean equals(Object other) {
        return other != null
                && getClass().equals(other.getClass())
                && equalsCasted((Car) other);
    }

    private boolean equalsCasted(Car other) {
        return Objects.equals(id, other.id)
                && Objects.equals(model, other.model)
                && Objects.equals(year, other.year)
                && Objects.equals(dailyPrice(), other.dailyPrice());
    }

    public Builder toBuilder() {
        return builder()
                .id(this.id)
                .model(this.model)
                .year(this.year)
                .dailyPrice(this.dailyPrice);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CarId id;
        private Model model;
        private Year year;
        private BigDecimal dailyPrice;

        private Builder() {

        }

        public Builder id(CarId id) {
            this.id = id;
            return this;
        }

        public Builder model(Model model) {
            this.model = model;
            return this;
        }

        public Builder year(Year year) {
            this.year = year;
            return this;
        }

        public Builder dailyPrice(BigDecimal dailyPrice) {
            this.dailyPrice = dailyPrice;
            return this;
        }

        public Car build() {
            return new Car(this);
        }
    }
}
