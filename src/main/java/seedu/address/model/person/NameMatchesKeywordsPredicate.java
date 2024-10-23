package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} strictly matches the keywords given.
 */
public class NameMatchesKeywordsPredicate implements Predicate<Person> {
    private final String keywords;

    public NameMatchesKeywordsPredicate(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.equalsIgnoreCase(person.getName().toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameContainsKeywordsPredicate)) {
            return false;
        }

        NameMatchesKeywordsPredicate otherNameMatchesKeywordsPredicate = (NameMatchesKeywordsPredicate) other;
        return keywords.equals(otherNameMatchesKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }

}
