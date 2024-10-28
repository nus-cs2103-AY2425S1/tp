package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        SortCommand sortByNameAscCommand = new SortCommand("name", true);
        SortCommand sortByDeadlineDescCommand = new SortCommand("deadline", false);

        // same object -> returns true
        assertTrue(sortByNameAscCommand.equals(sortByNameAscCommand));

        // same values -> returns true
        SortCommand sortByNameAscCommandCopy = new SortCommand("name", true);
        assertTrue(sortByNameAscCommand.equals(sortByNameAscCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameAscCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameAscCommand.equals(null));

        // different sort field or order -> returns false
        assertFalse(sortByNameAscCommand.equals(sortByDeadlineDescCommand));
    }

    @Test
    public void execute_sortByNameAscending_sortsListByNameAscending() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");
        SortCommand command = new SortCommand("name", true);

        // Sort the list by name in ascending order in both the model and the expected model
        expectedModel.sortByComparator(Comparator.comparing(person -> person.getName().fullName.toLowerCase()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the persons are sorted by name in ascending order
        assertTrue(model.getFilteredPersonList().get(0).equals(ALICE));
        assertTrue(model.getFilteredPersonList().get(1).equals(BENSON));
        assertTrue(model.getFilteredPersonList().get(6).equals(GEORGE));
    }

    @Test
    public void execute_sortByDeadlineDescending_sortsListByDeadlineDescending() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "deadline", "descending");
        SortCommand command = new SortCommand("deadline", false);

        // Sort the list by deadline in descending order in both the model and the expected model
        expectedModel.sortByComparator((person1, person2) ->
                person2.getDeadline().value.compareTo(person1.getDeadline().value));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the persons are sorted by deadline in descending order
        assertTrue(model.getFilteredPersonList().get(0).equals(DANIEL)); // DANIEL has the latest deadline
        assertTrue(model.getFilteredPersonList().get(6).equals(GEORGE)); // GEORGE has the earliest deadline
    }

    @Test
    public void execute_invalidSortField_throwsCommandException() {
        String invalidField = "invalidField";
        SortCommand command = new SortCommand(invalidField, true);

        String expectedMessage = SortCommand.MESSAGE_INVALID_KEYWORD;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_emptyAddressBook_noPersonsAvailable() throws CommandException {
        Model model = new ModelManager();
        SortCommand command = new SortCommand("name", true);
        String expectedMessage = SortCommand.MESSAGE_NO_PERSONS;

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }

    @Test
    public void execute_sortAfterFilter_sortsFilteredList() {
        model.updateFilteredPersonList(person -> person.getName().fullName.contains("Meier"));
        expectedModel.updateFilteredPersonList(person -> person.getName().fullName.contains("Meier"));

        SortCommand command = new SortCommand("name", true);
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name", "ascending");

        expectedModel.sortByComparator(Comparator.comparing(person -> person.getName().fullName.toLowerCase()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the filtered persons are sorted by name
        assertTrue(model.getFilteredPersonList().get(0).equals(BENSON));
        assertTrue(model.getFilteredPersonList().get(1).equals(DANIEL));
    }
}
