package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ARCHIVED_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_UNARCHIVED_PERSONS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getArchivedAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

public class UnarchiveCommandTest {

    private Model model = new ModelManager(getArchivedAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ARCHIVED_PERSONS);
        Person personToUnarchive = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        UnarchiveCommand archiveCommand = new UnarchiveCommand(List.of(INDEX_FIRST_PERSON));

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS,
                Messages.format(personToUnarchive));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ARCHIVED_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_ARCHIVED_PERSONS);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);

        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_UNARCHIVED_PERSONS);
        model.updateFilteredPersonList(PREDICATE_SHOW_UNARCHIVED_PERSONS);

        assertCommandSuccess(archiveCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(List.of(INDEX_FIRST_PERSON));
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(List.of(INDEX_SECOND_PERSON));

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(List.of(INDEX_FIRST_PERSON));
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }
}
