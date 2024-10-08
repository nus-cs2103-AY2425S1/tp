package seedu.address.model.vendor;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Vendor in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Vendor {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Vendor(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both vendors have the same name.
     * This defines a weaker notion of equality between two vendors.
     */
    public boolean isSameVendor(Vendor otherVendor) {
        if (otherVendor == this) {
            return true;
        }

        return otherVendor != null
                && otherVendor.getName().equals(getName());
    }

    /**
     * Returns true if both vendors have the same identity and data fields.
     * This defines a stronger notion of equality between two vendors.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Vendor)) {
            return false;
        }

        Vendor otherVendor = (Vendor) other;
        return name.equals(otherVendor.name)
                && phone.equals(otherVendor.phone)
                && email.equals(otherVendor.email)
                && address.equals(otherVendor.address)
                && tags.equals(otherVendor.tags);
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
