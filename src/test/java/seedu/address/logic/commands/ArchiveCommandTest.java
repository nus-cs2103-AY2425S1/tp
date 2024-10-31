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
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_throwsCommandException() {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_LIST);

        assertCommandFailure(archiveCommand, model, ArchiveCommand.MESSAGE_INVALID_WINDOW);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> outOfBoundIndexList = new ArrayList<>();
        outOfBoundIndexList.add(outOfBoundIndex);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndexList);

        assertCommandFailure(archiveCommand, model, ArchiveCommand.MESSAGE_INVALID_WINDOW);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_LIST);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_LIST);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_LIST);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        List<Index> targetIndexList = new ArrayList<>();
        targetIndexList.add(targetIndex);
        ArchiveCommand archiveCommand = new ArchiveCommand(targetIndexList);
        String expected = ArchiveCommand.class.getCanonicalName() + "{targetIndex=" + targetIndexList + "}";
        assertEquals(expected, archiveCommand.toString());
    }
}
