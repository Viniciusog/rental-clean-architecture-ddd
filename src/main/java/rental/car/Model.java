package rental.model.car;

import org.apache.commons.lang3.StringUtils;
import rental.model.Validation;

import java.util.Objects;

public class Model {

    private final String name;
    private final Make make;

    public Model(Make make, String name) {
        this.make = make;
        this.name = Validation.required(name, "Model name is required.");
    }

    public String name() {
        return this.name;
    }

    public Make make() {
        return this.make;
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, StringUtils.upperCase(this.name));
    }

    @Override
    public boolean equals(Object other) {
        return other != null
                && getClass().equals(other.getClass())
                && equalsCasted((Model) other);
    }

    private boolean equalsCasted(Model other) {
        return Objects.equals(make, other.make)
                && StringUtils.equalsIgnoreCase(name, other.name);
    }
}