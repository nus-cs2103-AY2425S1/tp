package hallpointer.address.model.member;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
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

        // Same name different telegram
        Member updatedAlice = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueMemberList.contains(updatedAlice));

        // Same telegram different name
        updatedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueMemberList.contains(updatedAlice));

        // Same name and telegram
        updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
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

        // Same name different telegram
        Member updatedAliceDifferentTelegram = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(updatedAliceDifferentTelegram));

        // Same telegram different name
        Member updatedAliceDifferentName = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(updatedAliceDifferentName));
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(updatedAliceDifferentName));

        // Same name and telegram
        Member updatedAlice = new MemberBuilder(ALICE).build();
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(updatedAlice));
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

        uniqueMemberList.add(BOB);
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.setMember(ALICE, BOB));
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.setMember(CARL, ALICE));
    }

    @Test
    public void setMember_updatedMemberIsSameMember_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setMember(ALICE, ALICE);
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(ALICE);
        assertEquals(expectedList, uniqueMemberList);
    }

    @Test
    public void setMember_updatedMemberHasSameIdentity_success() {
        uniqueMemberList.add(ALICE);

        // Same name different telegram
        Member updatedAliceDifferentTelegram = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemberList.setMember(ALICE, updatedAliceDifferentTelegram);
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(updatedAliceDifferentTelegram);
        assertEquals(expectedList, uniqueMemberList);
        uniqueMemberList.setMember(updatedAliceDifferentTelegram, ALICE); // Cleaning up for later tests

        // Same telegram different name
        Member updatedAliceDifferentName = new MemberBuilder(ALICE).withName(VALID_NAME_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemberList.setMember(ALICE, updatedAliceDifferentName);
        expectedList = new UniqueMemberList();
        expectedList.add(updatedAliceDifferentName);
        assertEquals(expectedList, uniqueMemberList);
        uniqueMemberList.setMember(updatedAliceDifferentName, ALICE); // Cleaning up for later tests


        // Same name and telegram
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueMemberList.setMember(ALICE, updatedAlice);
        expectedList = new UniqueMemberList();
        expectedList.add(updatedAlice);
        assertEquals(expectedList, uniqueMemberList);

    }

    @Test
    public void setMember_updatedMemberHasDifferentIdentity_success() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.setMember(ALICE, BOB);
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(BOB);
        assertEquals(expectedList, uniqueMemberList);
    }

    @Test
    public void setMember_updatedMemberHasNonUniqueIdentity_throwsDuplicateMemberException() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);

        // Same name different telegram
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMember(ALICE,
                new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build()));

        // Same telegram different name
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMember(ALICE,
                new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build()));

        // Different name and telegram
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMember(ALICE, BOB));
    }

    @Test
    public void remove_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.remove(null));
    }

    @Test
    public void remove_memberDoesNotExist_throwsMemberNotFoundException() {
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.remove(ALICE));

        uniqueMemberList.add(BOB);
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.remove(ALICE));
    }

    @Test
    public void remove_existingMember_removesMember() {
        uniqueMemberList.add(BOB);
        uniqueMemberList.add(ALICE);
        uniqueMemberList.remove(ALICE);
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(BOB);
        assertEquals(expectedList, uniqueMemberList);

        uniqueMemberList.remove(BOB);
        expectedList = new UniqueMemberList();
        assertEquals(expectedList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullUniqueMemberList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMembers((UniqueMemberList) null));
    }

    @Test
    public void setMembers_uniqueMemberList_replacesOwnListWithProvidedUniqueMemberList() {
        uniqueMemberList.add(ALICE);
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(BOB);
        uniqueMemberList.setMembers(expectedList);
        assertEquals(expectedList, uniqueMemberList);
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
        UniqueMemberList expectedList = new UniqueMemberList();
        expectedList.add(BOB);
        assertEquals(expectedList, uniqueMemberList);
    }

    @Test
    public void setMembers_listWithDuplicateMembers_throwsDuplicateMemberException() {
        // Same name different telegram
        List<Member> listWithDuplicateMembersDifferentTelegram = Arrays.asList(ALICE,
                new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build());
        assertThrows(DuplicateMemberException.class, () ->
                uniqueMemberList.setMembers(listWithDuplicateMembersDifferentTelegram));

        // Same telegram different name
        List<Member> listWithDuplicateMembersDifferentName = Arrays.asList(ALICE,
                new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build());
        assertThrows(DuplicateMemberException.class, () ->
                uniqueMemberList.setMembers(listWithDuplicateMembersDifferentName));

        // Same name and telegram
        List<Member> listWithDuplicateMembers = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMembers(listWithDuplicateMembers));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueMemberList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void equals_true() {
        UniqueMemberList expectedList = new UniqueMemberList();
        assertTrue(uniqueMemberList.equals(expectedList)); // Empty also ok

        uniqueMemberList.add(ALICE);
        expectedList.add(ALICE);
        assertTrue(uniqueMemberList.equals(expectedList)); // Non-empty ok

        // same object -> returns true
        assertTrue(uniqueMemberList.equals(uniqueMemberList));
    }
    @Test
    public void equals_nullOrDifferentType_false() {
        assertFalse(uniqueMemberList.equals(null));
        assertFalse(uniqueMemberList.equals(0.9));
    }

    @Test
    public void equals_differentMembersOrOrder_false() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);

        // same values different order -> returns false
        UniqueMemberList differentList = new UniqueMemberList();
        differentList.add(BOB);
        differentList.add(ALICE);
        assertFalse(uniqueMemberList.equals(differentList));

        // different member -> returns false
        differentList = new UniqueMemberList();
        differentList.add(ALICE);
        differentList.add(CARL);
        assertFalse(uniqueMemberList.equals(differentList));
    }

    @Test
    public void equals_sameIdentityDifferentDetails_false() {
        uniqueMemberList.add(ALICE);
        uniqueMemberList.add(BOB);

        // same name different telegram -> returns false
        UniqueMemberList differentList = new UniqueMemberList();
        differentList.add(ALICE);
        differentList.add(new MemberBuilder(BOB).withTelegram(VALID_TELEGRAM_AMY).build());
        assertFalse(uniqueMemberList.equals(differentList));

        // same telegram different name -> returns false
        differentList = new UniqueMemberList();
        differentList.add(ALICE);
        differentList.add(new MemberBuilder(BOB).withName(VALID_NAME_AMY).build());
        assertFalse(uniqueMemberList.equals(differentList));

        // same name and telegram, different details -> returns false
        differentList = new UniqueMemberList();
        differentList.add(new MemberBuilder(ALICE).withRoom("1-1-1").build());
        differentList.add(BOB);
        assertFalse(uniqueMemberList.equals(differentList));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueMemberList.asUnmodifiableObservableList().toString(), uniqueMemberList.toString());
    }
}
