package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Combines NameContainsKeywordsPredicate and OrgContainsKeywordsPredicate
 */
public class CompoundedPredicate implements Predicate<Person> {

    private final NameContainsKeywordsPredicate namePredicate;
    private final OrgContainsKeywordsPredicate orgPredicate;

    /**
     * Initialises the predictaes
     * @param namePredicate - NameContainsKeywordsPredicat
     * @param orgPredicate - OrgContainsKeywordsPredicate
     */
    public CompoundedPredicate(NameContainsKeywordsPredicate namePredicate, OrgContainsKeywordsPredicate orgPredicate) {
        this.namePredicate = namePredicate;
        this.orgPredicate = orgPredicate;
    }
    @Override
    public boolean test(Person person) {
        return namePredicate.test(person) && orgPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompoundedPredicate)) {
            return false;
        }

        CompoundedPredicate otherCompoundedPredicate = (CompoundedPredicate) other;
        return orgPredicate.equals(otherCompoundedPredicate.orgPredicate)
                && namePredicate.equals(otherCompoundedPredicate.namePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("name predicate", namePredicate.toString())
                .add("organisation predicate", orgPredicate.toString()).toString();
    }
}
