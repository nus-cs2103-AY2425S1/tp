package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

/**
 * Combines multiple keyword predicates into one.
 */
public class CombinedContainsKeywordsPredicate extends ContainsKeywordsPredicate {
    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code CombinedContainsKeywordsPredicate} with the given list of predicates.
     * This predicate will evaluate to true if the provided {@code Person} satisfies all the predicates.
     *
     * @param predicates A list of predicates that each evaluate a specific criterion on a {@code Person}.
     *                   This allows combining various keyword predicates for fields like name, phone, email, or tag
     *                   to enable multi-condition filtering functionality.
     */
    public CombinedContainsKeywordsPredicate(List<Predicate<Person>> predicates) {
        super(null); // Pass null or an empty list if you don't need it for composite
        this.predicates = predicates;
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CombinedContainsKeywordsPredicate)) {
            return false;
        }

        CombinedContainsKeywordsPredicate otherPredicate = (CombinedContainsKeywordsPredicate) other;
        return predicates.equals(otherPredicate.predicates);
    }

    @Override
    public String toString() {
        return String.format("%s%s", getClass().getCanonicalName(), predicates);
    }
}
