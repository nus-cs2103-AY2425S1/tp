package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code CheckClientCommand}.
 */
public class CheckClientCommandTest {

    private static final CommandResult expectedNoCarToCheckMessage = new CommandResult(
            CheckClientCommand.MESSAGE_NO_CAR_TO_CHECK);

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Helper class to execute and test a CheckClientCommand given a client.
     * @param clientToCheck The client to Check in/ out.
     * @param index The index of the client to Check in/ out.
     */
    public void checkClientTestHelper(Person clientToCheck, Index index) {
        CheckClientCommand checkClientCommand = new CheckClientCommand(index);
        try {
            if (clientToCheck.getCar() == null) {
                assertEquals(expectedNoCarToCheckMessage, checkClientCommand.execute(model));
                return;
            }
            CommandResult expectedCheckInResult = new CommandResult(
                    String.format(CheckClientCommand.MESSAGE_CHECK_IN_CLIENT_SUCCESS,
                    clientToCheck.getName(), clientToCheck.getVrn()));
            CommandResult expectedCheckOutResult = new CommandResult(
                    String.format(CheckClientCommand.MESSAGE_CHECK_OUT_CLIENT_SUCCESS,
                    clientToCheck.getName(), clientToCheck.getVrn()));

            clientToCheck.setServicing();
            if (clientToCheck.isServicing()) {
                assertEquals(expectedCheckInResult, checkClientCommand.execute(model));
                return;
            }
            assertEquals(expectedCheckOutResult, checkClientCommand.execute(model));
        } catch (CommandException e) {
            fail();
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person clientToCheck = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        checkClientTestHelper(clientToCheck, INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        CheckClientCommand checkClientCommand = new CheckClientCommand(outOfBoundIndex);

        assertCommandFailure(checkClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person clientToCheck = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        checkClientTestHelper(clientToCheck, INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        CheckClientCommand checkClientCommand = new CheckClientCommand(outOfBoundIndex);

        assertCommandFailure(checkClientCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        CheckClientCommand checkFirstCommand = new CheckClientCommand(INDEX_FIRST_PERSON);
        CheckClientCommand checkSecondCommand = new CheckClientCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(checkFirstCommand.equals(checkFirstCommand));

        // same values -> returns true
        CheckClientCommand checkFirstCommandCopy = new CheckClientCommand(INDEX_FIRST_PERSON);
        assertTrue(checkFirstCommand.equals(checkFirstCommandCopy));

        // different types -> returns false
        assertFalse(checkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(checkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(checkFirstCommand.equals(checkSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        CheckClientCommand checkClientCommand = new CheckClientCommand(targetIndex);
        String expected = CheckClientCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, checkClientCommand.toString());
    }
}
