package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tags} match any of the keywords given.
 */
public class PersonContainsTagsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a new predicate with the given keywords.
     *
     * @param keywords the list of keywords to match against person tags
     * @throws AssertionError if keywords is null or empty
     */
    public PersonContainsTagsPredicate(List<String> keywords) {
        assert keywords != null;
        assert !keywords.isEmpty();
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags(); // Retrieves the set of tags associated with the person
        return keywords.stream()
                .anyMatch(keyword -> tags.stream()
                        .anyMatch(tag -> StringUtil.containsWordIgnoreCase(tag.tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonContainsTagsPredicate)) {
            return false;
        }

        PersonContainsTagsPredicate otherPredicate = (PersonContainsTagsPredicate) other;
        return keywords.equals(otherPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("tags", keywords).toString();
    }
}