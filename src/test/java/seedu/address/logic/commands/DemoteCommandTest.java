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
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for DemoteCommand.
 */
public class DemoteCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndPerson_success() {
        Index lastIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(lastIndex.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person demotedPerson = personInList.withIsEmployee(false).build();
        DemoteCommand demoteCommand = new DemoteCommand(lastIndex);

        String expectedMessage = String.format(DemoteCommand.MESSAGE_SUCCESS, Messages.format(demotedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, demotedPerson);

        assertCommandSuccess(demoteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_notAnEmployee_failure() {
        DemoteCommand demoteCommand = new DemoteCommand(INDEX_FIRST_PERSON);
        assertCommandFailure(demoteCommand, model, DemoteCommand.MESSAGE_NOT_AN_EMPLOYEE);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DemoteCommand demoteCommand = new DemoteCommand(outOfBoundIndex);

        assertCommandFailure(demoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Demote in a filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DemoteCommand demoteCommand = new DemoteCommand(outOfBoundIndex);

        assertCommandFailure(demoteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final DemoteCommand standardCommand = new DemoteCommand(INDEX_FIRST_PERSON);

        // same index -> returns true
        DemoteCommand commandWithSameIndex = new DemoteCommand(INDEX_FIRST_PERSON);
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DemoteCommand(INDEX_SECOND_PERSON)));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        DemoteCommand demoteCommand = new DemoteCommand(index);
        String expected = DemoteCommand.class.getCanonicalName() + "{index=" + index + "}";
        assertEquals(expected, demoteCommand.toString());
    }
}
