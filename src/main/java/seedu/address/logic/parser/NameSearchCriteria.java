package seedu.address.logic.parser;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.List;

public class NameSearchCriteria implements SearchCriteria {

    private final List<String> keywords;

    public NameSearchCriteria(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> person.hasName(new Name(keyword)));
    }
}
