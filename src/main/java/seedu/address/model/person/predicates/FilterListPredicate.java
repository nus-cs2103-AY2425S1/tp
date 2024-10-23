package seedu.address.model.person.predicates;

import seedu.address.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

/**
 * A predicate that tests if a given person is present in a list of persons.
 */
public class FilterListPredicate implements Predicate<Person> {

    private final List<Person> persons;

    /**
     * Constructs a {@code FilterListPredicate} with the specified list of persons.
     *
     * @param persons The list of persons to match against.
     */
    public FilterListPredicate(List<Person> persons) {
        this.persons = persons;
    }

    /**
     * Tests if the specified person is present in the list.
     *
     * @param person The person to test.
     * @return {@code true} if the person is in the list, otherwise {@code false}.
     */
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
