package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DUPLICATED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LIST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LIST;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validSingleIndexUnfilteredPersonList_success() {
        Index lastIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        List<Index> lastIndexList = new ArrayList<>();
        lastIndexList.add(lastIndex);

        Person archivedPerson = new PersonBuilder().withArchive("true").build();

        Person unarchivedPerson = new PersonBuilder().build();

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(lastIndexList);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVED_PERSON_SUCCESS,
                "\n" + Messages.format(archivedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addPerson(unarchivedPerson);

        AddressBookParser.setInspect(false);
        model.addPerson(archivedPerson);
        assertCommandSuccess(unarchiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_outOfBoundIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        List<Index> outOfBoundIndexList = new ArrayList<>();
        outOfBoundIndexList.add(outOfBoundIndex);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndexList);
        String expectedErrorMessage = String.format(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                outOfBoundIndex.getOneBased());

        assertCommandFailure(unarchiveCommand, model, expectedErrorMessage);
    }

    @Test
    public void execute_duplicateIndexUnfilteredList_throwsCommandException() {
        List<Index> duplicatedList = new ArrayList<>();
        duplicatedList.add(INDEX_FIRST);
        duplicatedList.add(INDEX_FIRST);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(duplicatedList);

        String expectedErrorMessage = String.format(MESSAGE_INVALID_DUPLICATED_INDEX,
                INDEX_FIRST.getOneBased());

        assertCommandFailure(unarchiveCommand, model, expectedErrorMessage);
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
