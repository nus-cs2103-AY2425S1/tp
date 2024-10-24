package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LIST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_throwsCommandException() {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_LIST);

        assertCommandFailure(unarchiveCommand, model, UnarchiveCommand.MESSAGE_INVALID_WINDOW);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> outOfBoundIndexList = new ArrayList<>();
        outOfBoundIndexList.add(outOfBoundIndex);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndexList);

        assertCommandFailure(unarchiveCommand, model, UnarchiveCommand.MESSAGE_INVALID_WINDOW);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_LIST);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_LIST);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_LIST);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        List<Index> targetIndexList = new ArrayList<>();
        targetIndexList.add(targetIndex);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(targetIndexList);
        String expected = UnarchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndexList + "}";
        assertEquals(expected, unarchiveCommand.toString());
    }
}
