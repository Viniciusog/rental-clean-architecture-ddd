package rental.infrastructure.repository.car;

import jakarta.persistence.*;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.Make;
import rental.model.car.Model;

import java.math.BigDecimal;
import java.time.Year;

@Entity(name = "Car")
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "model_year", nullable = false)
    private Short year;

    @Column(name = "daily_price", nullable = false)
    private BigDecimal dailyPrice;

    public CarEntity() {

    }

    public CarEntity(Car car) {
        this.id = car.id() == null ? null : car.id().value();
        this.make = car.model().make().value();
        this.model = car.model().name();
        this.year = (short)car.year().getValue();
        this.dailyPrice = car.dailyPrice();
    }

    public Car toCar() {
        return Car.builder()
                .id(CarId.of(id))
                .model(new Model(new Make(make), model))
                .year(Year.of(year))
                .dailyPrice(dailyPrice)
                .build();
    }

    public Long id() {
        return id;
    }
}