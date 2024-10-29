package seedu.address.logic.parser;

import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagSearchCriteria implements SearchCriteria {

    private final List<String> tags;

    public TagSearchCriteria(List<String> tags) {
        this.tags = tags;
    }
    @Override
    public boolean test(Person person) {
        return tags.stream().anyMatch(tag -> person.hasTag(new Tag(tag)));
    }

    @Override
    public String toString() {
        return "AgeCriteria{ages=" + tags + "}";
    }
}
