package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
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
import seedu.address.model.person.SupplierStatus;
import seedu.address.testutil.PersonBuilder;


public class MarkSupplierCommandTest {
    private static final String SUPPLIER_STATUS_STUB = "active";
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withStatus(SUPPLIER_STATUS_STUB).build();
        MarkSupplierCommand markCommand = new MarkSupplierCommand(INDEX_FIRST_PERSON,
                new SupplierStatus(editedPerson.getStatus().status));
        String expectedMessage = String.format(MarkSupplierCommand.MESSAGE_MARK_SUPPLIER_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased(), editedPerson.getStatus().status);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(markCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withStatus(SUPPLIER_STATUS_STUB).build();
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(INDEX_FIRST_PERSON,
                new SupplierStatus(editedPerson.getStatus().status));
        String expectedMessage = String.format(MarkSupplierCommand.MESSAGE_MARK_SUPPLIER_SUCCESS,
                INDEX_FIRST_PERSON.getOneBased(), editedPerson.getStatus().status);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(outOfBoundIndex,
                new SupplierStatus(SUPPLIER_STATUS_STUB));
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
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
        MarkSupplierCommand remarkCommand = new MarkSupplierCommand(outOfBoundIndex,
                new SupplierStatus(SUPPLIER_STATUS_STUB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
    }
    @Test
    public void equals() {
        final MarkSupplierCommand standardCommand = new MarkSupplierCommand(INDEX_FIRST_PERSON,
                new SupplierStatus(SUPPLIER_STATUS_STUB));

        // same values -> returns true
        MarkSupplierCommand commandWithSameValues = new MarkSupplierCommand(INDEX_FIRST_PERSON,
                new SupplierStatus(SUPPLIER_STATUS_STUB));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MarkSupplierCommand(INDEX_SECOND_PERSON,
                new SupplierStatus(VALID_STATUS_AMY))));

        // different status -> returns false
        assertFalse(standardCommand.equals(new MarkSupplierCommand(INDEX_FIRST_PERSON,
                new SupplierStatus(VALID_STATUS_BOB))));
    }
}
