package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import tuteez.commons.util.StringUtil;
import tuteez.commons.util.ToStringBuilder;
import tuteez.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Phone} matches any of the keywords given.
 */
public class PhoneContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public PhoneContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getPhone().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof tuteez.model.person.predicates.PhoneContainsKeywordsPredicate)) {
            return false;
        }

        tuteez.model.person.predicates.PhoneContainsKeywordsPredicate otherPhoneContainsKeywordsPredicate =
                (tuteez.model.person.predicates.PhoneContainsKeywordsPredicate) other;
        return keywords.equals(otherPhoneContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
