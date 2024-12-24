package rental.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Objects;

public abstract class SurrogateId {

    private final Long value;

    protected SurrogateId(Long value) {
        this.value = value;
    }

    private void validate(Long value) {
        if (value == null) {
            throw new IllegalArgumentException("value is required");
        }
        if (value <= 0) {
            throw new IllegalArgumentException("value must be greater than zero");
        }
    }

    public Long value() {
        return this.value;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object other) {
        return other != null
                && this.getClass().equals(other.getClass())
                && equalsCasted((SurrogateId) other);
    }

    private boolean equalsCasted(SurrogateId other) {
        return Objects.equals(value, other.value);
    }
}