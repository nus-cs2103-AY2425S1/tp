package seedu.address.model.person;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class ContainsSpecificKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public ContainsSpecificKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        if (keywords.stream().anyMatch(keyword ->
                StringUtil.containsMultipleWordsIgnoreCase(person.getName().fullName, keyword))) {
            return true; // Returns true if names match
        } else if (keywords.stream().anyMatch(keyword ->
                StringUtil.containsMultipleWordsIgnoreCase(person.getAddress().value, keyword))) {
            return true; // Returns true if address match
        } else if (keywords.stream().anyMatch(keyword ->
                StringUtil.containsMultipleWordsIgnoreCase(person.getPhone().value, keyword))) {
            return true; // Returns true if phone number matches
        } else if (keywords.stream().anyMatch(keyword ->
                StringUtil.containsMultipleWordsIgnoreCase(person.getEmail().value, keyword))) {
            return true; // Returns true if email matches
        } else if (keywords.stream().anyMatch(keyword ->
                person.getTags().stream().anyMatch(tag -> StringUtil.containsMultipleWordsIgnoreCase(tag.tagName, keyword)))) {
            return true; // Returns true if tags match
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContainsSpecificKeywordsPredicate)) {
            return false;
        }

        ContainsSpecificKeywordsPredicate otherContainsKeywordsPredicate = (ContainsSpecificKeywordsPredicate) other;
        return keywords.equals(otherContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
