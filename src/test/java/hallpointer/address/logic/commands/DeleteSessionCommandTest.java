package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.GEORGE;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static hallpointer.address.testutil.TypicalSessions.MEETING;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.ModelStub;
import hallpointer.address.testutil.SessionBuilder;
import hallpointer.address.testutil.TypicalIndexes;
import javafx.collections.ObservableList;

/**
 * Test class for {@link DeleteSessionCommand}.
 */
public class DeleteSessionCommandTest {

    private final Session defaultSession = new Session(
            new SessionName("Rehearsal"),
            new SessionDate("24 Oct 2024"),
            new Point("10"));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSessionCommand(null, new HashSet<>()));
        assertThrows(NullPointerException.class, () -> new DeleteSessionCommand(new SessionName("Rehearsal"), null));
        assertThrows(NullPointerException.class, () -> new DeleteSessionCommand(null, null));
    }

    @Test
    public void constructor_emptyIndices_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new DeleteSessionCommand(
                new SessionName("Rehearsal"), new HashSet<>()));
    }

    @Test
    public void execute_validSessionDelete_success() throws Exception {
        ModelStubAcceptingMemberWithSession modelStub = new ModelStubAcceptingMemberWithSession();
        Member validMember = new MemberBuilder().withName("Alice").build();
        validMember.addSession(MEETING);
        validMember.addSession(defaultSession);
        modelStub.addMember(validMember);

        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(TypicalIndexes.INDEX_FIRST_MEMBER);
        SessionName sessionName = new SessionName("Rehearsal");

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(sessionName, memberIndexes);
        CommandResult commandResult = deleteSessionCommand.execute(modelStub);

        assertEquals(
                String.format(DeleteSessionCommand.MESSAGE_SUCCESS, sessionName, 1),
                commandResult.getFeedbackToUser()
        );
        assertEquals(validMember.getSessions().size(), 1); // check if session is deleted properly
        assertTrue(validMember.getSessions().contains(MEETING));
        assertFalse(validMember.getSessions().contains(defaultSession));
    }

    //@@author {yuvrajaryan}
    @Test
    public void execute_deleteSessionFromNonExistentMember_throwsCommandException() {
        ModelStubWithoutMembers modelStub = new ModelStubWithoutMembers();
        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(INDEX_FIRST_MEMBER); // Valid index, but no members exist

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                defaultSession.getSessionName(), memberIndexes);
        assertThrows(CommandException.class,
                DeleteSessionCommand.MESSAGE_INVALID_INDEX, () -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithMemberButNoSession();
        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(Index.fromZeroBased(5)); // Invalid index

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                new SessionName("Rehearsal"), memberIndexes);
        assertThrows(CommandException.class,
                DeleteSessionCommand.MESSAGE_INVALID_INDEX, () -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionNotInAnyMember_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithMemberButNoSession();
        Set<Index> memberIndexes = new HashSet<>();
        SessionName missingSessionName = new SessionName("NonExistentSession");
        memberIndexes.add(TypicalIndexes.INDEX_FIRST_MEMBER);

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                missingSessionName, memberIndexes);
        assertThrows(CommandException.class,
                String.format(DeleteSessionCommand.MESSAGE_SESSION_NOT_IN_MEMBER,
                        missingSessionName, modelStub.getFilteredMemberList().get(0).getName()), ()
                        -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionNotInAnySelectedMembers_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingMemberWithSession();
        modelStub.addMember(GEORGE); // has the REHEARSAL session
        modelStub.addMember(ALICE);
        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(TypicalIndexes.INDEX_SECOND_MEMBER);

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(REHEARSAL.getSessionName(), memberIndexes);
        assertThrows(CommandException.class,
                String.format(DeleteSessionCommand.MESSAGE_SESSION_NOT_IN_MEMBER,
                        REHEARSAL.getSessionName(), ALICE.getName()), ()
                        -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionNotInSomeSelectedMembers_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingMemberWithSession();
        modelStub.addMember(GEORGE); // has the REHEARSAL session
        modelStub.addMember(ALICE);
        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(TypicalIndexes.INDEX_FIRST_MEMBER);
        memberIndexes.add(TypicalIndexes.INDEX_SECOND_MEMBER);

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(REHEARSAL.getSessionName(), memberIndexes);
        assertThrows(CommandException.class,
                String.format(DeleteSessionCommand.MESSAGE_SESSION_NOT_IN_MEMBER,
                        REHEARSAL.getSessionName(), ALICE.getName()), ()
                        -> deleteSessionCommand.execute(modelStub));
        assertEquals(modelStub.getFilteredMemberList().size(), 2); // check if nothing was deleted
        assertTrue(modelStub.getFilteredMemberList().contains(GEORGE));
        assertTrue(modelStub.getFilteredMemberList().contains(ALICE));
    }

    @Test
    public void equals() {
        Set<Index> indexesAlice = new HashSet<>();
        Set<Index> indexesBob = new HashSet<>();
        indexesAlice.add(TypicalIndexes.INDEX_FIRST_MEMBER);
        indexesBob.add(TypicalIndexes.INDEX_SECOND_MEMBER);
        SessionName rehearsal = new SessionName("Rehearsal");
        SessionName exam = new SessionName("Exam");

        DeleteSessionCommand deleteRehearsalFromAliceCommand = new DeleteSessionCommand(rehearsal, indexesAlice);
        DeleteSessionCommand deleteRehearsalFromBobCommand = new DeleteSessionCommand(rehearsal, indexesBob);
        DeleteSessionCommand deleteExamFromAliceCommand = new DeleteSessionCommand(exam, indexesAlice);

        // same object -> returns true
        assertTrue(deleteRehearsalFromAliceCommand.equals(deleteRehearsalFromAliceCommand));

        // same values -> returns true
        DeleteSessionCommand deleteRehearsalFromAliceCommandCopy = new DeleteSessionCommand(rehearsal, indexesAlice);
        assertTrue(deleteRehearsalFromAliceCommand.equals(deleteRehearsalFromAliceCommandCopy));

        // different types -> returns false
        assertFalse(deleteRehearsalFromAliceCommand.equals(1));

        // null -> returns false
        assertFalse(deleteRehearsalFromAliceCommand.equals(null));

        // different member -> returns false
        assertFalse(deleteRehearsalFromAliceCommand.equals(deleteRehearsalFromBobCommand));

        // different session -> returns false
        assertFalse(deleteRehearsalFromAliceCommand.equals(deleteExamFromAliceCommand));
    }

    @Test
    public void toStringMethod() {
        Session session = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<Index>();
        indices.add(INDEX_FIRST_MEMBER);

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(session.getSessionName(), indices);
        String expected = DeleteSessionCommand.class.getCanonicalName()
                + "{delete=" + ATTENDANCE.getSessionName() + "}";
        assertEquals(expected, deleteSessionCommand.toString());
    }

    /**
     * A Model stub that contains no members.
     */
    private class ModelStubWithoutMembers extends ModelStub {
        // Override methods to return necessary default values or throw errors
        private final UniqueMemberList members = new UniqueMemberList();
        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }
    }

    /**
     * A Model stub that contains a member with no session.
     */
    private class ModelStubWithMemberButNoSession extends ModelStub {
        private final Member member = new MemberBuilder().withName("Alice").build();
        private final UniqueMemberList members = new UniqueMemberList();
        {
            members.add(member);
        }
        @Override
        public boolean hasMember(Member member) {
            return this.member.isSameMember(member);
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }
    }

    /**
     * A Model stub that contains a member with a session.
     */
    private class ModelStubAcceptingMemberWithSession extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();

        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }

        @Override
        public void setMember(Member target, Member updatedMember) {
            members.setMember(target, updatedMember);
        }
        @Override
        public void addMember(Member member) {
            // Simulate adding a member with a session
            members.add(member);
        }

    }
}
