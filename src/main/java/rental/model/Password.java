package rental.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import rental.Validation;

import java.util.Objects;

public class Password {

    private final String value;

    private Password(String password) {
        value = Validation.required(password, "password is required");
    }

    public static Password of(String password) {
        return new Password(password);
    }

    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Password otherPassword
                && Objects.equals(value, otherPassword.value);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}