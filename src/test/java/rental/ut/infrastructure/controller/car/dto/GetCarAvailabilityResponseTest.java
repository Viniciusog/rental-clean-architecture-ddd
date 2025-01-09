package rental.ut.infrastructure.controller.car;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import rental.infrastructure.controller.car.GetCarAvailabilityResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GetCarAvailabilityResponseTest {

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void createSuccessfully(boolean availableParam) {
        GetCarAvailabilityResponse response = new GetCarAvailabilityResponse(availableParam);

        assertThat(response.available(), is(availableParam));
    }
}