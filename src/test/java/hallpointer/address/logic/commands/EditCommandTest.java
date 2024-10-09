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
import hallpointer.address.logic.commands.EditCommand.EditMemberDescriptor;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.testutil.EditMemberDescriptorBuilder;
import hallpointer.address.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Member editedMember = new MemberBuilder().build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMember = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastMember.getZeroBased());

        MemberBuilder memberInList = new MemberBuilder(lastMember);
        Member editedMember = memberInList.withName(VALID_NAME_BOB).withTelegram(VALID_TELEGRAM_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastMember, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(lastMember, editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, new EditMemberDescriptor());
        Member editedMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        Member memberInFilteredList = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        Member editedMember = new MemberBuilder(memberInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER,
                new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, Messages.format(editedMember));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMemberUnfilteredList_failure() {
        Member firstMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(firstMember).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MEMBER, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicateMemberFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        // edit member in filtered list into a duplicate in address book
        Member memberInList = model.getAddressBook().getMemberList().get(INDEX_SECOND_MEMBER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER,
                new EditMemberDescriptorBuilder(memberInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_invalidMemberIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMemberIndexFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        Index outOfBoundIndex = INDEX_SECOND_MEMBER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getMemberList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMemberDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEMBER, DESC_AMY);

        // same values -> returns true
        EditMemberDescriptor copyDescriptor = new EditMemberDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEMBER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEMBER, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEMBER, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditMemberDescriptor editMemberDescriptor = new EditMemberDescriptor();
        EditCommand editCommand = new EditCommand(index, editMemberDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editMemberDescriptor="
                + editMemberDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

}
