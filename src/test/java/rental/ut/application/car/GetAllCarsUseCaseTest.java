package rental.ut.application.car;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rental.application.car.GetAllCarsUseCase;
import rental.model.car.Car;
import rental.model.car.CarId;
import rental.model.car.CarRepository;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static rental.fixture.CarFixture.aCarWithId;

public class GetAllCarsUseCaseTest {

    private CarRepository repository;
    private GetAllCarsUseCase useCase;

    @BeforeEach
    public void beforeEach() {
        repository = mock(CarRepository.class);
        useCase = new GetAllCarsUseCase(repository);
    }

    @Test
    void mustGetAllCars() {
        List<Car> expectedCars = List.of(
                aCarWithId().id(CarId.of(1L)).build(),
                aCarWithId().id(CarId.of(2L)).build());
        when(repository.getAll()).thenReturn(expectedCars);

        List<Car> resultCars = useCase.execute();

        verify(repository).getAll();
        assertThat(resultCars, containsInAnyOrder(expectedCars.toArray()));
    }
}
