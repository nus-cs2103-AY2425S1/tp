package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name} contains the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final String name;

    public NameContainsKeywordsPredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(Person person) {
        return StringUtil.containsNameIgnoreCase(person.getName().fullName, name);
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

        NameContainsKeywordsPredicate otherNameContainsKeywordsPredicate = (NameContainsKeywordsPredicate) other;
        return name.equals(otherNameContainsKeywordsPredicate.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", name).toString();
    }
}
