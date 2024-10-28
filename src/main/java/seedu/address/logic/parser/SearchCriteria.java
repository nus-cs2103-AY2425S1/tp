package seedu.address.logic.parser;

import seedu.address.model.person.Person;

@FunctionalInterface
public interface SearchCriteria {
    public boolean test(Person person);
}
