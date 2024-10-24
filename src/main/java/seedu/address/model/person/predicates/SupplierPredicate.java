package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Abstract class for predicates that operate on a {@code Person}.
 */
public abstract class SupplierPredicate implements Predicate<Person> {
    protected final String keyword;

    public SupplierPredicate(String keywords) {
        this.keyword = keywords;
    }

    @Override
    public abstract boolean test(Person person);

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SupplierPredicate)) {
            return false;
        }

        SupplierPredicate otherPredicate = (SupplierPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keyword)
                .toString();
    }
}
