package seedu.address.model.client;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.model.tag.Tag;

/**
 * Represents a Client in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Client {

    private static int numOfClients = 0;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final int id;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final List<RentalInformation> rentalInformationList = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Set<Tag> tags, List<RentalInformation> rentalInformationList) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.id = numOfClients;
        numOfClients++;
        this.rentalInformationList.addAll(rentalInformationList);
    }

    /**
     * Every field must be present and not null.
     */
    public Client(Name name, Phone phone, Email email, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
        this.id = numOfClients;
        numOfClients++;
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

    public Integer getId() {
        return id;
    }

    public List<RentalInformation> getRentalInformation() {
        return Collections.unmodifiableList(rentalInformationList);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Client otherClient) {
        if (otherClient == this) {
            return true;
        }

        return (isSameName(otherClient) && isSamePhone(otherClient) && isSameEmail(otherClient));
    }

    private boolean isSameName(Client otherClient) {
        return otherClient != null && otherClient.getName().equals(getName());
    }

    private boolean isSamePhone(Client otherClient) {
        return otherClient != null && otherClient.getPhone().equals(getPhone());
    }

    private boolean isSameEmail(Client otherClient) {
        return otherClient != null && otherClient.getEmail().equals(getEmail());
    }

    /**
     * Checks if both the email and phone fields of this {@code Client} are empty.
     *
     * @return {@code true} if both email and phone fields are empty, {@code false} otherwise
     */
    public boolean isEmailPhoneEmpty() {
        System.out.println("email: " + this.email.value + " phone: " + this.phone.value);
        return this.email.value == null && this.phone.value == null;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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

        return this.name.equals(otherClient.name)
                && this.phone.equals(otherClient.phone)
                && this.email.equals(otherClient.email)
                && this.rentalInformationList.equals(otherClient.rentalInformationList)
                && this.tags.equals(otherClient.tags);

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, rentalInformationList, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("rental information", rentalInformationList)
                .add("tags", tags)
                .toString();
    }

}
