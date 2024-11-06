package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.logic.commands.CommandTestUtil.showMemberAtIndex;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.Messages;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.member.Member;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteMemberCommand}.
 */
public class DeleteMemberCommandTest {

    private Model model = new ModelManager(getTypicalHallPointer(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST_MEMBER);

        String expectedMessage = String.format(
                DeleteMemberCommand.MESSAGE_SUCCESS,
                memberToDelete.getName().value,
                memberToDelete.getRoom().value,
                memberToDelete.getTelegram().value
        );

        ModelManager expectedModel = new ModelManager(model.getHallPointer(), new UserPrefs());
        expectedModel.deleteMember(memberToDelete);

        assertCommandSuccess(deleteMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(outOfBoundIndex);

        assertCommandFailure(deleteMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        Member memberToDelete = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(INDEX_FIRST_MEMBER);

        String expectedMessage = String.format(
                DeleteMemberCommand.MESSAGE_SUCCESS,
                memberToDelete.getName().value,
                memberToDelete.getRoom().value,
                memberToDelete.getTelegram().value
        );

        Model expectedModel = new ModelManager(model.getHallPointer(), new UserPrefs());
        expectedModel.deleteMember(memberToDelete);
        showNoMember(expectedModel);

        assertCommandSuccess(deleteMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        Index outOfBoundIndex = INDEX_SECOND_MEMBER;
        // ensures that outOfBoundIndex is still in bounds of hall pointer list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getHallPointer().getMemberList().size());

        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(outOfBoundIndex);

        assertCommandFailure(deleteMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteMemberCommand deleteFirstCommand = new DeleteMemberCommand(INDEX_FIRST_MEMBER);
        DeleteMemberCommand deleteSecondCommand = new DeleteMemberCommand(INDEX_SECOND_MEMBER);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMemberCommand deleteFirstCommandCopy = new DeleteMemberCommand(INDEX_FIRST_MEMBER);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different member -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteMemberCommand deleteMemberCommand = new DeleteMemberCommand(targetIndex);
        String expected = DeleteMemberCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteMemberCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMember(Model model) {
        model.updateFilteredMemberList(p -> false);

        assertTrue(model.getFilteredMemberList().isEmpty());
    }
}
