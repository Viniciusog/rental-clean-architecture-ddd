package rental.model.car;

import rental.model.Validation;

public class Make {

    private final String value;

    public Make(String value) {
        this.value = Validation.required(value, "Make value is required.");
    }

    public String value() {
        return this.value;
    }
}