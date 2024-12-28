package rental.model.car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {

    void save(Car car);
    void deleteById(CarId carId);
    Optional<Car> getById(CarId carId);
    List<Car> getAll();
}