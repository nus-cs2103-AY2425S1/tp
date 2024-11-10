package seedu.address.model.person;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate extends ContainsKeywordsPredicate {

    /**
     * Constructs a {@code PhoneContainsKeywordsPredicate} with the specified keywords.
     *
     * @param keywords List of keywords to match against a person's phone number.
     */
    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any of the keywords match the phone number of the given person.
     *
     * @param person the {@code Person} to test.
     * @return true if any keyword matches the person's phone number, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        return getKeywords().stream()
                .anyMatch(keyword -> person.getPhone().value.toLowerCase().contains(keyword.toLowerCase()));
    }

    /**
     * Checks if this predicate is equal to another object.
     *
     * @param other the object to compare with this predicate.
     * @return true if the other object is the same type and has the same keywords, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; // Check for reference equality
        }

        // Check if the other object is an instance of PhoneContainsKeywordsPredicate
        if (!(other instanceof PhoneContainsKeywordsPredicate)) {
            return false;
        }

        PhoneContainsKeywordsPredicate otherPredicate = (PhoneContainsKeywordsPredicate) other;
        return getKeywords().equals(otherPredicate.getKeywords());
    }

    /**
     * Returns a string representation of this predicate.
     *
     * @return string representation of the object's state.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", getKeywords())
                .toString();
    }
}
