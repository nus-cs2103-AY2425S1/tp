package seedu.address.model.person.predicates;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * A predicate that tests if a given person is present in a list of persons.
 */
public class IsPersonInListPredicate implements Predicate<Person> {

    private final List<Person> persons;

    /**
     * Constructs a {@code IsPersonInListPredicate} with the specified list of persons.
     *
     * @param persons The list of persons to match against.
     */
    public IsPersonInListPredicate(List<Person> persons) {
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
