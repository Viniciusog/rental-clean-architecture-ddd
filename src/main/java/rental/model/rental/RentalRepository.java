package rental.model.rental;

import rental.model.car.CarId;
import rental.model.customer.CustomerId;

import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    RentalId save(Rental rental);
    void deleteById(RentalId rentalId);
    Optional<Rental> getById(RentalId rentalId);
    List<Rental> getAll();
    List<Rental> getByCustomerId(CustomerId id);
    List<Rental> getByCarIdAndTimeRange(CarId carId, DateTimeRange timeRange);
    boolean existsByCarIdAndTimeRange(CarId carId, DateTimeRange timeRange);
    boolean existsByCarIdAndTimeRangeWithRentalExclusion(CarId carId,
                                                         DateTimeRange timeRange,
                                                         RentalId rentalId);
}