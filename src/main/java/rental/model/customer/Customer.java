package rental.model.customer;

import rental.Validation;
import rental.model.Email;
import rental.model.exception.CustomerIsAlreadyActiveException;
import rental.model.exception.CustomerIsAlreadyInactiveException;

import java.util.Objects;

public final class Customer {

    private CustomerId id;
    private CustomerName name;
    private Email email;
    private Boolean active;

    public Customer(Builder builder) {
        this.id = builder.id;
        this.name = validateName(builder.name);
        this.email = builder.email;
        this.active = Validation.required(builder.active, "active is required");
    }

    public CustomerId id() {
        return this.id;
    }

    public CustomerName name() {
        return this.name;
    }

    public Email email() {
        return this.email;
    }

    public Boolean isActive() {
        return Boolean.TRUE.equals(active);
    }

    public void deactivate() {
        if (!active) {
            throw new CustomerIsAlreadyInactiveException(id);
        }
        active = false;
    }

    public void activate() {
        if (active) {
            throw new CustomerIsAlreadyActiveException(id);
        }
        active = true;
    }

    public void created(CustomerId id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, active);
    }

    @Override
    public boolean equals(Object other) {
        return other != null
                && getClass().equals(other.getClass())
                && equalsCasted((Customer) other);
    }

    private boolean equalsCasted(Customer other) {
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(email, other.email)
                && Objects.equals(active, other.active);
    }

    public void update(CustomerName name, Email email) {
        this.name = validateName(name);
        this.email = validateEmail(email);
    }

    public CustomerName validateName(CustomerName name) {
        return Validation.required(name, "name is required");
    }

    public Email validateEmail(Email email) {
        return Validation.required(email, "email is required");
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private CustomerId id;
        private CustomerName name;
        private Email email;
        private Boolean active = true;

        private Builder() {

        }

        public Builder id(CustomerId id) {
            this.id = id;
            return this;
        }

        public Builder id(Long id) {
            this.id = id == null ? null : CustomerId.of(id);
            return this;
        }

        public Builder name(CustomerName name) {
            this.name = name;
            return this;
        }

        public Builder name(String name) {
            this.name = name == null ? null : CustomerName.of(name);
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder email(String email) {
            this.email = email == null ? null : Email.of(email);
            return this;
        }

        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}