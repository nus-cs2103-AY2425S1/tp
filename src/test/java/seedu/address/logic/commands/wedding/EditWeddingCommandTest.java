package seedu.address.logic.commands.wedding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WEDDING_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_WEDDING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEDDING_CLIVE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showWeddingAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.wedding.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.wedding.Wedding;
import seedu.address.testutil.EditWeddingDescriptorBuilder;
import seedu.address.testutil.WeddingBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditWeddingCommand.
 */
public class EditWeddingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Wedding editedWedding = new WeddingBuilder().build();
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(editedWedding).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(
                 EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(model.getFilteredWeddingList().get(0), editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLast = Index.fromOneBased(model.getFilteredWeddingList().size());
        Wedding lastWedding = model.getFilteredWeddingList().get(indexLast.getZeroBased());

        WeddingBuilder weddingInList = new WeddingBuilder(lastWedding);
        //Using Bob's address WLOG
        Wedding editedWedding = weddingInList
                .withName(VALID_WEDDING_CLIVE)
                .withAddress(VALID_ADDRESS_BOB).build();

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder()
                .withName(VALID_WEDDING_CLIVE)
                .withAddress(VALID_ADDRESS_BOB).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(indexLast, descriptor);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(lastWedding, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_FIRST, new EditWeddingDescriptor());
        Wedding editedWedding = model.getFilteredWeddingList().get(INDEX_FIRST.getZeroBased());

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showWeddingAtIndex(model, INDEX_FIRST);

        Wedding weddingInFilteredList = model.getFilteredWeddingList().get(INDEX_FIRST.getZeroBased());
        Wedding editedWedding = new WeddingBuilder(weddingInFilteredList).withName(VALID_WEDDING_CLIVE).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_FIRST,
                new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_CLIVE).build());

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(model.getFilteredWeddingList().get(0), editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateWeddingUnfilteredList_failure() {
        Wedding firstWedding = model.getFilteredWeddingList().get(INDEX_FIRST.getZeroBased());
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder(firstWedding).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_SECOND, descriptor);

        assertCommandFailure(editCommand, model, EditWeddingCommand.MESSAGE_DUPLICATE_WEDDING);
    }

    @Test
    public void execute_duplicateWeddingFilteredList_failure() {
        showWeddingAtIndex(model, INDEX_FIRST);

        // edit wedding in filtered list into a duplicate in address book
        Wedding weddingInList = model.getAddressBook().getWeddingList().get(INDEX_SECOND.getZeroBased());
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_FIRST,
                new EditWeddingDescriptorBuilder(weddingInList).build());

        assertCommandFailure(editCommand, model, EditWeddingCommand.MESSAGE_DUPLICATE_WEDDING);
    }

    @Test
    public void execute_invalidWeddingIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWeddingList().size() + 1);
        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_BOB).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidWeddingIndexFilteredList_failure() {
        showWeddingAtIndex(model, INDEX_FIRST);
        Index outOfBoundIndex = INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getWeddingList().size());

        EditWeddingCommand editCommand = new EditWeddingCommand(outOfBoundIndex,
                new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_addressOnlySpecifiedUnfilteredList_success() {
        Wedding lastWedding = model.getFilteredWeddingList().get(INDEX_SECOND.getZeroBased());

        WeddingBuilder weddingInList = new WeddingBuilder(lastWedding);
        Wedding editedWedding = weddingInList.withAddress(VALID_ADDRESS_BOB).build();

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withAddress(VALID_ADDRESS_BOB).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_SECOND, descriptor);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(lastWedding, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_nameOnlySpecifiedUnfilteredList_success() {
        Wedding lastWedding = model.getFilteredWeddingList().get(INDEX_FIRST.getZeroBased());

        WeddingBuilder weddingInList = new WeddingBuilder(lastWedding);
        Wedding editedWedding = weddingInList.withName(VALID_WEDDING_CLIVE).build();

        EditWeddingDescriptor descriptor = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_CLIVE).build();
        EditWeddingCommand editCommand = new EditWeddingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(
                EditWeddingCommand.MESSAGE_EDIT_WEDDING_SUCCESS, Messages.format(editedWedding));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setWedding(lastWedding, editedWedding);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final EditWeddingCommand standardCommand = new EditWeddingCommand(INDEX_FIRST, DESC_WEDDING_AMY);

        // same values -> returns true
        EditWeddingDescriptor copyDescriptor = new EditWeddingDescriptor(DESC_WEDDING_AMY);
        EditWeddingCommand commandWithSameValues = new EditWeddingCommand(INDEX_FIRST, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        assertTrue(copyDescriptor.equals(copyDescriptor));

        // null -> returns false
        assertFalse(standardCommand.equals(null));
        assertFalse(copyDescriptor.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        assertFalse(copyDescriptor.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditWeddingCommand(INDEX_SECOND, DESC_WEDDING_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditWeddingCommand(INDEX_FIRST, DESC_WEDDING_BOB)));
    }

    @Test
    public void editWeddingDescriptor_equals() {
        EditWeddingDescriptor descriptorA = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_BOB).build();
        EditWeddingDescriptor descriptorB = new EditWeddingDescriptorBuilder().withName(VALID_WEDDING_CLIVE).build();
        EditWeddingDescriptor descriptorCopy = new EditWeddingDescriptor(descriptorA);

        // same values -> returns true
        assertTrue(descriptorA.equals(descriptorCopy));

        // same object -> returns true
        assertTrue(descriptorA.equals(descriptorA));

        // null -> returns false
        assertFalse(descriptorA.equals(null));

        // different types -> returns false
        assertFalse(descriptorA.equals(5));

        // different descriptor -> returns false
        assertFalse(descriptorA.equals(descriptorB));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditWeddingDescriptor editWeddingDescriptor = new EditWeddingDescriptor();
        EditWeddingCommand editCommand = new EditWeddingCommand(index, editWeddingDescriptor);
        String expected = EditWeddingCommand.class.getCanonicalName() + "{index=" + index + ", editWeddingDescriptor="
                + editWeddingDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
