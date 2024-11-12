package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;

/**
 * Tests that a {@code Vendor}'s {@code Budget} matches any of the keywords given.
 */
public class BudgetContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public BudgetContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (!(person instanceof Vendor)) {
            return false;
        }
        Vendor vendor = (Vendor) person;
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vendor.getBudget().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BudgetContainsKeywordsPredicate)) {
            return false;
        }

        BudgetContainsKeywordsPredicate otherBudgetContainsKeywordsPredicate =
                (BudgetContainsKeywordsPredicate) other;
        return keywords.equals(otherBudgetContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
