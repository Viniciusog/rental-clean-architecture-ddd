package rental.infrastructure.repository.rental;

import jakarta.persistence.*;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;

import java.math.BigDecimal;
import java.time.Instant;

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

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    public RentalEntity() {

    }

    public RentalEntity(Rental rental) {
        this.id = rental.id() == null ? null : rental.id().value();
        this.customerId = rental.customerId().value();
        this.carId = rental.carId().value();
        this.startTime = rental.timeRange().start();
        this.endTime = rental.timeRange().end();
        this.totalPrice = rental.totalPrice();
    }

    public Rental toRental() {
        return Rental.builder()
                .id(RentalId.of(id))
                .carId(CarId.of(carId))
                .customerId(CustomerId.of(customerId))
                .timeRange(DateTimeRange.of(startTime, endTime))
                .totalPrice(totalPrice)
                .build();
    }

    public Long id() {
        return id;
    }
}