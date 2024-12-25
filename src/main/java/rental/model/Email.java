package rental.model;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import rental.model.exception.InvalidEmailException;

import java.util.Objects;

public class Email {

    private final String address;

    private Email(String address) {
        validate(address);
        this.address = address;
    }

    public static Email of(String address) {
        return new Email(address);
    }

    private void validate(String address) {
        InternetAddress internetAddress;
        try {
            internetAddress = new InternetAddress(address);
            internetAddress.validate();
        } catch (AddressException exception) {
            throw new InvalidEmailException(address, exception);
        }
        ensureNoPersonalInfo(address, internetAddress);
    }

    private void ensureNoPersonalInfo(String address, InternetAddress internetAddress) {
        if (internetAddress.getPersonal() != null) {
            throw new InvalidEmailException(address);
        }
    }

    public String address() {
        return this.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Email otherEmail
                && Objects.equals(address, otherEmail.address);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}