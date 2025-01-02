package rental.model.rental;

import rental.infrastructure.repository.rental.RentalEntity;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RentalRepository {
    RentalId save(Rental rental);
    void deleteById(RentalId rentalId);
    Optional<Rental> getById(RentalId rentalId);
    List<Rental> getAll();
    List<Rental> getByCustomerId(CustomerId id);
    List<Rental> getByCarIdAndDateInterval(CarId carId,
                                         LocalDate initialDate,
                                         LocalDate endDate);
}