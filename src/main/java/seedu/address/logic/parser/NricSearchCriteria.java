package seedu.address.logic.parser;

import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

import java.util.List;

public class NricSearchCriteria implements SearchCriteria {

    private final List<String> keywords;

    public NricSearchCriteria(List<String> keywords) {
        this.keywords = keywords;
    }
    @Override
    public boolean test(Person person) {
        return keywords.stream().anyMatch(keyword -> person.hasNric(new Nric(keyword)));
    }
}
