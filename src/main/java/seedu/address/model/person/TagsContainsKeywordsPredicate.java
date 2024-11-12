package seedu.address.model.person;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;



/**
 * Tests that a {@code Person}'s {@code Tags} matches any of the keywords given.
 */
public class TagsContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    /**
     * This constructor takes a list of keywords and, for each keyword, checks if there is a corresponding
     * full tag name in {@code Tag.shortCutMap}. If a match is found, both the
     * original keyword and its mapped shortcut name are added to the {@code keywords} list.
     * If no match is found, the original keyword is added as is.
     *
     * @param keywords A list of keywords to be processed and matched.
     */
    public TagsContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        Set<Tag> tags = person.getTags();

        // Handling case where keywords are empty
        if (keywords.isEmpty()) {
            return false;
        }

        // Convert all keywords to lowercase
        List<String> lowerCaseKeywords = keywords.stream()
                .map(String::toLowerCase)
                .toList();

        // Stream through the tags and check if any tag matches a keyword (case-insensitive)
        return tags.stream().anyMatch(tag ->
                lowerCaseKeywords.stream()
                        .anyMatch(keyword -> tag.getTagName().toString().toLowerCase().contains(keyword))
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagsContainsKeywordsPredicate otherTagsContainsKeywordsPredicate)) {
            return false;
        }

        return keywords.equals(otherTagsContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
