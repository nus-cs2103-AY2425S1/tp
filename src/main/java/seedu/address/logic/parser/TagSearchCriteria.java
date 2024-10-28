package seedu.address.logic.parser;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

import java.util.List;

public class TagSearchCriteria implements SearchCriteria {

    private final List<String> keywords;

    public TagSearchCriteria(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(tag -> person.hasTag(new Tag(tag)));
    }
}
