package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnpinContactCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinContactCommand unpinContactCommand = new UnpinContactCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnpinContactCommand.MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.format(personToUnpin));

        expectedModel.unpinPerson(personToUnpin);
        model.pinPerson(personToUnpin); // Ensure the person is pinned

        assertCommandSuccess(unpinContactCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UnpinContactCommand unpinContactCommand = new UnpinContactCommand(outOfBoundIndex);

        assertCommandFailure(unpinContactCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_personAlreadyUnpinned_throwsCommandException() {
        Person personToUnpin = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        model.unpinPerson(personToUnpin); // Ensure the person is already unpinned
        UnpinContactCommand unpinContactCommand = new UnpinContactCommand(INDEX_FIRST_PERSON);

        assertCommandFailure(unpinContactCommand, model, UnpinContactCommand.MESSAGE_UNPIN_PERSON_ALREADY_UNPINNED);
    }

    @Test
    public void equals() {
        UnpinContactCommand unpinFirstCommand = new UnpinContactCommand(INDEX_FIRST_PERSON);
        UnpinContactCommand unpinSecondCommand = new UnpinContactCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinContactCommand unpinFirstCommandCopy = new UnpinContactCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(unpinFirstCommandCopy));

        // different types -> returns false
        assertFalse(unpinFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toString_validIndex_correctStringRepresentation() {
        UnpinContactCommand unpinFirstCommand = new UnpinContactCommand(INDEX_FIRST_PERSON);
        String expectedString =
                "seedu.address.logic.commands.UnpinContactCommand"
                        + "{targetIndex=seedu.address.commons.core.index.Index{zeroBasedIndex=0}}";
        assertEquals(expectedString, unpinFirstCommand.toString());
    }
}
