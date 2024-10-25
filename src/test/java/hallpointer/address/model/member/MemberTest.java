package hallpointer.address.model.member;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static hallpointer.address.testutil.TypicalSessions.MEETING;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.SessionBuilder;

public class MemberTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Member member = new MemberBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> member.getTags().remove(0));
    }

    @Test
    public void isSameMember() {
        // null -> returns false
        assertFalse(ALICE.isSameMember(null));

        // same object -> returns true
        assertTrue(ALICE.isSameMember(ALICE));

        // same name, all other attributes different -> returns true
        Member updatedAlice = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMember(updatedAlice));

        // different name, all other attributes same -> returns false
        updatedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameMember(updatedAlice));

        // name differs in case, all other attributes different -> returns true
        Member updatedBob = new MemberBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase())
                .withTelegram(VALID_TELEGRAM_AMY).withRoom(VALID_ROOM_AMY).withTags(VALID_TAG_HUSBAND)
                .withSessions(new SessionBuilder(REHEARSAL).build()).build();
        assertTrue(BOB.isSameMember(updatedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        updatedBob = new MemberBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameMember(updatedBob));
    }

    @Test
    public void hasSession_nullSession_throwsNullPointerException() {
        Member member = new MemberBuilder(ALICE).build();
        assertThrows(NullPointerException.class, () -> member.hasSession(null));
    }

    @Test
    public void hasSession_sessionNotInMember_returnsFalse() {
        Member member = new MemberBuilder(ALICE).build();
        assertFalse(member.hasSession(new SessionBuilder(REHEARSAL).build()));

        member.addSession(new SessionBuilder(ATTENDANCE).build());
        assertFalse(member.hasSession(new SessionBuilder(REHEARSAL).build()));
    }

    @Test
    public void hasSession_sessionInMember_returnsTrue() {
        Member member = new MemberBuilder(ALICE).build();
        member.addSession(new SessionBuilder(MEETING).build());
        assertTrue(member.hasSession(new SessionBuilder(MEETING).build()));

        member.addSession(new SessionBuilder(ATTENDANCE).build());
        assertTrue(member.hasSession(new SessionBuilder(MEETING).build()));
        assertTrue(member.hasSession(new SessionBuilder(ATTENDANCE).build()));
    }

    @Test
    public void hasSession_sessionInMemberDifferentCase_returnsTrue() {
        Member member = new MemberBuilder(ALICE).build();
        member.addSession(new SessionBuilder(MEETING).build());
        assertTrue(member.hasSession(new SessionBuilder(MEETING).withSessionName("meeting w1").build()));
    }

    @Test
    public void addSession_nullSession_throwsNullPointerException() {
        Member member = new MemberBuilder(ALICE).build();
        assertThrows(NullPointerException.class, () -> member.addSession(null));
    }

    @Test
    public void addSession_validSession_increasesSessions() {
        Member member = new MemberBuilder(ALICE).build();
        Session newSession = new Session(new SessionName(VALID_NAME_BOB),
                new SessionDate("16 Oct 2024"), new Point("10"));
        member.addSession(newSession);
        assertTrue(member.getSessions().contains(newSession));
        assertEquals(newSession.getPoints(), member.getTotalPoints());

        Session rehearsal = new SessionBuilder(REHEARSAL).build();
        member.addSession(rehearsal);
        assertTrue(member.getSessions().contains(rehearsal));
        assertEquals(rehearsal.getPoints().add(newSession.getPoints()),
                member.getTotalPoints());
    }

    @Test
    public void removeSession_nullSession_throwsNullPointerException() {
        Member member = new MemberBuilder(ALICE).build();
        assertThrows(NullPointerException.class, () -> member.removeSession(null));
    }

    @Test
    public void removeSession_validSession_decreasesSessions() {
        Member member = new MemberBuilder(ALICE).build();
        Session newSession = new Session(new SessionName(VALID_NAME_BOB),
                new SessionDate("10 Nov 2025"), new Point("10"));
        Session rehearsal = new SessionBuilder(REHEARSAL).build();
        member.addSession(rehearsal);
        member.addSession(newSession);
        member.removeSession(newSession.getSessionName());
        assertFalse(member.getSessions().contains(newSession));
        assertEquals(rehearsal.getPoints(), member.getTotalPoints());

        member.removeSession(rehearsal.getSessionName());
        assertFalse(member.getSessions().contains(rehearsal));
        assertEquals(new Point("0"), member.getTotalPoints());
    }

    @Test
    public void equals() {
        // same values -> returns true
        Member aliceCopy = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different member -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Member updatedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different telegram -> returns false
        updatedAlice = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different room -> returns false
        updatedAlice = new MemberBuilder(ALICE).withRoom(VALID_ROOM_BOB).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different tags -> returns false
        updatedAlice = new MemberBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(updatedAlice));

        // different sessions -> returns false
        updatedAlice = new MemberBuilder(ALICE)
                .withSessions(new SessionBuilder(REHEARSAL).build()).build();
    }

    @Test
    public void hashCode_remainsConsistentAcrossCalls() {
        Member member = new MemberBuilder(ALICE).build();

        // Ensure that hash code remains consistent for the same object across calls
        int initialHashCode = member.hashCode();
        assertTrue(initialHashCode == member.hashCode());
    }

    @Test
    public void toStringMethod() {
        String expected = Member.class.getCanonicalName() + "{name=" + ALICE.getName()
            + ", telegram=" + ALICE.getTelegram() + ", room=" + ALICE.getRoom()
            + ", tags=" + ALICE.getTags() + ", totalPoints=" + ALICE.getTotalPoints()
            + ", sessions=" + ALICE.getSessions() + "}";
        assertEquals(expected, ALICE.toString());
    }
}
