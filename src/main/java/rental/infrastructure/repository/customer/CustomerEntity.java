package rental.infrastructure.repository.customer;

import jakarta.persistence.*;
import rental.model.customer.Customer;

@Entity(name= "Customer")
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    private Boolean active;

    public CustomerEntity() {

    }

    public CustomerEntity(Customer customer) {
        this.id = customer.id() == null ? null : customer.id().value();
        this.name = customer.name().value();
        this.email = customer.email() == null ? null : customer.email().address();
        this.active = customer.isActive();
    }

    public Customer toCustomer() {
        return Customer.builder()
                .id(id)
                .name(name)
                .email(email)
                .active(active)
                .build();
    }

    public Long id() {
        return this.id;
    }
}
