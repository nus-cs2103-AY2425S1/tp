package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_LEVEL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for PriorityCommand.
 */
public class PriorityCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addValidPriorityLevelUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPriorityLevel(VALID_PRIORITY_LEVEL).build();

        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), VALID_PRIORITY_LEVEL,
                false);

        String expectedMessage = String.format("Priority level %d successfully set for %s",
                VALID_PRIORITY_LEVEL, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientId_throwsCommandException() {
        // Using an out-of-bounds index
        PriorityCommand priorityCommand = new PriorityCommand(model.getFilteredPersonList().size() + 1,
                VALID_PRIORITY_LEVEL, false);

        assertThrows(CommandException.class, () -> priorityCommand.execute(model));
    }

    @Test
    public void execute_negativePatientId_throwsCommandException() {
        // Using a negative index
        PriorityCommand priorityCommand = new PriorityCommand(-1, VALID_PRIORITY_LEVEL, false);

        assertThrows(CommandException.class, () -> priorityCommand.execute(model));
    }

    @Test
    public void execute_invalidPriorityLevel_throwsCommandException() {
        // Create a command with an invalid priority level (e.g., 4)
        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 4,
                false);

        assertThrows(CommandException.class, () -> priorityCommand.execute(model));
    }

    @Test
    public void equals() {
        final PriorityCommand standardCommand = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 1, false);

        // same values -> returns true
        PriorityCommand commandWithSameValues = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 1, false);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        PriorityCommand commandWithDifferentIndex =
                new PriorityCommand(INDEX_SECOND_PERSON.getOneBased(), 1, false);
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

        // different priorityLevel -> returns false
        PriorityCommand commandWithDifferentPriorityLevel =
                new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 2, false);
        assertFalse(standardCommand.equals(commandWithDifferentPriorityLevel));

        // different reset flag -> returns false
        PriorityCommand commandWithResetFlag = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 1, true);
        assertFalse(standardCommand.equals(commandWithResetFlag));
    }

    @Test
    public void execute_resetPriorityLevelUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withPriorityLevel(3).build();

        PriorityCommand priorityCommand = new PriorityCommand(INDEX_FIRST_PERSON.getOneBased(), 3, true);

        String expectedMessage = String.format(PriorityCommand.MESSAGE_SUCCESS_RESET, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(priorityCommand, model, expectedMessage, expectedModel);
    }

}
