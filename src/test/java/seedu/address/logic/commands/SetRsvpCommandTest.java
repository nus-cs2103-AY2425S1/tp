package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetRsvpCommand.
 */
public class SetRsvpCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Test for valid RSVP status "Coming".
     */
    @Test
    public void execute_setRsvpComing_success() {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        SetRsvpCommand setRsvpCommand = new SetRsvpCommand(INDEX_FIRST_PERSON, 1);

        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getEmail(), RsvpStatus.COMING, personToUpdate.getTags());

        String expectedMessage = String.format(SetRsvpCommand.MESSAGE_SET_SUCCESS
                + updatedPerson.getName().fullName + " (" + RsvpStatus.COMING + ")");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(setRsvpCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for valid RSVP status "Not Coming".
     */
    @Test
    public void execute_setRsvpNotComing_success() {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SetRsvpCommand setRsvpCommand = new SetRsvpCommand(INDEX_SECOND_PERSON, 2);

        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getEmail(), RsvpStatus.NOT_COMING, personToUpdate.getTags());

        String expectedMessage = String.format(SetRsvpCommand.MESSAGE_SET_SUCCESS
                + updatedPerson.getName().fullName + " (" + RsvpStatus.NOT_COMING + ")");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(setRsvpCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for valid RSVP status "Pending".
     */
    @Test
    public void execute_setRsvpPending_success() {
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SetRsvpCommand setRsvpCommand = new SetRsvpCommand(INDEX_SECOND_PERSON, 3);

        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getEmail(), RsvpStatus.PENDING, personToUpdate.getTags());

        String expectedMessage = String.format(SetRsvpCommand.MESSAGE_SET_SUCCESS
                + updatedPerson.getName().fullName + " (" + RsvpStatus.PENDING + ")");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(setRsvpCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Test for invalid index.
     */
    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SetRsvpCommand setRsvpCommand = new SetRsvpCommand(outOfBoundIndex, 3);

        // Correctly format the expected error message with the correct size
        String expectedMessage = String.format(SetRsvpCommand.MESSAGE_INVALID_INDEX + "%d)",
                model.getFilteredPersonList().size());

        assertCommandFailure(setRsvpCommand, model, expectedMessage);
    }


    /**
     * Test equals() method.
     */
    @Test
    public void equals() {
        SetRsvpCommand setRsvpFirstCommand = new SetRsvpCommand(INDEX_FIRST_PERSON, 1);
        SetRsvpCommand setRsvpSecondCommand = new SetRsvpCommand(INDEX_SECOND_PERSON, 2);

        // same object -> returns true
        assertTrue(setRsvpFirstCommand.equals(setRsvpFirstCommand));

        // different types -> returns false
        assertFalse(setRsvpFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setRsvpFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(setRsvpFirstCommand.equals(setRsvpSecondCommand));
    }

    @Test
    public void execute_undoSetRsvpCommand_success() {
        Model originalModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person personToUpdate = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        SetRsvpCommand setRsvpCommand = new SetRsvpCommand(INDEX_SECOND_PERSON, 3);

        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getEmail(), RsvpStatus.PENDING, personToUpdate.getTags());

        String expectedMessage = String.format(SetRsvpCommand.MESSAGE_SET_SUCCESS
                + updatedPerson.getName().fullName + " (" + RsvpStatus.PENDING + ")");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToUpdate, updatedPerson);

        assertCommandSuccess(setRsvpCommand, model, expectedMessage, expectedModel);

        model.updatePreviousCommand(setRsvpCommand);
        UndoCommand undoCommand = new UndoCommand();
        assertCommandSuccess(undoCommand, model, UndoCommand.MESSAGE_SUCCESS, originalModel);
    }
}
