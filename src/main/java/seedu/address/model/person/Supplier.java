package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.order.SupplyOrder;
import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;

/**
 * Represents a Supplier in the address book.
 * Inherits from Person and adds additional fields for Supplier-specific information.
 */
public class Supplier extends Person {

    private final List<SupplyOrder> openSupplyOrders; // stores a list of open/unfulfilled supply orders
    private final Ingredients ingredientsSupplied; // list of ingredients supplied by the supplier

    /**
     * Every field must be present and not null.
     */
    public Supplier(Name name, Phone phone, Email email, Address address,
                     Ingredients ingredientsSupplied,
                    Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.ingredientsSupplied = ingredientsSupplied;
        this.openSupplyOrders = new ArrayList<>();
    }

    public List<SupplyOrder> getOpenSupplyOrders() {
        return openSupplyOrders;
    }

    public void addSupplyOrder(SupplyOrder supplyOrder) {
        openSupplyOrders.add(supplyOrder);
    }

    public void removeSupplyOrder(SupplyOrder supplyOrder) {
        openSupplyOrders.remove(supplyOrder);
    }

    public Ingredients getIngredientsSupplied() {
        return ingredientsSupplied;
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
                && otherSupplier.getIngredientsSupplied().equals(getIngredientsSupplied());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ingredientsSupplied);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" Ingredients Supplied: ")
                .append(String.join(", ", getIngredientsSupplied().getIngredientNames()));
        return builder.toString();
    }
}
