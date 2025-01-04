package rental.it.infrastructure.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rental.fixture.CarFixture;
import rental.infrastructure.repository.car.CarRepositoryAdapter;
import rental.model.car.*;
import rental.model.exception.CarNotFoundException;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(CarRepositoryAdapter.class)
public class CarRepositoryTest {

    @Autowired
    private CarRepository repository;

    @Test
    void mustCreateCarSuccessfully() {
        Car car = CarFixture.aCarWithoutId().build();

        repository.save(car);

        Car resultCar = repository.getById(car.id()).orElseThrow();

        assertThat(car.id(), is(notNullValue()));
        assertThat(resultCar, is(car));
    }

    @Test
    void mustUpdateCarSuccessfully() {
        Car car = repository.getById(CarId.of(1L)).orElseThrow();
        Model newModel = new Model(new Make("New make"), "New model");
        Year newYear = Year.of(2010);
        BigDecimal newDailyPrice = BigDecimal.valueOf(150.00);
        car.update(newModel, newYear, newDailyPrice);

        repository.save(car);
        Car retrieved = repository.getById(CarId.of(1L)).orElseThrow();

        assertThat(retrieved, is(car));
    }

    @Test
    void mustGetById() {
        CarId carId = CarId.of(1L);

        Car car = repository.getById(carId).orElseThrow();

        assertThat(car.id().value(), is(1L));
        assertThat(car.model().make().value(), is("Toyota"));
        assertThat(car.model().name(), is("Corolla"));
        assertThat(car.year().getValue(), is(2024));
    }

    @Test
    void getByIdReturnsEmptyWhenCarIsNotFound() {
        CarId carId = CarId.of(999L);

        Optional<Car> car = repository.getById(carId);

        assertThat(car.isEmpty(), is(true));
    }

    @Test
    void mustDeleteCarSuccessfully() {
        CarId carId = CarId.of(1L);

        repository.deleteById(carId);

        Optional<Car> resultCar = repository.getById(carId);
        assertThat(resultCar.isEmpty(), is(true));
    }

    @Test
    void deleteThrowsExceptionWhenCarIsNotFound() {
        CarId carId = CarId.of(999L);
        CarNotFoundException exception = assertThrows(
                CarNotFoundException.class,
                () -> repository.deleteById(carId));

        assertThat(exception.getMessage(), is("Car not found with id: " + carId.value()));
    }

    @Test
    void mustGetAllCars() {
        List<Car> cars = repository.getAll();

        assertThat(cars.size(), is(greaterThanOrEqualTo(4)));
    }
}