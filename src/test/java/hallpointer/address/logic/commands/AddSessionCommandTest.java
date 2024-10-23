package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static hallpointer.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.AddressBook;
import hallpointer.address.model.Model;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.session.Session;
import hallpointer.address.testutil.MemberBuilder;
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
    public void execute_duplicateSession_throwsCommandException() {
        Session validSession = new SessionBuilder().build();
        ModelStubWithMember modelStub = new ModelStubWithMember(new MemberBuilder().withSession(validSession).build());
        Set<Index> indices = new HashSet<>();
        indices.add(INDEX_FIRST_MEMBER);

        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession, indices);

        assertThrows(CommandException.class, () -> addSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromOneBased(2)); // Invalid index as there is only one member

        AddSessionCommand addSessionCommand = new AddSessionCommand(validSession, indices);

        assertThrows(CommandException.class, () -> addSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionAcceptedByModelMultipleMembers_addSuccessful() throws Exception {
        ModelStubAcceptingSessionAdded modelStub = new ModelStubAcceptingSessionAdded();
        Session validSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<>();
        Set<Session> resultSessions = new HashSet<>();

        indices.add(INDEX_FIRST_MEMBER);
        indices.add(Index.fromOneBased(2));
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
    public void equals() {
        Session session1 = new SessionBuilder().withSessionName("Session1").build();
        Session session2 = new SessionBuilder().withSessionName("Session2").build();
        Set<Index> indices1 = new HashSet<>();
        Set<Index> indices2 = new HashSet<>();
        indices1.add(INDEX_FIRST_MEMBER);
        indices2.add(INDEX_SECOND_MEMBER);

        AddSessionCommand addSessionCommand1 = new AddSessionCommand(session1, indices1);
        AddSessionCommand addSessionCommand2 = new AddSessionCommand(session2, indices2);

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
    }

    @Test
    public void toStringMethod() {
        AddMemberCommand addMemberCommand = new AddMemberCommand(ALICE);
        String expected = AddMemberCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addMemberCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        private final UniqueMemberList members = new UniqueMemberList();
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMember(Member member) {
            members.add(member);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasMember(Member member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMember(Member target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setMember(Member target, Member updatedMember) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Member> getFilteredMemberList() {
            return members.asUnmodifiableObservableList();
        }

        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that always accept the session being added.
     */
    private class ModelStubAcceptingSessionAdded extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();
        final ArrayList<Session> sessionsAdded = new ArrayList<>();

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

    private class ModelStubWithMember extends ModelStub {
        final UniqueMemberList members = new UniqueMemberList();

        public ModelStubWithMember(Member member) {
            members.add(member);
        }
        @Override public void addMember(Member member) {
            members.add(member);
        }
    }
}
