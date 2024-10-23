package hallpointer.address.model;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.getTypicalAddressBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.exceptions.DuplicateMemberException;
import hallpointer.address.testutil.MemberBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getMemberList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateMembers_throwsDuplicateMemberException() {
        // Two members with the same identity fields
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Member> newMembers = Arrays.asList(ALICE, updatedAlice);
        AddressBookStub newData = new AddressBookStub(newMembers);

        assertThrows(DuplicateMemberException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasMembers(null));
    }

    @Test
    public void hasMember_memberNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasMembers(ALICE));
    }

    @Test
    public void hasMember_memberInAddressBook_returnsTrue() {
        addressBook.addMember(ALICE);
        assertTrue(addressBook.hasMembers(ALICE));
    }

    @Test
    public void hasMember_memberWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addMember(ALICE);
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasMembers(updatedAlice));
    }

    @Test
    public void getMemberList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getMemberList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{members=" + addressBook.getMemberList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose members list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Member> members = FXCollections.observableArrayList();

        AddressBookStub(Collection<Member> members) {
            this.members.setAll(members);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }
    }

}
