package rental.model.car;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import rental.model.Validation;

import java.util.Objects;

public final class Make {

    private final String value;

    public Make(String value) {
        this.value = Validation.required(value, "Make value is required.");
    }

    public String value() {
        return this.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(StringUtils.upperCase(this.value));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Make otherMake
                && StringUtils.equalsIgnoreCase(value, otherMake.value());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}