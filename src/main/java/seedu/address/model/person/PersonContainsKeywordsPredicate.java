package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Name}, {@code Phone}, {@code Email}, {@code Address}, {@code Birthday}
 * or {@code Tags} match any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PersonContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsIgnoreCase(person.getName().fullName, keyword)
                                || StringUtil.containsIgnoreCase(person.getPhone().toString(), keyword)
                                || StringUtil.containsIgnoreCase(person.getEmail().toString(), keyword)
                                || StringUtil.containsIgnoreCase(person.getAddress().toString(), keyword)
                                || StringUtil.containsIgnoreCase(person.getBirthday().toString(), keyword)
                                || person.getTags().stream()
                                        .anyMatch(tag -> StringUtil.containsIgnoreCase(tag.toString(), keyword))
                );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsKeywordsPredicate)) {
            return false;
        }

        PersonContainsKeywordsPredicate otherPersonContainsKeywordsPredicate = (PersonContainsKeywordsPredicate) other;
        return keywords.equals(otherPersonContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
