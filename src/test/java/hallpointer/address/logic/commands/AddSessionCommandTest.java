package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_THIRD_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static hallpointer.address.testutil.TypicalMembers.CARL;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static hallpointer.address.testutil.TypicalSessions.REHEARSAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.session.Session;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.ModelStub;
import hallpointer.address.testutil.SessionBuilder;
import javafx.collections.ObservableList;

class AddSessionCommandTest {

    @Test
    public void constructor_nullSession_throwsNullPointerException() {
        Session session = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<Index>();
        indices.add(INDEX_FIRST_MEMBER);

        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null, indices));
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(session, null));
        assertThrows(NullPointerException.class, () -> new AddSessionCommand(null, null));
    }

    @Test
    public void constructor_emptyIndices_throwsAssertionError() {
        Session session = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<Index>();

        assertThrows(AssertionError.class, () -> new AddSessionCommand(session, indices));
    }

    @Test
    public void execute_sessionAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<Index>();
        Set<Session> resultSessions = new HashSet<>();

        indices.add(INDEX_FIRST_MEMBER);
        modelStub.addMember(new MemberBuilder().build());
        CommandResult commandResult = new AddSessionCommand(validSession, indices).execute(modelStub);

        assertEquals(
                String.format(
                        AddSessionCommand.MESSAGE_SUCCESS,
                        validSession.getSessionName().sessionName,
                        validSession.getDate().fullDate,
                        validSession.getPoints().points,
                        indices.size()
                ),
                commandResult.getFeedbackToUser()
        );
        resultSessions.add(validSession);
        assertEquals(Collections.unmodifiableSet(resultSessions),
                modelStub.getFilteredMemberList().get(0).getSessions());
    }

    @Test
    public void execute_sessionAcceptedByModelMultipleMembers_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<>();
        Set<Session> resultSessions = new HashSet<>();

        indices.add(INDEX_FIRST_MEMBER);
        indices.add(INDEX_SECOND_MEMBER);
        modelStub.addMember(new MemberBuilder().build());
        modelStub.addMember(new MemberBuilder().withName("Bob").build());

        CommandResult commandResult = new AddSessionCommand(validSession, indices).execute(modelStub);

        assertEquals(
                String.format(
                        AddSessionCommand.MESSAGE_SUCCESS,
                        validSession.getSessionName().sessionName,
                        validSession.getDate().fullDate,
                        validSession.getPoints().points,
                        indices.size()
                ),
                commandResult.getFeedbackToUser()
        );
        resultSessions.add(validSession);
        assertEquals(Collections.unmodifiableSet(resultSessions),
                modelStub.getFilteredMemberList().get(0).getSessions());
        assertEquals(Collections.unmodifiableSet(resultSessions),
                modelStub.getFilteredMemberList().get(1).getSessions());
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        Session validSession = new SessionBuilder(REHEARSAL).build();
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        modelStub.addMember(new MemberBuilder().withSessions(validSession).build());
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_MEMBER);

        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession, indices);
        assertThrows(CommandException.class,
                AddSessionCommand.MESSAGE_DUPLICATE_SESSION, () -> addSessionCommand.execute(modelStub));

        // not case-sensitive
        Session modifiedSession = new SessionBuilder(REHEARSAL).withSessionName("Rehearsal w1").build();
        AddSessionCommand modifiedAddSessionCommand = new AddSessionCommand(modifiedSession, indices);
        assertThrows(CommandException.class,
                AddSessionCommand.MESSAGE_DUPLICATE_SESSION, () -> modifiedAddSessionCommand.execute(modelStub));

        // one fails means all fails and nothing happens
        modelStub.addMember(new MemberBuilder(BOB).build());
        modelStub.addMember(new MemberBuilder(CARL).withSessions(validSession).build());
        indices = new HashSet<>();
        indices.add(INDEX_SECOND_MEMBER);
        indices.add(INDEX_THIRD_MEMBER);
        AddSessionCommand newAddSessionCommand = new AddSessionCommand(validSession, indices);
        assertThrows(CommandException.class,
                AddSessionCommand.MESSAGE_DUPLICATE_SESSION, () -> newAddSessionCommand.execute(modelStub));
        assertTrue(modelStub.getFilteredMemberList().get(1).getSessions().isEmpty());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_THIRD_MEMBER); // Invalid index as there is only one member

        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession, indices);
        assertThrows(CommandException.class, () -> addSessionCommand.execute(modelStub));

        // all must be valid
        indices = new HashSet<>();
        indices.add(INDEX_FIRST_MEMBER);
        indices.add(Index.fromOneBased(90));
        modelStub.addMember(new MemberBuilder().build());

        AddSessionCommand modifiedAddSessionCommand = new AddSessionCommand(validSession, indices);
        assertThrows(CommandException.class, () -> modifiedAddSessionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Session session1 = new SessionBuilder().withSessionName("Session1").build();
        Session session2 = new SessionBuilder().withSessionName("Session2").build();
        Set<Index> indices1 = new HashSet<>();
        Set<Index> indices2 = new HashSet<>();
        Set<Index> indices3 = new HashSet<>();
        Set<Index> indices4 = new HashSet<>();
        indices1.add(INDEX_FIRST_MEMBER);
        indices2.add(INDEX_SECOND_MEMBER);
        indices3.add(INDEX_THIRD_MEMBER);
        indices4.add(INDEX_THIRD_MEMBER);
        indices4.add(INDEX_SECOND_MEMBER);

        AddSessionCommand addSessionCommand1 = new AddSessionCommand(session1, indices1);
        AddSessionCommand addSessionCommand2 = new AddSessionCommand(session2, indices2);
        AddSessionCommand addSessionCommand3 = new AddSessionCommand(session1, indices3);
        AddSessionCommand addSessionCommand4 = new AddSessionCommand(session1, indices4);


        // same object -> returns true
        assertTrue(addSessionCommand1.equals(addSessionCommand1));

        // same values -> returns true
        AddSessionCommand addSessionCommand1Copy = new AddSessionCommand(session1, indices1);
        assertTrue(addSessionCommand1.equals(addSessionCommand1Copy));

        // different types -> returns false
        assertFalse(addSessionCommand1.equals(1));

        // null -> returns false
        assertFalse(addSessionCommand1.equals(null));

        // different session -> returns false
        assertFalse(addSessionCommand1.equals(addSessionCommand2));

        // different indices -> returns false
        assertFalse(addSessionCommand3.equals(addSessionCommand4));
        assertFalse(addSessionCommand4.equals(addSessionCommand3));
        assertFalse(addSessionCommand1.equals(addSessionCommand3));
    }

    @Test
    public void toStringMethod() {
        Session session = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<Index>();
        indices.add(INDEX_FIRST_MEMBER);

        AddSessionCommand addSessionCommand = new AddSessionCommand(session, indices);
        String expected = AddSessionCommand.class.getCanonicalName() + "{toAdd=" + ATTENDANCE + "}";
        assertEquals(expected, addSessionCommand.toString());
    }

    /**
     * A Model stub that always accept the session being added (implicitly requires Member acceptance too).
     */
    private class ModelStubAcceptingSessionAdded extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();

        @Override
        public void addMember(Member member) {
            members.add(member);
        }

        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }
        @Override
        public void setMember(Member target, Member updatedMember) {
            members.setMember(target, updatedMember);
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
