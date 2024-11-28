package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneNumberContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a PhoneNumberContainsKeywordsPredicate to filter phone numbers based on the provided keywords.
     *
     * @param keywords the list of keywords to match against phone numbers
     */
    public PhoneNumberContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.beginsWordIgnoreCase(person.getPhone().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneNumberContainsKeywordPredicate)) {
            return false;
        }

        PhoneNumberContainsKeywordPredicate otherPhoneNumberContainsKeywordsPredicate =
                (PhoneNumberContainsKeywordPredicate) other;
        return keywords.equals(otherPhoneNumberContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
