package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
        SortCommand sortByNameCommand = new SortCommand("name");
        SortCommand sortByDeadlineCommand = new SortCommand("deadline");

        // same object -> returns true
        assertTrue(sortByNameCommand.equals(sortByNameCommand));

        // same values -> returns true
        SortCommand sortByNameCommandCopy = new SortCommand("name");
        assertTrue(sortByNameCommand.equals(sortByNameCommandCopy));

        // different types -> returns false
        assertFalse(sortByNameCommand.equals(1));

        // null -> returns false
        assertFalse(sortByNameCommand.equals(null));

        // different sort field -> returns false
        assertFalse(sortByNameCommand.equals(sortByDeadlineCommand));
    }

    @Test
    public void execute_sortByName_sortsListByName() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        SortCommand command = new SortCommand("name");

        // Sort the list by name in both the model and the expected model
        expectedModel.sortByComparator((person1, person2) -> person1
                .getName()
                .fullName.compareToIgnoreCase(person2.getName().fullName));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the persons are sorted by name
        assertTrue(model.getFilteredPersonList().get(0).equals(ALICE)); // ALICE should be first alphabetically
        assertTrue(model.getFilteredPersonList().get(1).equals(BENSON)); // BENSON should be second alphabetically
        assertTrue(model.getFilteredPersonList().get(6).equals(GEORGE)); // GEORGE should be last alphabetically
    }

    @Test
    public void execute_sortByDeadline_sortsListByDeadline() {
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "deadline");
        SortCommand command = new SortCommand("deadline");

        // Sort the list by deadline in both the model and the expected model
        expectedModel.sortByComparator((person1, person2) -> person1
                .getDeadline()
                .value.compareTo(person2.getDeadline().value));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the persons are sorted by deadline
        assertTrue(model.getFilteredPersonList().get(0).equals(GEORGE)); // GEORGE has the earliest deadline
        assertTrue(model.getFilteredPersonList().get(1).equals(FIONA)); // FIONA should be second
        assertTrue(model.getFilteredPersonList().get(6).equals(DANIEL)); // DANIEL should be last
    }

    @Test
    public void execute_invalidSortField_throwsCommandException() {
        String invalidField = "invalidField";
        SortCommand command = new SortCommand(invalidField);

        String expectedMessage = SortCommand.MESSAGE_INVALID_KEYWORD;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_alreadySortedList_sortsListAgainByName() {
        // First, sort the list by name
        SortCommand command = new SortCommand("name");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");
        expectedModel.sortByComparator((person1, person2) -> person1.getName()
                .fullName
                .compareToIgnoreCase(person2.getName().fullName));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Execute the sort command again to ensure no issues with sorting an already sorted list
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertTrue(model.getFilteredPersonList().get(0).equals(ALICE));
    }

    @Test
    public void execute_emptyAddressBook_noPersonsAvailable() throws CommandException {
        Model model = new ModelManager();
        SortCommand command = new SortCommand("name");
        String expectedMessage = SortCommand.MESSAGE_NO_PERSONS;

        CommandResult result = command.execute(model);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertTrue(model.getAddressBook().getPersonList().isEmpty());
    }


    @Test
    public void execute_sortAfterFilter_sortsFilteredList() {
        model.updateFilteredPersonList(person -> person.getName().fullName.contains("Meier"));
        expectedModel.updateFilteredPersonList(person -> person.getName().fullName.contains("Meier"));

        SortCommand command = new SortCommand("name");
        String expectedMessage = String.format(SortCommand.MESSAGE_SUCCESS, "name");

        expectedModel.sortByComparator((person1, person2) -> person1.getName()
                .fullName
                .compareToIgnoreCase(person2.getName().fullName));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Ensure the filtered persons are sorted by name
        assertTrue(model.getFilteredPersonList().get(0).equals(BENSON));
        assertTrue(model.getFilteredPersonList().get(1).equals(DANIEL));
    }
}
