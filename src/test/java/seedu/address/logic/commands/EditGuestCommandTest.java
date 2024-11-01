package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showGuestAtIndex;
import static seedu.address.testutil.TypicalGuests.getTypicalAddressBookWithGuests;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.util.EditGuestDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Guest;
import seedu.address.testutil.EditGuestDescriptorBuilder;
import seedu.address.testutil.GuestBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditGuestCommand.
 */
public class EditGuestCommandTest {
    private Model model = new ModelManager(getTypicalAddressBookWithGuests(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Guest editedGuest = new GuestBuilder().build();
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(editedGuest).build();
        EditGuestCommand editGuestCommand = new EditGuestCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditGuestCommand.MESSAGE_EDIT_GUEST_SUCCESS,
                Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredGuestList().get(0), editedGuest);

        assertCommandSuccess(editGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastGuest = Index.fromOneBased(model.getFilteredGuestList().size());
        Guest lastGuest = (Guest) model.getFilteredGuestList().get(indexLastGuest.getZeroBased());

        GuestBuilder guestInList = new GuestBuilder(lastGuest);
        Guest editedGuest = guestInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditGuestCommand editGuestCommand = new EditGuestCommand(indexLastGuest, descriptor);

        String expectedMessage = String.format(EditGuestCommand.MESSAGE_EDIT_GUEST_SUCCESS,
                Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastGuest, editedGuest);

        assertCommandSuccess(editGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditGuestCommand editGuestCommand = new EditGuestCommand(INDEX_FIRST_PERSON, new EditGuestDescriptor());
        Guest editedGuest = (Guest) model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditGuestCommand.MESSAGE_EDIT_GUEST_SUCCESS,
                Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        Guest guestInFilteredList = (Guest) model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        Guest editedGuest = new GuestBuilder(guestInFilteredList).withName(VALID_NAME_BOB).build();
        EditGuestCommand editGuestCommand = new EditGuestCommand(INDEX_FIRST_PERSON,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditGuestCommand.MESSAGE_EDIT_GUEST_SUCCESS,
                Messages.format(editedGuest));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredGuestList().get(0), editedGuest);

        assertCommandSuccess(editGuestCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateGuestUnfilteredList_failure() {
        Guest firstGuest = (Guest) model.getFilteredGuestList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder(firstGuest).build();
        EditGuestCommand editGuestCommand = new EditGuestCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editGuestCommand, model, EditGuestCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_duplicateGuestFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);

        // edit guest in filtered list into a duplicate in address book
        Guest guestInList = (Guest) model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditGuestCommand editGuestCommand = new EditGuestCommand(INDEX_FIRST_PERSON,
                new EditGuestDescriptorBuilder(guestInList).build());

        assertCommandFailure(editGuestCommand, model, EditGuestCommand.MESSAGE_DUPLICATE_GUEST);
    }

    @Test
    public void execute_invalidGuestIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGuestList().size() + 1);
        EditGuestDescriptor descriptor = new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditGuestCommand editGuestCommand = new EditGuestCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editGuestCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidGuestIndexFilteredList_failure() {
        showGuestAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditGuestCommand editGuestCommand = new EditGuestCommand(outOfBoundIndex,
                new EditGuestDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editGuestCommand, model, Messages.MESSAGE_INVALID_GUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditGuestCommand standardCommand = new EditGuestCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditGuestDescriptor copyDescriptor = new EditGuestDescriptor(DESC_AMY);
        EditGuestCommand commandWithSameValues = new EditGuestCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditGuestCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditGuestCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditGuestDescriptor editGuestDescriptor = new EditGuestDescriptor();
        EditGuestCommand editGuestCommand = new EditGuestCommand(index, editGuestDescriptor);
        String expected = EditGuestCommand.class.getCanonicalName() + "{index=" + index + ", editGuestDescriptor="
                + editGuestDescriptor + "}";
        assertEquals(expected, editGuestCommand.toString());
    }

}
