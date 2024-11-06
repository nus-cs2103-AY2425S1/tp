package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.product.Ingredients;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.Remark;

/**
 * Represents a Supplier with additional details about the ingredients they supply.
 */
public class Supplier extends Person {
    private Ingredients ingredientsSupplied;

    /**
     * Constructs a {@code Supplier} with all required fields.
     *
     * @param name               The supplier's name.
     * @param phone              The supplier's phone.
     * @param email              The supplier's email.
     * @param address            The supplier's address.
     * @param ingredientsSupplied A list of ingredients supplied by the supplier.
     * @param remark             Any remark associated with the supplier.
     * @param tags               A set of tags related to the supplier.
     */
    public Supplier(Name name, Phone phone, Email email, Address address,
                    Ingredients ingredientsSupplied, Remark remark, Set<Tag> tags) {
        super(name, phone, email, address, remark, tags);
        this.ingredientsSupplied = ingredientsSupplied;
    }

    /**
     * Retrieves the list of ingredients supplied by the supplier.
     *
     * @return The {@code Ingredients} object representing supplied ingredients.
     */
    public Ingredients getIngredientsSupplied() {
        return ingredientsSupplied;
    }
    // New method to update the ingredients supplied
    public void setIngredientsSupplied(Ingredients ingredientsSupplied) {
        this.ingredientsSupplied = ingredientsSupplied;
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
        return super.equals(otherSupplier) && otherSupplier.getIngredientsSupplied().equals(getIngredientsSupplied());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(super.toString());
        builder.append(" Ingredients Supplied: ")
                .append(String.join("", getIngredientsSupplied().getIngredientNames()));
        return builder.toString();
    }
}