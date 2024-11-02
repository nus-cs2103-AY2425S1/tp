package seedu.eventtory.model.vendor;

import java.util.List;
import java.util.function.Predicate;

import seedu.eventtory.commons.util.StringUtil;
import seedu.eventtory.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Vendor}'s {@code Name}, {@code Description}, {@code Phone} or {@code Tag}
 * matches any of the keywords given.
 */
public class VendorContainsKeywordsPredicate implements Predicate<Vendor> {
    private final List<String> keywords;

    public VendorContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Vendor vendor) {
        return keywords.stream().anyMatch(keyword ->
                StringUtil.containsPartialWordIgnoreCase(vendor.getName().fullName, keyword)
                || StringUtil.containsPartialWordIgnoreCase(vendor.getDescription().toString(), keyword)
                || StringUtil.containsPartialWordIgnoreCase(vendor.getPhone().toString(), keyword)
                || StringUtil.containsPartialWordIgnoreCase(vendor.getTags().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VendorContainsKeywordsPredicate)) {
            return false;
        }

        VendorContainsKeywordsPredicate otherVendorContainsKeywordsPredicate = (VendorContainsKeywordsPredicate) other;
        return keywords.equals(otherVendorContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
