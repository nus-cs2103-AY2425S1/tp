package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
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
    private static final Remark REMARK_STUB = new Remark("Some remark");
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB);

        // same values -> returns true
        assertTrue(remarkCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB)));

        // same object -> returns true
        assertTrue(remarkCommand.equals(remarkCommand));

        // null -> returns false
        assertFalse(remarkCommand.equals(null));

        // different types -> returns false
        assertFalse(remarkCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(remarkCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, REMARK_STUB)));

        // different remark -> returns false
        assertFalse(remarkCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Another remark"))));
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, REMARK_STUB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // Ensure outOfBoundIndex is within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, REMARK_STUB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validPersonIndexUnfilteredList_success() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withRemark(REMARK_STUB.value).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validPersonIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withRemark(REMARK_STUB.value).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void generateSuccessMessage_emptyRemark_success() {
        Person person = new PersonBuilder().build();
        RemarkCommand command = new RemarkCommand(INDEX_FIRST_PERSON, Remark.EMPTY_REMARK);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, Messages.format(person));
        assertEquals(expectedMessage, command.generateSuccessMessage(person));
    }

    @Test
    public void generateSuccessMessage_validRemark_success() {
        Person person = new PersonBuilder().build();
        RemarkCommand command = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, Messages.format(person));
        assertEquals(expectedMessage, command.generateSuccessMessage(person));
    }

    @Test
    public void toStringMethod_validRemarkCommand_success() {
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, REMARK_STUB);

        String expected = new ToStringBuilder(remarkCommand)
                .add("index", INDEX_FIRST_PERSON)
                .add("remark", REMARK_STUB)
                .toString();

        assertEquals(expected, remarkCommand.toString());
    }
}
