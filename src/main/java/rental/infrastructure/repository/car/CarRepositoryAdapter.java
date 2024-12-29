package rental.infrastructure.repository.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;
import rental.model.exception.CarNotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class CarRepositoryAdapter implements CarRepository {

    @Autowired
    private JpaCarRepository jpaRepository;

    @Override
    public void save(Car car) {
        CarEntity entity = new CarEntity(car);
        jpaRepository.save(entity);
        car.created(CarId.of(entity.id()));
    }

    @Override
    public void deleteById(CarId carId) {
        if (!jpaRepository.existsById(carId.value())) {
            throw new CarNotFoundException(carId);
        }
        jpaRepository.deleteById(carId.value());
    }

    @Override
    public Optional<Car> getById(CarId carId) {
        return jpaRepository.findById(carId.value()).map(CarEntity::toCar);
    }

    @Override
    public List<Car> getAll() {
        return jpaRepository.findAll().stream().map(CarEntity::toCar).toList();
    }
}