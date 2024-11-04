package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
import javafx.collections.ObservableList;

public class DeleteSessionCommandIntegrationTest {

    private final Session defaultSession = new Session(
            new SessionName("Rehearsal"),
            new SessionDate("24 Oct 2024"),
            new Point("10"));

    @Test
    public void execute_deleteSessionFromMember_sessionDeleted() throws Exception {
        ModelStubAcceptingMemberWithSession modelStub = new ModelStubAcceptingMemberWithSession();
        Member member = new MemberBuilder().withName("Alice").build();
        member.addSession(defaultSession);
        modelStub.addMember(member);

        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(INDEX_FIRST_MEMBER);
        SessionName sessionName = defaultSession.getSessionName();

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(sessionName, memberIndexes);
        CommandResult commandResult = deleteSessionCommand.execute(modelStub);

        // Check that the session is deleted
        assertEquals(
                String.format(DeleteSessionCommand.MESSAGE_SUCCESS, sessionName.toString(), 1),
                commandResult.getFeedbackToUser()
        );
        assertFalse(member.getSessions().contains(defaultSession)); // Session should no longer exist
    }

    @Test
    public void execute_deleteSessionFromNonExistentMember_throwsCommandException() {
        ModelStubWithoutMembers modelStub = new ModelStubWithoutMembers();
        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(INDEX_FIRST_MEMBER); // Valid index, but no members exist

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                defaultSession.getSessionName(), memberIndexes);
        assertThrows(CommandException.class,
                DeleteSessionCommand.MESSAGE_INVALID_INDEX, ()
                        -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_deleteNonExistentSession_throwsCommandException() {
        ModelStubAcceptingMemberWithSession modelStub = new ModelStubAcceptingMemberWithSession();
        Member member = new MemberBuilder().withName("Alice").build();
        member.addSession(defaultSession);
        modelStub.addMember(member);

        Set<Index> memberIndexes = new HashSet<>();
        memberIndexes.add(INDEX_FIRST_MEMBER);
        SessionName sessionName = new SessionName("NonExistentSession"); // Session not added to member

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(sessionName, memberIndexes);
        assertThrows(CommandException.class, () -> deleteSessionCommand.execute(modelStub));
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

    /**
     * A Model stub that contains no members.
     */
    private class ModelStubWithoutMembers extends ModelStub {
        private final UniqueMemberList members = new UniqueMemberList();

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }
    }
}
