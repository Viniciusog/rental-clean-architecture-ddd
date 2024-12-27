package rental.infrastructure.repository.car;

import jakarta.persistence.*;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.Make;
import rental.model.car.Model;

import java.time.Year;

@Entity(name = "Car")
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(name = "model_year", nullable = false)
    private Short year;

    public CarEntity() {

    }

    public CarEntity(Car car) {
        this.id = car.id() == null ? null : car.id().value();
        this.make = car.model().make().value();
        this.model = car.model().name();
        this.year = (short)car.year().getValue();
    }

    public Car toCar() {
        return Car.builder()
                .id(CarId.of(id))
                .model(new Model(new Make(make), model))
                .year(Year.of(year))
                .build();
    }

    public Long id() {
        return id;
    }
}