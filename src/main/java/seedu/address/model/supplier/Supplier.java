package seedu.address.model.supplier;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.product.Product;


/**
 * Represents a Supplier in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Supplier {

    // Identity fields
    private final SupplierName name;
    private final SupplierContact phone;
    private final SupplierEmail email;

    // Data fields
    private final Company company;
    private final Product product;
    private final SupplierStatus status;
    /**
     * Every field must be present and not null.
     */
    public Supplier(SupplierName name, SupplierContact phone, SupplierEmail email,
                    Company company, Product product, SupplierStatus status) {
        requireAllNonNull(name, phone, email, company, product);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.product = product;
        this.status = status;
    }

    public SupplierName getName() {
        return name;
    }

    public SupplierContact getPhone() {
        return phone;
    }

    public SupplierEmail getEmail() {
        return email;
    }

    public Company getCompany() {
        return company;
    }
    public Product getProduct() {
        return product;
    }
    public SupplierStatus getSupplierStatus() {
        return status;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameSupplier(Supplier otherSupplier) {
        if (otherSupplier == this) {
            return true;
        }

        return otherSupplier != null
                && otherSupplier.getName().equals(getName());
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
        if (!(other instanceof Supplier supplier)) {
            return false;
        }

        Supplier otherSupplier = (Supplier) other;
        return name.equals(otherSupplier.name)
                && phone.equals(otherSupplier.phone)
                && email.equals(otherSupplier.email)
                && company.equals(otherSupplier.company)
                && product.equals(otherSupplier.product);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, company, product);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("company", company)
                .add("product", product)
                .add("status", status)
                .toString();
    }

}
