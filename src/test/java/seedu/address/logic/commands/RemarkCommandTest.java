package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {
    private static final String REMARK_STUB = "Some remark";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Remark remark = new Remark(REMARK_STUB);
        RemarkCommand stubCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        // same value -> return true
        assertTrue(stubCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, remark)));
        // same object -> return true
        assertTrue(stubCommand.equals(stubCommand));
        // null -> return false
        assertFalse(stubCommand.equals(null));
        // different command type -> return false
        assertFalse(stubCommand.equals(new ClearCommand()));
        // different index -> return false
        assertFalse(stubCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, remark)));
        // different remark value -> return false
        assertFalse(stubCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("different remark"))));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Remark remark = new Remark(REMARK_STUB);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, remark);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(REMARK_STUB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void generateSuccessCommand_emptyRemark_success() {
        Person person = new PersonBuilder().build();
        Index validIndex = INDEX_FIRST_PERSON;
        RemarkCommand emptyRemarkCommand = new RemarkCommand(validIndex, Remark.EMPTY_REMARK);
        String expected = RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS.replace("%1$s", "")
                + Messages.format(person);
        assertEquals(expected, emptyRemarkCommand.generateSuccessMessage(person));
    }

    @Test
    public void generateSuccessCommand_validRemark_success() {
        Person person = new PersonBuilder().build();
        Index validIndex = INDEX_FIRST_PERSON;
        RemarkCommand validRemarkCommand = new RemarkCommand(validIndex, new Remark(REMARK_STUB));
        String expected = RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS.replace("%1$s", "")
                + Messages.format(person);
        assertEquals(expected, validRemarkCommand.generateSuccessMessage(person));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(REMARK_STUB));
        String expected = RemarkCommand.class.getCanonicalName() + "{index=" + index
                + ", remark=" + REMARK_STUB + "}";
        assertEquals(expected, remarkCommand.toString());
    }
}
