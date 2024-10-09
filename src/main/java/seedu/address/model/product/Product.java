package seedu.address.model.product;

import seedu.address.commons.util.ToStringBuilder;
import java.util.Objects;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class Product {
    private final seedu.address.model.product.ProductName name;

    /**
     * Every field must be present and not null.
     */
    public Product(seedu.address.model.product.ProductName name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public ProductName getName() {
        return name;
    }


    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameProduct(Product otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
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
        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return name.equals(otherProduct.name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }
}
