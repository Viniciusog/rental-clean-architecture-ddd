package rental.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rental.application.AppTransaction;
import rental.application.customer.*;
import rental.model.customer.CustomerRepository;

@Configuration
public class CustomerUseCaseContext {

    @Autowired
    private AppTransaction transaction;

    @Autowired
    private CustomerRepository customerRepository;

    @Bean
    public CreateCustomerUseCase createCustomerUseCase() {
        return new CreateCustomerUseCase(transaction, customerRepository);
    }

    @Bean
    public GetCustomerByIdUseCase getCustomerByIdUseCase() {
        return new GetCustomerByIdUseCase(customerRepository);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase() {
        return new DeleteCustomerUseCase(transaction, customerRepository);
    }

    @Bean
    public ActivateCustomerUseCase activateCustomerUseCase() {
        return new ActivateCustomerUseCase(transaction, customerRepository);
    }

    @Bean
    public DeactivateCustomerUseCase deactivateCustomerUseCase() {
        return new DeactivateCustomerUseCase(transaction, customerRepository);
    }

}