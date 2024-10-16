package seedu.address.model.owner;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents an Owner in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Owner {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;

    /**
     * Every field must be present and not null.
     */
    public Owner(Name name, Phone phone, Email email, Address address) {
        requireAllNonNull(name, phone, email, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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


    /**
     * Returns true if both owners have the same ID.
     * This defines a weaker notion of equality between two owner.
     */
    public boolean isSameOwner(Owner otherOwner) {
        if (otherOwner == this) {
            return true;
        }

        return otherOwner != null
            && otherOwner.getName().equals(getName());
    }

    /**
     * Returns true if both owners have the same identity and data fields.
     * This defines a stronger notion of equality between two owners.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Owner)) {
            return false;
        }

        Owner otherOwner = (Owner) other;
        return name.equals(otherOwner.name)
            && phone.equals(otherOwner.phone)
            && email.equals(otherOwner.email)
            && address.equals(otherOwner.address);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", name)
            .add("phone", phone)
            .add("email", email)
            .add("address", address)
            .toString();
    }

}
