package rental.model.customer;

public class CustomerFilter {

    private final String nameStarting;
    private final String nameContaining;

    public CustomerFilter(String nameStarting, String nameContaining) {
        this.nameStarting = nameStarting;
        this.nameContaining = nameContaining;
    }

    public String nameStarting() {
        return this.nameStarting;
    }

    public String nameContaining() {
        return this.nameContaining;
    }
}