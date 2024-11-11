package seedu.address.model.person;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Email} matches any of the keywords given.
 */
public class EmailContainsKeywordsPredicate extends ContainsKeywordsPredicate {

    /**
     * Constructs an {@code EmailContainsKeywordsPredicate} with the specified keywords.
     *
     * @param keywords List of keywords to match against a person's email.
     */
    public EmailContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any of the keywords match the email address of the given person.
     *
     * @param person the {@code Person} to test.
     * @return true if any keyword matches the person's email address, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        return super.getKeywords().stream()
                .anyMatch(keyword -> person.getEmail().value.toLowerCase().contains(keyword.toLowerCase()));
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

        // Check if the other object is an instance of EmailContainsKeywordsPredicate
        if (!(other instanceof EmailContainsKeywordsPredicate)) {
            return false;
        }

        EmailContainsKeywordsPredicate otherPredicate = (EmailContainsKeywordsPredicate) other;
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
