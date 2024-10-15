package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.client.insurance.InsurancePlansManager;
import seedu.address.model.tag.Tag;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    public static final String NO_CLAIMS_MESSAGE = "No open claims";
    public static final String OPEN_CLAIMS_MESSAGE = "%1$s open claims";

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final InsurancePlansManager insurancePlansManager;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Address address,
                  InsurancePlansManager insurancePlansManager, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.insurancePlansManager = insurancePlansManager;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public InsurancePlansManager getInsurancePlansManager() {
        return insurancePlansManager;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both clients have the same name.
     * This defines a weaker notion of equality between two clients.
     */
    public boolean isSameClient(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return otherClient != null
                && otherClient.getName().equals(getName());
    }

    /**
     * Retrieves a formatted string representing the number of open claims.
     *
     * This method checks the number of open claims managed by the {@link InsurancePlansManager}.
     * If there are no open claims, it returns a predefined message indicating that there are no claims.
     * Otherwise, it returns a formatted message displaying the count of open claims.
     *
     * @return A string message indicating the number of open claims. If there are no open claims,
     *         it returns {@link #NO_CLAIMS_MESSAGE}. If there are open claims, it returns
     *         a formatted string with the number of open claims using {@link #OPEN_CLAIMS_MESSAGE}.
     */
    public String getClaimsString() {
        int numberOfOpenClaims = this.insurancePlansManager.getNumberOfOpenClaims();
        return (numberOfOpenClaims == 0) ? NO_CLAIMS_MESSAGE : String.format(OPEN_CLAIMS_MESSAGE, numberOfOpenClaims);
    }

    /**
     * Returns true if both clients have the same identity and data fields.
     * This defines a stronger notion of equality between two clients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Client)) {
            return false;
        }

        Client otherClient = (Client) other;
        return name.equals(otherClient.name)
                && phone.equals(otherClient.phone)
                && email.equals(otherClient.email)
                && address.equals(otherClient.address)
                && tags.equals(otherClient.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
