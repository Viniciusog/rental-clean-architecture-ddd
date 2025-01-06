package rental.infrastructure.repository.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rental.model.car.CarId;
import rental.model.customer.CustomerId;
import rental.model.exception.RentalNotFoundException;
import rental.model.rental.DateTimeRange;
import rental.model.rental.Rental;
import rental.model.rental.RentalId;
import rental.model.rental.RentalRepository;

import java.util.List;
import java.util.Optional;

@Component
public class RentalRepositoryAdapter implements RentalRepository {

    @Autowired
    private JpaRentalRepository repository;

    @Override
    public RentalId save(Rental rental) {
        RentalEntity entity = new RentalEntity(rental);
        repository.save(entity);
        rental.created(RentalId.of(entity.id()));
        return RentalId.of(entity.id());
    }

    @Override
    public void deleteById(RentalId rentalId) {
        if (!repository.existsById(rentalId.value())) {
            throw new RentalNotFoundException(rentalId);
        }
        repository.deleteById(rentalId.value());
    }

    @Override
    public Optional<Rental> getById(RentalId rentalId) {
        return repository.findById(rentalId.value()).map(RentalEntity::toRental);
    }

    @Override
    public List<Rental> getAll() {
        return repository.findAll().stream().map(RentalEntity::toRental).toList();
    }

    @Override
    public List<Rental> getByCustomerId(CustomerId id) {
        return repository.findByCustomerId(id.value()).stream().map(RentalEntity::toRental).toList();
    }

    @Override
    public List<Rental> getByCarIdAndDateInterval(CarId carId, DateTimeRange timeRange) {
        return repository.findByCarIdAndDateRange(carId.value(), timeRange.start(), timeRange.end())
                .stream()
                .map(RentalEntity::toRental)
                .toList();
    }

    @Override
    public boolean existsByCarIdAndTimeRange(CarId carId, DateTimeRange timeRange) {
        return repository.existsByCarIdAndTimeRange(carId.value(), timeRange.start(), timeRange.end());
    }
}