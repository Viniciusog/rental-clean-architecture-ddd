package rental.model.car;

import rental.model.rental.RentalRepository;

import java.time.LocalDate;

public class CarAvailabilityChecker {

    private RentalRepository repository;

    public CarAvailabilityChecker(RentalRepository repository) {
        this.repository = repository;
    }

    public boolean isCarAvailable(CarId carId, LocalDate initialDate, LocalDate endDate) {
        return repository.getByCarIdAndDateInterval(carId, initialDate, endDate).isEmpty();
    }
}