package seedu.ddd.model.contact.common.predicate;

import java.util.List;
import java.util.function.Predicate;

import seedu.ddd.commons.util.StringUtil;
import seedu.ddd.commons.util.ToStringBuilder;
import seedu.ddd.model.contact.vendor.Vendor;

/**
 * Tests that a {@code Vendor}'s {@code Service} matches any of the keywords given.
 */
public class ServiceContainsKeywordsPredicate implements Predicate<Vendor> {
    private final List<String> keywords;

    public ServiceContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vendor vendor) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(vendor.getService().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ServiceContainsKeywordsPredicate)) {
            return false;
        }

        ServiceContainsKeywordsPredicate otherServiceContainsKeywordsPredicate =
                (ServiceContainsKeywordsPredicate) other;
        return keywords.equals(otherServiceContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
