package rental.ut.infrastructure.controller.car;

import org.junit.jupiter.api.Test;
import rental.infrastructure.controller.car.CreateCarRequest;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static rental.fixture.CarFixture.*;

public class CreateCarRequestTest {

    @Test
    void createSuccessfully() {
        String make = MAKE.value();
        String model = MODEL.name();
        short year = (short)YEAR.getValue();
        BigDecimal dailyPrice = DAILY_PRICE;

        CreateCarRequest request = new CreateCarRequest(make, model, year, dailyPrice);

        assertThat(request.make(), is(make));
        assertThat(request.model(), is(model));
        assertThat(request.year(), is(year));
        assertThat(request.dailyPrice(), is(dailyPrice));
    }
}