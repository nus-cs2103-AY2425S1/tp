package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.DESC_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADELE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showConcertAtIndex;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONCERT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CONCERT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditConcertCommand.EditConcertDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertContact;
import seedu.address.testutil.ConcertBuilder;
import seedu.address.testutil.EditConcertDescriptorBuilder;

public class EditConcertCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Concert editedConcert = new ConcertBuilder().build();
        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder(editedConcert).build();
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_FIRST_CONCERT, descriptor);
        Concert originalConcert = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT
                .getZeroBased());

        String expectedMessage = String.format(EditConcertCommand.MESSAGE_EDIT_CONCERT_SUCCESS,
                Messages.format(editedConcert));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setConcert(originalConcert, editedConcert);
        expectedModel.getFilteredConcertContactList().forEach(cc -> {
            if (cc.getConcert().equals(originalConcert)) {
                expectedModel.setConcertContact(cc, new ConcertContact(cc.getPerson(), editedConcert));
            }
        });
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noConcertContact_success() {
        Concert editedConcert = new ConcertBuilder().build();
        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder(editedConcert).build();
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_THIRD_CONCERT, descriptor);
        Concert originalConcert = model.getFilteredConcertList().get(INDEX_THIRD_CONCERT.getZeroBased());

        String expectedMessage = String.format(EditConcertCommand.MESSAGE_EDIT_CONCERT_SUCCESS,
                Messages.format(editedConcert));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setConcert(originalConcert, editedConcert);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedtUnfilteredList_success() {
        Index indexLastConcert = Index.fromOneBased(model.getFilteredConcertList().size());
        Concert lastConcert = model.getFilteredConcertList().get(indexLastConcert.getZeroBased());

        ConcertBuilder concertInList = new ConcertBuilder(lastConcert);
        Concert editedConcert = concertInList.withName(VALID_NAME_COACHELLA).build();

        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder().withName(
                VALID_NAME_COACHELLA).build();
        EditConcertCommand editCommand = new EditConcertCommand(indexLastConcert, descriptor);

        String expectedMessage = String.format(EditConcertCommand.MESSAGE_EDIT_CONCERT_SUCCESS,
                Messages.format(editedConcert));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.setConcert(lastConcert, editedConcert);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_FIRST_CONCERT, new EditConcertDescriptor());
        Concert editedConcert = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT.getZeroBased());

        String expectedMessage = String.format(EditConcertCommand.MESSAGE_EDIT_CONCERT_SUCCESS, Messages
                .format(editedConcert));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        Concert concertInFilteredList = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT
                .getZeroBased());
        Concert editedConcert = new ConcertBuilder(concertInFilteredList).withName(VALID_NAME_ADELE)
                .build();
        Concert originalConcert = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT.getZeroBased());

        EditConcertCommand editCommand = new EditConcertCommand(INDEX_FIRST_CONCERT,
                new EditConcertDescriptorBuilder().withName(VALID_NAME_ADELE).build());

        String expectedMessage = String.format(EditConcertCommand.MESSAGE_EDIT_CONCERT_SUCCESS, Messages
                .format(editedConcert));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                new UserPrefs());
        expectedModel.setConcert(model.getFilteredConcertList().get(0), editedConcert);
        expectedModel.getFilteredConcertContactList().forEach(cc -> {
            if (cc.getConcert().equals(originalConcert)) {
                expectedModel.setConcertContact(cc, new ConcertContact(cc.getPerson(), editedConcert));
            }
        });

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void constructor_null_failure() {
        assertThrows(NullPointerException.class, ()
                -> new EditConcertCommand(INDEX_FIRST_CONCERT, (EditConcertDescriptor) null));
        assertThrows(NullPointerException.class, ()
                -> new EditConcertCommand((Index) null, DESC_COACHELLA));
        assertThrows(NullPointerException.class, ()
                -> new EditConcertCommand((Index) null, (EditConcertDescriptor) null));
    }

    @Test
    public void execute_duplicateConcertUnfilteredList_failure() {
        Concert firstConcert = model.getFilteredConcertList().get(INDEX_FIRST_CONCERT.getZeroBased());
        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder(firstConcert).build();
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_SECOND_CONCERT, descriptor);

        assertCommandFailure(editCommand, model, EditConcertCommand.MESSAGE_DUPLICATE_CONCERT);
    }

    @Test
    public void execute_duplicateConcertFilteredList_failure() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);

        // edit concert in filtered list into a duplicate in address book
        Concert concertInList = model.getAddressBook().getConcertList().get(INDEX_SECOND_CONCERT
                .getZeroBased());
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_FIRST_CONCERT,
                new EditConcertDescriptorBuilder(concertInList).build());

        assertCommandFailure(editCommand, model, EditConcertCommand.MESSAGE_DUPLICATE_CONCERT);
    }

    @Test
    public void execute_invalidConcertIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditConcertDescriptor descriptor = new EditConcertDescriptorBuilder().withName(VALID_NAME_COACHELLA)
                .build();
        EditConcertCommand editCommand = new EditConcertCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }


    /**
     * Edit filtered list where index is larger than size of filtered list, but smaller than size of
     * address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showConcertAtIndex(model, INDEX_FIRST_CONCERT);
        Index outOfBoundIndex = INDEX_SECOND_CONCERT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditConcertCommand editCommand = new EditConcertCommand(outOfBoundIndex, new EditConcertDescriptorBuilder()
                .withName(VALID_NAME_ADELE).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONCERT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditConcertCommand standardCommand = new EditConcertCommand(INDEX_FIRST_CONCERT, DESC_COACHELLA);

        // same values -> returns true
        EditConcertDescriptor copyDescriptor = new EditConcertDescriptor(DESC_COACHELLA);
        EditConcertCommand commandWithSameValues = new EditConcertCommand(INDEX_FIRST_CONCERT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditConcertCommand(INDEX_SECOND_CONCERT, DESC_COACHELLA)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditConcertCommand(INDEX_FIRST_CONCERT, DESC_GLASTONBURY)));
    }

    @Test
    public void toStringMethod() {
        EditConcertDescriptor editConcertDescriptor = new EditConcertDescriptor();
        EditConcertCommand editCommand = new EditConcertCommand(INDEX_FIRST_CONCERT,
                editConcertDescriptor);
        String expected = EditConcertCommand.class.getCanonicalName() + "{index=" + INDEX_FIRST_CONCERT
                + ", editConcertDescriptor=" + editConcertDescriptor + "}";
        assertEquals(editCommand.toString(), expected);
    }
}
