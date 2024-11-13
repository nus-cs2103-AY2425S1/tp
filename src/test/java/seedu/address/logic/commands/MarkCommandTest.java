package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.ContactRecordBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkCommand}.
 */
public class MarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMark = model.getSortedFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, validRecord);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS,
                Messages.format(personToMark),
                validRecord);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.markAsContacted(personToMark, validRecord);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedFilteredPersonList().size() + 1);
        ContactRecord validRecord = new ContactRecordBuilder().build();

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex, validRecord);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getSortedFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ContactRecord validRecord = new ContactRecordBuilder().build();

        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, validRecord);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS,
                Messages.format(personToMark),
                validRecord);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.markAsContacted(personToMark, validRecord);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        ContactRecord validRecord = new ContactRecordBuilder().build();

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkCommand markCommand = new MarkCommand(outOfBoundIndex, validRecord);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equalsIndexCommandTest() {
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markFirstCommand = new MarkCommand(INDEX_FIRST_PERSON, validRecord);
        MarkCommand markSecondCommand = new MarkCommand(INDEX_SECOND_PERSON, validRecord);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkCommand markFirstCommandCopy = new MarkCommand(INDEX_FIRST_PERSON, validRecord);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // different date -> returns false
        ContactRecord validRecord2 = new ContactRecordBuilder().withNotes("different note").build();
        MarkCommand markFirstCommandDifferentDate = new MarkCommand(INDEX_FIRST_PERSON, validRecord2);
        assertFalse(markFirstCommand.equals(markFirstCommandDifferentDate));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    @Test
    public void equalsNricCommandTest() {
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markFirstNric = new MarkCommand(new Nric(VALID_NRIC_AMY), validRecord);
        MarkCommand markSecondNric = new MarkCommand(new Nric(VALID_NRIC_BOB), validRecord);

        // same object -> returns true
        assertTrue(markFirstNric.equals(markFirstNric));

        // same values -> returns true
        MarkCommand markFirstNricCopy = new MarkCommand(new Nric(VALID_NRIC_AMY), validRecord);
        assertTrue(markFirstNric.equals(markFirstNricCopy));

        // different date -> returns false
        ContactRecord validRecord2 = new ContactRecordBuilder().withNotes("different note").build();
        MarkCommand markFirstNricDifferentDate = new MarkCommand(new Nric(VALID_NRIC_AMY), validRecord2);
        assertFalse(markFirstNric.equals(markFirstNricDifferentDate));

        // different types -> returns false
        assertFalse(markFirstNric.equals(1));

        // null -> returns false
        assertFalse(markFirstNric.equals(null));

        // different person -> returns false
        assertFalse(markFirstNric.equals(markSecondNric));
    }

    @Test
    public void toStringMethod() {
        // Test for Index
        Index targetIndex = Index.fromOneBased(1);
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markCommandWithIndex = new MarkCommand(targetIndex, validRecord);
        String expectedIndexString = MarkCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex
                + ", targetNric=null}";
        assertEquals(expectedIndexString, markCommandWithIndex.toString());

        // Test for NRIC
        Nric targetNric = new Nric(VALID_NRIC_AMY);
        MarkCommand markCommandWithNric = new MarkCommand(targetNric, validRecord);
        String expectedNricString = MarkCommand.class.getCanonicalName() + "{targetIndex=null, targetNric="
                + targetNric + "}";
        assertEquals(expectedNricString, markCommandWithNric.toString());
    }

    @Test
    public void execute_validNric_success() {
        Person personToMark = model.getSortedFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markCommand = new MarkCommand(personToMark.getNric(), validRecord);

        String expectedMessage = String.format(MarkCommand.MESSAGE_MARK_PERSON_SUCCESS,
                Messages.format(personToMark),
                validRecord);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.markAsContacted(personToMark, validRecord);

        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        Nric unregisteredNric = new Nric("S5419807H");
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markCommand = new MarkCommand(unregisteredNric, validRecord);

        assertCommandFailure(markCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_NRIC);
    }

    @Test
    public void restrictedUsageInHistoryView() {
        model.setHistoryView(true);
        ContactRecord validRecord = new ContactRecordBuilder().build();
        MarkCommand markCommand = new MarkCommand(INDEX_FIRST_PERSON, validRecord);
        assertCommandFailure(markCommand, model, Messages.MESSAGE_USAGE_RESTRICTED_IN_HISTORY_VIEW);
    }
}
