package seedu.internbuddy.model.company;

import java.util.function.Predicate;

import seedu.internbuddy.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Company}'s {@code Name} matches any of the keywords given.
 */
public class CompanyToViewPredicate implements Predicate<Company> {
    private final Company companyToView;

    public CompanyToViewPredicate(Company companyToView) {
        this.companyToView = companyToView;
    }

    @Override
    public boolean test(Company company) {
        return companyToView.isSameCompany(company);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyToViewPredicate)) {
            return false;
        }

        CompanyToViewPredicate otherCompanyToViewPredicate = (CompanyToViewPredicate) other;
        return companyToView.equals(otherCompanyToViewPredicate.companyToView);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("companyToView", companyToView).toString();
    }
}
