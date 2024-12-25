package rental.fixture;

import rental.model.Email;
import rental.model.customer.Customer;
import rental.model.customer.CustomerId;
import rental.model.customer.CustomerName;

public class CustomerFixture {

    public static final CustomerId CUSTOMER_ID = CustomerId.of(123L);
    public static final CustomerName CUSTOMER_NAME = CustomerName.of("Steve Jobs");
    public static final Email EMAIL = Email.of("steve@server.com");

    public static Customer.Builder aCustomerWithId() {
        return builder();
    }

    public static Customer.Builder aCustomerWithoutId() {
        return builder().id((Long) null);
    }

    public static Customer.Builder anActiveCustomer() {
        return builder().active(true);
    }

    public static Customer.Builder aCustomer(Long id, String name) {
        return builder().id(id).name(name);
    }

    public static Customer.Builder anInactiveCustomer() {
        return builder().active(false);
    }

    public static Customer.Builder builder() {
        return Customer.builder()
                .id(CUSTOMER_ID)
                .name(CUSTOMER_NAME)
                .email(EMAIL);
    }
}