package seedu.address.logic.parser;

import seedu.address.model.person.Person;

import java.util.List;

public class PhoneSearchCriteria implements SearchCriteria {

    private final List<String> phones;
    @Override
    public boolean test(Person person) {
        return false;
    }
}
