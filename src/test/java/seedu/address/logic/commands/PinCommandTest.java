package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
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
 * Contains integration tests (interaction with the Model) for {@code PinCommand}.
 */
public class PinCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToPin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(PinCommand.MESSAGE_PIN_PERSON_SUCCESS,
                Messages.formatShort(personToPin));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPinnedPersonList(personToPin);

        assertCommandSuccess(pinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        PinCommand pinCommand = new PinCommand(outOfBoundIndex);

        assertCommandFailure(pinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);
        ModelManager filledModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        filledModel.addPinnedPersonList(validPerson);

        assertThrows(CommandException.class,
                PinCommand.MESSAGE_ALREADY_PINNED, () -> pinCommand.execute(filledModel));
    }


    @Test
    public void equals() {
        PinCommand pinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);
        PinCommand pinSecondCommand = new PinCommand(INDEX_SECOND_PERSON);
        PinCommand duplicatepinFirstCommand = new PinCommand(INDEX_FIRST_PERSON);

        assertTrue(pinFirstCommand.equals(pinFirstCommand));
        assertTrue(pinFirstCommand.equals(duplicatepinFirstCommand));

        assertFalse(pinFirstCommand.equals(null));
        assertFalse(pinFirstCommand.equals(pinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        PinCommand pinCommand = new PinCommand(INDEX_FIRST_PERSON);
        String expected = PinCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, pinCommand.toString());
    }

}
