package seedu.address.model.person.predicates;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Details} matches any of the keywords given.
 */
public class DetailContainsKeywordsPredicate implements Predicate<Person> {
    private final Set<String> keywords;

    /**
     * Constructs a {@code DetailContainsKeywordsPredicate}.
     *
     * @param keywords A set of valid keywords.
     */
    public DetailContainsKeywordsPredicate(Set<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getDetail().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailContainsKeywordsPredicate)) {
            return false;
        }

        DetailContainsKeywordsPredicate otherDetailContainsKeywordsPredicate = (DetailContainsKeywordsPredicate) other;
        return keywords.equals(otherDetailContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
