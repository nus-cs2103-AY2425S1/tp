package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class FindPersonCommandTest {

    @Test
    public void equals() {
        Predicate<Person> firstPredicate = person -> person.getName().equals("Alice");
        Predicate<Person> secondPredicate = person -> person.getName().equals("Bob");

        FindPersonCommand findFirstCommand = new FindPersonCommand(firstPredicate);
        FindPersonCommand findSecondCommand = new FindPersonCommand(secondPredicate);

        // same object -> returns true
        assertEquals(findFirstCommand, findFirstCommand);

        // same values -> returns true
        FindPersonCommand findFirstCommandCopy = new FindPersonCommand(firstPredicate);
        assertEquals(findFirstCommand, findFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, findFirstCommand);

        // null -> returns false
        assertNotEquals(null, findFirstCommand);

        // different person -> returns false
        assertNotEquals(findFirstCommand, findSecondCommand);
    }
}
