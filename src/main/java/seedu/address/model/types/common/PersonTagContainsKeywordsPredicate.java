package seedu.address.model.types.common;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.types.person.Person;

/**
 * Tests that a {@code Person}'s {@code Tag Name} matches any of the keywords given.
 */
public class PersonTagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.getTagName(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonTagContainsKeywordsPredicate)) {
            return false;
        }

        PersonTagContainsKeywordsPredicate otherTagContainsKeywordsPredicate =
                (PersonTagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
