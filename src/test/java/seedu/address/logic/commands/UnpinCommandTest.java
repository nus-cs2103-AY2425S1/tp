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

public class UnpinCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person pinnedPerson = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        model.addPinnedPersonList(pinnedPerson);
    }

    @Test
    public void execute_validIndexPinnedList_success() {
        Person personToUnpin = model.getPinnedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.formatShort(personToUnpin));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(unpinCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexPinnedList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getPinnedPersonList().size() + 1);
        UnpinCommand unpinCommand = new UnpinCommand(outOfBoundIndex);

        assertCommandFailure(unpinCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        UnpinCommand unpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        UnpinCommand unpinSecondCommand = new UnpinCommand(INDEX_SECOND_PERSON);
        UnpinCommand duplicateUnpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));
        assertTrue(unpinFirstCommand.equals(duplicateUnpinFirstCommand));

        assertFalse(unpinFirstCommand.equals(null));
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        String expected = UnpinCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, unpinCommand.toString());
    }
}
