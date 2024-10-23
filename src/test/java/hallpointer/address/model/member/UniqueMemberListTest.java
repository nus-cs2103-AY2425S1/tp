package hallpointer.address.model.member;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static hallpointer.address.testutil.TypicalMembers.CARL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.member.exceptions.DuplicateMemberException;
import hallpointer.address.model.member.exceptions.MemberNotFoundException;
import hallpointer.address.testutil.MemberBuilder;

public class UniqueMemberListTest {

    private final UniqueMemberList uniqueMemberList = new UniqueMemberList();

    @Test
    public void contains_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.contains(null));
    }

    @Test
    public void contains_memberNotInList_returnsFalse() {
        assertFalse(uniqueMemberList.contains(ALICE));
    }

    @Test
    public void contains_memberInList_returnsTrue() {
        uniqueMemberList.add(ALICE);
        assertTrue(uniqueMemberList.contains(ALICE));
    }

    @Test
    public void contains_memberWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemberList.add(ALICE);
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueMemberList.contains(updatedAlice));
    }

    @Test
    public void add_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.add(null));
    }

    @Test
    public void add_duplicateMember_throwsDuplicateMemberException() {
        uniqueMemberList.add(ALICE);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(ALICE));
    }

    @Test
    public void setMember_nullTargetMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMember(null, ALICE));
    }

    @Test
    public void setMember_nullUpdatedMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMember(ALICE, null));
    }

    @Test
    public void setMember_targetMemberNotInList_throwsMemberNotFoundException() {
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.setMember(ALICE, ALICE));
    }

    @Test
    public void setMember_updatedMemberIsSameMember_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setMember(ALICE, ALICE);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(ALICE);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_updatedMemberHasSameIdentity_success() {
        uniqueMemberList.add(ALICE);
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemberList.setMember(ALICE, updatedAlice);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(updatedAlice);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_updatedMemberHasDifferentIdentity_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setMember(ALICE, BOB);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(BOB);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_updatedMemberHasNonUniqueIdentity_throwsDuplicateMemberException() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMember(ALICE, BOB));
    }

    @Test
    public void remove_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.remove(null));
    }

    @Test
    public void remove_memberDoesNotExist_throwsMemberNotFoundException() {
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.remove(ALICE));
    }

    @Test
    public void remove_existingMember_removesMember() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.remove(ALICE);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullUniqueMemberList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMembers((UniqueMemberList) null));
    }

    @Test
    public void setMembers_uniqueMemberList_replacesOwnListWithProvidedUniqueMemberList() {
        uniqueMemberList.add(ALICE);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(BOB);
        uniqueMemberList.setMembers(expectedUniqueMemberList);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMembers((List<Member>) null));
    }

    @Test
    public void setMembers_list_replacesOwnListWithProvidedList() {
        uniqueMemberList.add(ALICE);
        List<Member> memberList = Collections.singletonList(BOB);
        uniqueMemberList.setMembers(memberList);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(BOB);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_listWithDuplicateMembers_throwsDuplicateMemberException() {
        List<Member> listWithDuplicateMembers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMembers(listWithDuplicateMembers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMemberList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);

        // same values -> returns true
        UniqueMemberList differentUniqueMemberList = new UniqueMemberList();
        differentUniqueMemberList.add(ALICE);
        differentUniqueMemberList.add(BOB);
        assertTrue(uniqueMemberList.equals(differentUniqueMemberList));

        // same object -> returns true
        assertTrue(uniqueMemberList.equals(uniqueMemberList));

        // null -> returns false
        assertFalse(uniqueMemberList.equals(null));

        // different type -> returns false
        assertFalse(uniqueMemberList.equals(0.9));

        // different member -> returns false
        differentUniqueMemberList = new UniqueMemberList();
        differentUniqueMemberList.add(ALICE);
        differentUniqueMemberList.add(CARL);
        assertFalse(uniqueMemberList.equals(differentUniqueMemberList));

        // different order -> returns false
        differentUniqueMemberList = new UniqueMemberList();
        differentUniqueMemberList.add(BOB);
        differentUniqueMemberList.add(ALICE);
        assertFalse(uniqueMemberList.equals(differentUniqueMemberList));

        // same member different details -> returns false
        differentUniqueMemberList = new UniqueMemberList();
        differentUniqueMemberList.add(new MemberBuilder(ALICE).withRoom("1/1/1").build());
        differentUniqueMemberList.add(BOB);
        assertFalse(uniqueMemberList.equals(differentUniqueMemberList));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueMemberList.asUnmodifiableObservableList().toString(), uniqueMemberList.toString());
    }
}
