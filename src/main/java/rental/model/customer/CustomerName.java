package rental.model.customer;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class CustomerName {

    private final String value;

    private CustomerName(String value) {
        validateValue(value);
        this.value = value;
    }

    private static void validateValue(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("value is required");
        }
        if (value.length() < 2) {
            throw new IllegalArgumentException("value must have two or more characters");
        }
    }

    public static CustomerName of(String name) {
        return new CustomerName(name);
    }

    public String value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(StringUtils.upperCase(value));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return other != null
                && getClass().equals(other.getClass())
                && equalsCasted((CustomerName) other);
    }

    private boolean equalsCasted(CustomerName other) {
        return StringUtils.equalsIgnoreCase(value, other.value);
    }




}
