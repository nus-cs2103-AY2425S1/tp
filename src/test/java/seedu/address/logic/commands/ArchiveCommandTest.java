package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SECOND_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LIST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validMultipleIndexUnfilteredList_success() {
        Person personToArchive1 = model.getFilteredPersonList().get(INDEX_FIRST.getZeroBased());
        Person personToArchive2 = model.getFilteredPersonList().get(INDEX_SECOND.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_SECOND_LIST);
        List<Person> personToArchiveList = new ArrayList<>();
        personToArchiveList.add(personToArchive1);
        personToArchiveList.add(personToArchive2);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVED_PERSON_SUCCESS,
                Messages.formatPersonList(personToArchiveList));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToArchive1);
        expectedModel.deletePerson(personToArchive2);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
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
