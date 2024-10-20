package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContactContainsKeywordPredicate implements Predicate<Person> {
    private final List<String> keywords;

    /**
     * Constructs a TagContactContainsKeywordsPredicate to filter tags based on the provided keywords.
     *
     * @param keywords the list of keywords to match against tags
     */
    public TagContactContainsKeywordPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getTags().stream()
                        .anyMatch(tag -> StringUtil.beginsWordIgnoreCase(tag.tagName, keyword)));
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContactContainsKeywordPredicate)) {
            return false;
        }

        TagContactContainsKeywordPredicate otherTagContactContainsKeywordsPredicate =
                (TagContactContainsKeywordPredicate) other;
        return keywords.equals(otherTagContactContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
