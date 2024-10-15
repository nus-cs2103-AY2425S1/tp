package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneBeginsWithKeywordPredicate implements Predicate<Person> {
    private final String keyword;

    public PhoneBeginsWithKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        if (keyword.isEmpty()) {
            return false;
        }
        return person.getPhone().value.startsWith(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PhoneBeginsWithKeywordPredicate)) {
            return false;
        }

        PhoneBeginsWithKeywordPredicate otherPhoneBeginsWithKeywordPredicate = (PhoneBeginsWithKeywordPredicate) other;
        return keyword.equals(otherPhoneBeginsWithKeywordPredicate.keyword);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keyword", keyword).toString();
    }
}
