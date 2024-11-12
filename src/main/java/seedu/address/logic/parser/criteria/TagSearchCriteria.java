package seedu.address.logic.parser.criteria;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagSearchCriteria implements SearchCriteria {

    private final Set<Tag> tags;

    public TagSearchCriteria(List<String> tags) {
        this.tags = tags.stream().map(Tag::new).collect(Collectors.toSet());
    }
    @Override
    public boolean test(Person person) {
        return tags.stream().anyMatch(person::hasTag);
    }

    @Override
    public String toString() {
        return "AgeCriteria{ages=" + tags + "}";
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof TagSearchCriteria)) {
            return false;
        }

        TagSearchCriteria otherTagSearchCriteria = (TagSearchCriteria) other;
        return tags.equals(otherTagSearchCriteria.tags);
    }
}
