package seedu.address.model.person;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Supplier in the address book.
 * Inherits from Person and adds additional fields for Supplier-specific information.
 */
public class Supplier extends Person {

    private final String companyName;
    private final List<String> ingredients;
    private final String supplyFrequency;
    private final int minimumOrderQuantity;
    private final List<String> paymentTerms;  // Multiple payment options

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address,
                    DietaryPreference preference, Remark remark, Set<Tag> tags,
                    String companyName, List<String> ingredients, String supplyFrequency, int minimumOrderQuantity,
                    List<String> paymentTerms) {
        super(name, phone, email, address, preference, remark, tags);
        this.companyName = companyName;
        this.ingredients = ingredients;
        this.supplyFrequency = supplyFrequency;
        this.minimumOrderQuantity = minimumOrderQuantity;
        this.paymentTerms = paymentTerms;
    }

    public String getCompanyName() {
        return companyName;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getSupplyFrequency() {
        return supplyFrequency;
    }

    public int getMinimumOrderQuantity() {
        return minimumOrderQuantity;
    }

    public List<String> getPaymentTerms() {
        return paymentTerms;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Supplier)) {
            return false;
        }

        Supplier otherSupplier = (Supplier) other;
        return super.equals(otherSupplier)
                && otherSupplier.getCompanyName().equals(getCompanyName())
                && otherSupplier.getIngredients().equals(getIngredients())
                && otherSupplier.getSupplyFrequency().equals(getSupplyFrequency())
                && otherSupplier.getMinimumOrderQuantity() == getMinimumOrderQuantity()
                && otherSupplier.getPaymentTerms().equals(getPaymentTerms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), companyName, ingredients, supplyFrequency, minimumOrderQuantity, paymentTerms);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" Company: ")
                .append(getCompanyName())
                .append(" Ingredients: ")
                .append(getIngredients())
                .append(" Supply Frequency: ")
                .append(getSupplyFrequency())
                .append(" Minimum Order Quantity: ")
                .append(getMinimumOrderQuantity())
                .append(" Payment Terms: ")
                .append(getPaymentTerms());
        return builder.toString();
    }
}
