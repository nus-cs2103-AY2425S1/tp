package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.DESC_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.DESC_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.logic.commands.CommandTestUtil.showMemberAtIndex;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.Messages;
import hallpointer.address.logic.commands.UpdateMemberCommand.UpdateMemberDescriptor;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.UpdateMemberDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for UpdateMemberCommand.
 */
public class UpdateMemberCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Member updatedMember = new MemberBuilder().build();
        UpdateMemberDescriptor descriptor = new UpdateMemberDescriptorBuilder(updatedMember).build();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(INDEX_FIRST_MEMBER, descriptor);

        String expectedMessage = String.format(
                UpdateMemberCommand.MESSAGE_UPDATE_MEMBER_SUCCESS, Messages.format(updatedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), updatedMember);

        assertCommandSuccess(updateMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMember = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastMember.getZeroBased());

        MemberBuilder memberInList = new MemberBuilder(lastMember);
        Member updatedMember = memberInList.withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        UpdateMemberDescriptor descriptor = new UpdateMemberDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).withTags(VALID_TAG_HUSBAND).build();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(indexLastMember, descriptor);

        String expectedMessage = String.format(
                UpdateMemberCommand.MESSAGE_UPDATE_MEMBER_SUCCESS, Messages.format(updatedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(lastMember, updatedMember);

        assertCommandSuccess(updateMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        UpdateMemberDescriptor descriptor = new UpdateMemberDescriptor();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(INDEX_FIRST_MEMBER, descriptor);
        Member updatedMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());

        String expectedMessage = String.format(
                UpdateMemberCommand.MESSAGE_UPDATE_MEMBER_SUCCESS, Messages.format(updatedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(updateMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        Member memberInFilteredList = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        Member updatedMember = new MemberBuilder(memberInFilteredList).withName(VALID_NAME_BOB).build();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(INDEX_FIRST_MEMBER,
                new UpdateMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(
                UpdateMemberCommand.MESSAGE_UPDATE_MEMBER_SUCCESS, Messages.format(updatedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), updatedMember);

        assertCommandSuccess(updateMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMemberUnfilteredList_failure() {
        Member firstMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        UpdateMemberDescriptor descriptor = new UpdateMemberDescriptorBuilder(firstMember).build();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(INDEX_SECOND_MEMBER, descriptor);

        assertCommandFailure(updateMemberCommand, model, UpdateMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicateMemberFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        // update member in filtered list into a duplicate in address book
        Member memberInList = model.getAddressBook().getMemberList().get(INDEX_SECOND_MEMBER.getZeroBased());
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(INDEX_FIRST_MEMBER,
                new UpdateMemberDescriptorBuilder(memberInList).build());

        assertCommandFailure(updateMemberCommand, model, UpdateMemberCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_invalidMemberIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        UpdateMemberDescriptor descriptor = new UpdateMemberDescriptorBuilder().withName(VALID_NAME_BOB).build();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(updateMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    /**
     * Update filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMemberIndexFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        Index outOfBoundIndex = INDEX_SECOND_MEMBER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMemberList().size());

        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(outOfBoundIndex,
                new UpdateMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(updateMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final UpdateMemberCommand standardCommand = new UpdateMemberCommand(INDEX_FIRST_MEMBER, DESC_AMY);

        // same values -> returns true
        UpdateMemberDescriptor copyDescriptor = new UpdateMemberDescriptor(DESC_AMY);
        UpdateMemberCommand commandWithSameValues = new UpdateMemberCommand(INDEX_FIRST_MEMBER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new UpdateMemberCommand(INDEX_SECOND_MEMBER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new UpdateMemberCommand(INDEX_FIRST_MEMBER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        UpdateMemberDescriptor updateMemberDescriptor = new UpdateMemberDescriptor();
        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(index, updateMemberDescriptor);
        String expected = UpdateMemberCommand.class.getCanonicalName() + "{index=" + index + ", UpdateMemberDescriptor="
                + updateMemberDescriptor + "}";
        assertEquals(expected, updateMemberCommand.toString());
    }

}
