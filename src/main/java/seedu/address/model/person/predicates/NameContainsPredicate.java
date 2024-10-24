package seedu.address.model.person.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsPredicate extends SupplierPredicate {

    public NameContainsPredicate(String keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Person person) {
        // Partial String matching unlike the normal AB3
        return person.getName().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsPredicate)) {
            return false;
        }

        NameContainsPredicate otherNameContainsPredicate = (NameContainsPredicate) other;
        return super.equals(otherNameContainsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword:", keyword).toString();
    }
}
