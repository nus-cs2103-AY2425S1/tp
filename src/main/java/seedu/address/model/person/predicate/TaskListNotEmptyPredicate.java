package seedu.address.model.person.predicate;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person}'s {@code Level} matches any of the keywords given.
 */
public class TaskListNotEmptyPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getTaskList().size() > 0;
    }

}
