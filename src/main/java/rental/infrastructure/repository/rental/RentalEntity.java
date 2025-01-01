package rental.infrastructure.repository.rental;

import jakarta.persistence.*;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "Rental")
@Table(name = "rental")
public class RentalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "car_id", nullable = false)
    private Long carId;

    @Column(name = "initial_date", nullable = false)
    private LocalDate initialDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public RentalEntity() {

    }

    public RentalEntity(Rental rental) {
        this.id = rental.id() == null ? null : rental.id().value();
        this.customerId = rental.customerId().value();
        this.carId = rental.carId().value();
        this.initialDate = rental.initialDate();
        this.endDate = rental.endDate();
        this.totalPrice = rental.totalPrice();
    }

    public Rental toRental() {
        return Rental.builder()
                .id(RentalId.of(id))
                .carId(CarId.of(carId))
                .customerId(CustomerId.of(customerId))
                .initialDate(initialDate)
                .endDate(endDate)
                .totalPrice(totalPrice)
                .build();
    }
}