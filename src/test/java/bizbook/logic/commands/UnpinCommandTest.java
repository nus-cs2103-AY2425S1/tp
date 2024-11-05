package bizbook.logic.commands;

import static bizbook.logic.commands.CommandTestUtil.assertCommandFailure;
import static bizbook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static bizbook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static bizbook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static bizbook.testutil.TypicalPersons.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bizbook.commons.core.index.Index;
import bizbook.logic.Messages;
import bizbook.model.Model;
import bizbook.model.ModelManager;
import bizbook.model.UserPrefs;
import bizbook.model.person.Person;

public class UnpinCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Person pinnedPerson = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        model.pinPerson(pinnedPerson);
    }

    @Test
    public void execute_validIndexPinnedList_success() {
        Person personToUnpin = model.getPinnedPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(UnpinCommand.MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.formatShort(personToUnpin));

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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

        // same object -> returns true
        assertTrue(unpinFirstCommand.equals(unpinFirstCommand));

        // same values -> returns true
        UnpinCommand duplicateUnpinFirstCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        assertTrue(unpinFirstCommand.equals(duplicateUnpinFirstCommand));

        // null -> returns false
        assertFalse(unpinFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unpinFirstCommand.equals(unpinSecondCommand));
    }

    @Test
    public void toStringMethod() {
        UnpinCommand unpinCommand = new UnpinCommand(INDEX_FIRST_PERSON);
        String expected = UnpinCommand.class.getCanonicalName() + "{targetIndex=" + INDEX_FIRST_PERSON + "}";
        assertEquals(expected, unpinCommand.toString());
    }
}
