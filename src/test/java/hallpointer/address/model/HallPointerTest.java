package hallpointer.address.model;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;
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

public class HallPointerTest {

    private final HallPointer hallPointer = new HallPointer();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), hallPointer.getMemberList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hallPointer.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyHallPointer_replacesData() {
        HallPointer newData = getTypicalHallPointer();
        hallPointer.resetData(newData);
        assertEquals(newData, hallPointer);
    }

    @Test
    public void resetData_withDuplicateMembers_throwsDuplicateMemberException() {
        // Two members with the same identity fields
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Member> newMembers = Arrays.asList(ALICE, updatedAlice);
        HallPointerStub newData = new HallPointerStub(newMembers);

        assertThrows(DuplicateMemberException.class, () -> hallPointer.resetData(newData));
    }

    @Test
    public void hasMember_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> hallPointer.hasMembers(null));
    }

    @Test
    public void hasMember_memberNotInHallPointer_returnsFalse() {
        assertFalse(hallPointer.hasMembers(ALICE));
    }

    @Test
    public void hasMember_memberInHallPointer_returnsTrue() {
        hallPointer.addMember(ALICE);
        assertTrue(hallPointer.hasMembers(ALICE));
    }

    @Test
    public void hasMember_memberWithSameIdentityFieldsInHallPointer_returnsTrue() {
        hallPointer.addMember(ALICE);
        Member updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(hallPointer.hasMembers(updatedAlice));
    }

    @Test
    public void getMemberList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> hallPointer.getMemberList().remove(0));
    }

    @Test
    public void equals() {
        hallPointer.addMember(ALICE);

        // same values -> returns true
        HallPointer otherHallPointer = new HallPointer();
        otherHallPointer.addMember(ALICE);
        assertTrue(hallPointer.equals(otherHallPointer));

        // same object -> returns true
        assertTrue(hallPointer.equals(hallPointer));

        // null -> returns false
        assertFalse(hallPointer.equals(null));

        // different types -> returns false
        assertFalse(hallPointer.equals(6.0f));

        // different members in member list -> returns false
        otherHallPointer.addMember(BOB);
        assertFalse(hallPointer.equals(otherHallPointer));
    }

    @Test
    public void toStringMethod() {
        String expected = HallPointer.class.getCanonicalName() + "{members=" + hallPointer.getMemberList() + "}";
        assertEquals(expected, hallPointer.toString());
    }

    @Test
    public void countMemberOccurrences_memberNotInList_returnsZero() {
        Member member = new MemberBuilder().build();
        assertEquals(0, hallPointer.countMemberOccurrences(member));
    }

    @Test
    public void countMemberOccurrences_memberInList_returnsCorrectCount() {
        Member member = new MemberBuilder().build();
        hallPointer.addMember(member);
        assertEquals(1, hallPointer.countMemberOccurrences(member));
    }

    /**
     * A stub ReadOnlyHallPointer whose members list can violate interface constraints.
     */
    private static class HallPointerStub implements ReadOnlyHallPointer {
        private final ObservableList<Member> members = FXCollections.observableArrayList();

        HallPointerStub(Collection<Member> members) {
            this.members.setAll(members);
        }

        @Override
        public ObservableList<Member> getMemberList() {
            return members;
        }
    }

}
