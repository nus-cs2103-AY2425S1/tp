package seedu.address.model.person.predicates;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Company} matches any of the keywords given.
 */
public class CompanyContainsKeywordPredicate extends SupplierPredicate {

    public CompanyContainsKeywordPredicate(String keyword) {
        super(keyword);
    }

    @Override
    public boolean test(Person person) {
        // Partial String matching unlike the normal AB3
        return person.getCompany().toString().toLowerCase().contains(keyword.toLowerCase());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyContainsKeywordPredicate)) {
            return false;
        }

        CompanyContainsKeywordPredicate otherCompanyNameContainsPredicate =
                (CompanyContainsKeywordPredicate) other;

        return super.equals(otherCompanyNameContainsPredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword: ", keyword).toString();
    }
}
