package hallpointer.address.model.member;

import static hallpointer.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_ROOM_BOB;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_SESSION_NAME_MEETING;
import static hallpointer.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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

        // same name and different telegram, all other attributes different -> returns true
        Member updatedAliceSameName = new MemberBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMember(updatedAliceSameName));

        // same telegram and different name, all other attributes different -> returns true
        Member updatedAliceSameTelegram = new MemberBuilder(ALICE).withName(VALID_NAME_BOB)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMember(updatedAliceSameTelegram));

        // same telegram and same name, all other attributes different -> returns true
        Member updatedAliceSameNameAndTelegram = new MemberBuilder(ALICE)
                .withRoom(VALID_ROOM_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameMember(updatedAliceSameNameAndTelegram));

        // different name and telegram, all other attributes same -> returns false
        Member updatedAlice = new MemberBuilder(ALICE).withName(VALID_NAME_BOB)
                .withTelegram(VALID_TELEGRAM_BOB).build();
        assertFalse(ALICE.isSameMember(updatedAlice));

        // name differs in case -> returns true
        Member updatedBob = new MemberBuilder(BOB).withName(VALID_NAME_BOB.toUpperCase()).build();
        assertTrue(BOB.isSameMember(updatedBob));

        // telegram differs in case -> returns true
        updatedBob = new MemberBuilder(BOB).withTelegram(VALID_TELEGRAM_BOB.toUpperCase()).build();
        assertTrue(BOB.isSameMember(updatedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        updatedBob = new MemberBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSameMember(updatedBob));
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
        assertTrue(member.hasSession(new SessionBuilder(MEETING)
                .withSessionName(VALID_SESSION_NAME_MEETING.toLowerCase())
                .build()));
    }

    @Test
    public void addSession_nullSession_throwsNullPointerException() {
        Member member = new MemberBuilder(ALICE).build();
        assertThrows(NullPointerException.class, () -> member.addSession(null));
    }

    @Test
    public void addSession_validSession_increasesSessions() {
        Member member = new MemberBuilder(ALICE).build();
        Session meeting = new SessionBuilder(MEETING).build();
        member.addSession(meeting);
        assertTrue(member.getSessions().contains(meeting));
        assertEquals(meeting.getPoints(), member.getTotalPoints());

        Session rehearsal = new SessionBuilder(REHEARSAL).build();
        member.addSession(rehearsal);
        assertTrue(member.getSessions().contains(rehearsal));
        assertEquals(rehearsal.getPoints().add(meeting.getPoints()),
                member.getTotalPoints());
    }

    @Test
    public void removeSession_nullSession_throwsNullPointerException() {
        Member member = new MemberBuilder(ALICE).build();
        assertThrows(NullPointerException.class, () -> member.removeSession(null));
    }

    @Test
    public void removeSession_sessionNotInMember_throwsIllegalArgumentException() {
        Member member = new MemberBuilder(ALICE).build();
        Session session = new SessionBuilder(MEETING).build();
        Session toBeRemoved = new SessionBuilder(REHEARSAL).build();

        assertThrows(IllegalArgumentException.class, () -> member.removeSession(toBeRemoved.getSessionName()));

        member.addSession(session);
        assertThrows(IllegalArgumentException.class, () -> member.removeSession(toBeRemoved.getSessionName()));
    }

    @Test
    public void removeSession_validSession_decreasesSessions() {
        Member member = new MemberBuilder(ALICE).build();
        Session newSession = new SessionBuilder(MEETING).build();
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
    public void equals_nullOrDifferentType_false() {
        assertFalse(ALICE.equals(null));
        assertFalse(ALICE.equals(5));
    }

    @Test
    public void equals_true() {
        // same values -> returns true
        Member aliceCopy = new MemberBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // same name up to case -> returns true
        Member bobDifferentName = new MemberBuilder(BOB).withName(VALID_NAME_BOB.toUpperCase()).build();
        assertTrue(BOB.equals(bobDifferentName));

        // same telegram up to case -> returns true
        Member bobDifferentTelegram = new MemberBuilder(BOB).withTelegram(VALID_TELEGRAM_BOB.toUpperCase()).build();
        assertTrue(BOB.equals(bobDifferentTelegram));
    }

    @Test
    public void equals_differentDetails_false() {
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
        assertFalse(ALICE.equals(updatedAlice));
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
