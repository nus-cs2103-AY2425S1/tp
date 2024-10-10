package seedu.address.model.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.product.Product;
import seedu.address.model.tag.Tag;

/**
 * Represents a Supplier in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Product> assignedProducts = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public boolean addProduct(Product product) {
        return this.assignedProducts.add(product);
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
     * Returns true if both suppliers have the same name.
     * This defines a weaker notion of equality between two suppliers.
     */
    public boolean isSameSupplier(Supplier otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName());
    }

    /**
     * Returns true if both suppliers have the same identity and data fields.
     * This defines a stronger notion of equality between two suppliers.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Supplier)) {
            return false;
        }

        Supplier otherSupplier = (Supplier) other;
        return name.equals(otherSupplier.name)
                && phone.equals(otherSupplier.phone)
                && email.equals(otherSupplier.email)
                && address.equals(otherSupplier.address)
                && tags.equals(otherSupplier.tags);
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
