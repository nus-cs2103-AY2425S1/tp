package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonMeetsCriteriaPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        PersonMeetsCriteriaPredicate firstPredicate =
                new PersonMeetsCriteriaPredicate(
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("first"),
                    new HashSet<>()
                );
        PersonMeetsCriteriaPredicate secondPredicate =
                new PersonMeetsCriteriaPredicate(
                    Arrays.asList(),
                    Arrays.asList(),
                    Arrays.asList("second"),
                    new HashSet<>()
                );

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand filterFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(filterFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_emptyCriteria_allListed() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 7);
        PersonMeetsCriteriaPredicate predicate = preparePredicate(null);
        FilterCommand command = new FilterCommand(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_hasCriteria_multiplePersonsListed() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonMeetsCriteriaPredicate predicate = preparePredicate("street");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, DANIEL, GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        PersonMeetsCriteriaPredicate predicate = new PersonMeetsCriteriaPredicate(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList("address"),
                new HashSet<>()
            );
        FilterCommand filterCommand = new FilterCommand(predicate);
        String expected = FilterCommand.class.getCanonicalName()
            + "{predicate=" + predicate + "}";
        assertEquals(expected, filterCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private PersonMeetsCriteriaPredicate preparePredicate(String address) {
        return address == null
            ? new PersonMeetsCriteriaPredicate(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(),
                new HashSet<>()
            ) : new PersonMeetsCriteriaPredicate(
                Arrays.asList(),
                Arrays.asList(),
                Arrays.asList(address),
                new HashSet<>()
            );
    }
}
