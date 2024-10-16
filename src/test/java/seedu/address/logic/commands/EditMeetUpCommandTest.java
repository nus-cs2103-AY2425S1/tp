package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JASON_MEETUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JESSY_MEETUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_JASON;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetUpAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETUP;
import static seedu.address.testutil.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditMeetUpCommand.EditMeetUpDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.EditMeetUpDescriptorBuilder;
import seedu.address.testutil.MeetUpBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditMeetUpCommand.
 */
public class EditMeetUpCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        MeetUp editedMeetUp = new MeetUpBuilder().build();
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder(editedMeetUp).build();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(INDEX_FIRST_MEETUP, descriptor);

        String expectedMessage = String.format(EditMeetUpCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setMeetUp(model.getFilteredMeetUpList().get(0), editedMeetUp);

        assertCommandSuccess(editMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMeetUp = Index.fromOneBased(model.getFilteredMeetUpList().size());
        MeetUp lastMeetUp = model.getFilteredMeetUpList().get(indexLastMeetUp.getZeroBased());

        MeetUpBuilder meetUpInList = new MeetUpBuilder(lastMeetUp);
        MeetUp editedMeetUp = meetUpInList.withInfo(VALID_MEETUP_INFO_JASON).build();

        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_JASON).build();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(indexLastMeetUp, descriptor);

        String expectedMessage = String.format(EditMeetUpCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setMeetUp(lastMeetUp, editedMeetUp);

        assertCommandSuccess(editMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(INDEX_FIRST_MEETUP, new EditMeetUpDescriptor());
        MeetUp editedMeetUp = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());

        String expectedMessage = String.format(EditMeetUpCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));

        assertCommandSuccess(editMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        MeetUp meetUpInFilteredList = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        MeetUp editedMeetUp = new MeetUpBuilder(meetUpInFilteredList).withInfo(VALID_MEETUP_INFO_JASON).build();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(INDEX_FIRST_MEETUP,
                new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_JASON).build());

        String expectedMessage = String.format(EditMeetUpCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()));
        expectedModel.setMeetUp(model.getFilteredMeetUpList().get(0), editedMeetUp);

        assertCommandSuccess(editMeetUpCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMeetUpUnfilteredList_failure() {
        MeetUp firstMeetUp = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder(firstMeetUp).build();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(INDEX_SECOND_MEETUP, descriptor);

        assertCommandFailure(editMeetUpCommand, model, EditMeetUpCommand.MESSAGE_DUPLICATE_MEETUP);
    }

    @Test
    public void execute_duplicateMeetUpFilteredList_failure() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        // edit meetup in filtered list into a duplicate in address book
        MeetUp meetUpInList = model.getMeetUpList().getMeetUpList().get(INDEX_SECOND_MEETUP.getZeroBased());
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(INDEX_FIRST_MEETUP,
                new EditMeetUpDescriptorBuilder(meetUpInList).build());

        assertCommandFailure(editMeetUpCommand, model, EditMeetUpCommand.MESSAGE_DUPLICATE_MEETUP);
    }

    @Test
    public void execute_invalidMeetUpIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMeetUpList().size() + 1);
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_JASON).build();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editMeetUpCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of meetup list
     */
    @Test
    public void execute_invalidMeetUpIndexFilteredList_failure() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);
        Index outOfBoundIndex = INDEX_SECOND_MEETUP;
        // ensures that outOfBoundIndex is still in bounds of meetup list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getMeetUpList().getMeetUpList().size());

        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(outOfBoundIndex,
                new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_JASON).build());

        assertCommandFailure(editMeetUpCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditMeetUpCommand standardCommand = new EditMeetUpCommand(INDEX_FIRST_MEETUP, DESC_JASON_MEETUP);

        // same values -> returns true
        EditMeetUpDescriptor copyDescriptor = new EditMeetUpDescriptor(DESC_JASON_MEETUP);
        EditMeetUpCommand commandWithSameValues = new EditMeetUpCommand(INDEX_FIRST_MEETUP, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMeetUpCommand(INDEX_SECOND_MEETUP, DESC_JASON_MEETUP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMeetUpCommand(INDEX_FIRST_MEETUP, DESC_JESSY_MEETUP)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditMeetUpDescriptor editMeetUpDescriptor = new EditMeetUpDescriptor();
        EditMeetUpCommand editMeetUpCommand = new EditMeetUpCommand(index, editMeetUpDescriptor);
        String expected = EditMeetUpCommand.class.getCanonicalName() + "{index=" + index + ", editMeetUpDescriptor="
                + editMeetUpDescriptor + "}";
        assertEquals(expected, editMeetUpCommand.toString());
    }
}
