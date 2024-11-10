package seedu.address.model.person;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate extends ContainsKeywordsPredicate {

    /**
     * Constructs a {@code NameContainsKeywordsPredicate} with the specified keywords.
     *
     * @param keywords List of keywords to match against a person's name.
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        super(keywords);
    }

    /**
     * Tests if any of the keywords match the name of the given person.
     *
     * @param person the {@code Person} whose name is to be tested.
     * @return true if any keyword matches the person's name, false otherwise.
     */
    @Override
    public boolean test(Person person) {
        return getKeywords().stream()
                .anyMatch(keyword -> person.getName().fullName.toLowerCase().contains(keyword.toLowerCase()));
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

        // Check if the other object is an instance of NameContainsKeywordsPredicate
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameContainsKeywordsPredicate otherPredicate = (NameContainsKeywordsPredicate) other;
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
