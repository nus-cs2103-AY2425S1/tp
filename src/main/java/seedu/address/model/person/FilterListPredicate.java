package seedu.address.model.person;

import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

public class FilterListPredicate implements Predicate<Person> {

    private final List<Person> persons;

    public FilterListPredicate(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean test(Person person) {
        for (Person personToTest : persons) {
            if (personToTest.equals(person)) {
                return true;
            }
        }

        return false;
    }

}
