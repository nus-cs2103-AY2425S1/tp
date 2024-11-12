package tuteez.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import tuteez.commons.util.StringUtil;
import tuteez.model.person.Person;
import tuteez.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public TagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .map(Tag::getTagName)
                .anyMatch(tagName ->
                        keywords.stream().anyMatch(keyword -> StringUtil.containsWordIgnoreCase(tagName, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagContainsKeywordsPredicate)) {
            return false;
        }

        TagContainsKeywordsPredicate otherTagContainsKeywordsPredicate = (TagContainsKeywordsPredicate) other;
        return keywords.equals(otherTagContainsKeywordsPredicate.keywords);
    }
}
