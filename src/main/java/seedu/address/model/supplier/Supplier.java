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
    private final Company company;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Product> products = new HashSet<>();
    private final SupplierStatus status;

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Company company, Set<Tag> tags,
                    Set<Product> products, SupplierStatus status) {
        requireAllNonNull(name, phone, email, company, tags, products);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.tags.addAll(tags);
        this.products.addAll(products);
        this.status = status;
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

    public Company getCompany() {
        return company;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
    /**
     * Returns the status of the supplier.
     */
    public SupplierStatus getStatus() {
        return status;
    }

    /**
     * Returns an immutable product set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Product> getProducts() {
        return Collections.unmodifiableSet(products);
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
                && company.equals(otherSupplier.company)
                && tags.equals(otherSupplier.tags)
                && products.equals(otherSupplier.products);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, company, tags, products);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("company", company)
                .add("tags", tags)
                .add("products", products)
                .add("status", status)
                .toString();
    }

}
