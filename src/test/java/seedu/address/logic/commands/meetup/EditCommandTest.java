package seedu.address.logic.commands.meetup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_NETWORKING_MEETUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PITCH_MEETUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEETUP_INFO_PITCH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMeetUpAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETUP;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETUP;
import static seedu.address.testutil.buyer.TypicalBuyers.getTypicalBuyerList;
import static seedu.address.testutil.meetup.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.property.TypicalProperties.getTypicalPropertyList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.meetup.EditCommand.EditMeetUpDescriptor;
import seedu.address.model.BuyerList;
import seedu.address.model.MeetUpList;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PropertyList;
import seedu.address.model.UserPrefs;
import seedu.address.model.meetup.MeetUp;
import seedu.address.testutil.meetup.EditMeetUpDescriptorBuilder;
import seedu.address.testutil.meetup.MeetUpBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalBuyerList(), new UserPrefs(), getTypicalMeetUpList(),
            getTypicalPropertyList());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        MeetUp editedMeetUp = new MeetUpBuilder().build();
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder(editedMeetUp).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEETUP, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setMeetUp(model.getFilteredMeetUpList().get(0), editedMeetUp);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMeetUp = Index.fromOneBased(model.getFilteredMeetUpList().size());
        MeetUp lastMeetUp = model.getFilteredMeetUpList().get(indexLastMeetUp.getZeroBased());

        MeetUpBuilder meetUpInList = new MeetUpBuilder(lastMeetUp);
        MeetUp editedMeetUp = meetUpInList.withInfo(VALID_MEETUP_INFO_PITCH).build();

        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_PITCH).build();
        EditCommand editCommand = new EditCommand(indexLastMeetUp, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setMeetUp(lastMeetUp, editedMeetUp);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEETUP, new EditMeetUpDescriptor());
        MeetUp editedMeetUp = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        MeetUp meetUpInFilteredList = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        MeetUp editedMeetUp = new MeetUpBuilder(meetUpInFilteredList).withInfo(VALID_MEETUP_INFO_PITCH).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEETUP,
                new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_PITCH).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEETUP_SUCCESS,
                Messages.format(editedMeetUp));

        Model expectedModel = new ModelManager(new BuyerList(model.getBuyerList()), new UserPrefs(),
                new MeetUpList(model.getMeetUpList()), new PropertyList(model.getPropertyList()));
        expectedModel.setMeetUp(model.getFilteredMeetUpList().get(0), editedMeetUp);
        CommandResult expectedCommandResult = new CommandResult(expectedMessage, false, false, true, false, false);

        assertCommandSuccess(editCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_duplicateMeetUpUnfilteredList_failure() {
        MeetUp firstMeetUp = model.getFilteredMeetUpList().get(INDEX_FIRST_MEETUP.getZeroBased());
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder(firstMeetUp).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MEETUP, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEETUP);
    }

    @Test
    public void execute_duplicateMeetUpFilteredList_failure() {
        showMeetUpAtIndex(model, INDEX_FIRST_MEETUP);

        // edit meetup in filtered list into a duplicate in buyer list
        MeetUp meetUpInList = model.getMeetUpList().getMeetUpList().get(INDEX_SECOND_MEETUP.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEETUP,
                new EditMeetUpDescriptorBuilder(meetUpInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEETUP);
    }

    @Test
    public void execute_invalidMeetUpIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMeetUpList().size() + 1);
        EditMeetUpDescriptor descriptor = new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_PITCH).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
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

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMeetUpDescriptorBuilder().withInfo(VALID_MEETUP_INFO_PITCH).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEETUP_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEETUP, DESC_PITCH_MEETUP);

        // same values -> returns true
        EditMeetUpDescriptor copyDescriptor = new EditMeetUpDescriptor(DESC_PITCH_MEETUP);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEETUP, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEETUP, DESC_PITCH_MEETUP)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEETUP, DESC_NETWORKING_MEETUP)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditMeetUpDescriptor editMeetUpDescriptor = new EditMeetUpDescriptor();
        EditCommand editCommand = new EditCommand(index, editMeetUpDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editMeetUpDescriptor="
                + editMeetUpDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }
}
