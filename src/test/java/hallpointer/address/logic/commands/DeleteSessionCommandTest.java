package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.GuiSettings;
import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.ReadOnlyUserPrefs;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.UniqueMemberList;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.testutil.MemberBuilder;
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
    public void constructor_nullSessionName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSessionCommand(null, List.of()));
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSessionCommand(new SessionName("Rehearsal"), null));
    }

    @Test
    public void execute_validSessionDelete_success() throws Exception {
        ModelStubAcceptingMemberWithSession modelStub = new ModelStubAcceptingMemberWithSession();
        Member validMember = new MemberBuilder().withName("Alice").build();
        validMember.addSession(defaultSession);
        modelStub.addMember(validMember);

        List<Index> memberIndexes = Arrays.asList(TypicalIndexes.INDEX_FIRST_MEMBER);
        SessionName sessionName = new SessionName("Rehearsal");

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(sessionName, memberIndexes);
        CommandResult commandResult = deleteSessionCommand.execute(modelStub);

        assertEquals(
                String.format(DeleteSessionCommand.MESSAGE_DELETE_SESSION_SUCCESS, sessionName.toString(), 1),
                commandResult.getFeedbackToUser()
        );
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithoutMembers();
        List<Index> memberIndexes = Arrays.asList(Index.fromZeroBased(5)); // Invalid index

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                new SessionName("Rehearsal"), memberIndexes);
        assertThrows(CommandException.class,
                DeleteSessionCommand.MESSAGE_INVALID_INDEX, ()
                        -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void execute_sessionNotInMember_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithMemberButNoSession();
        List<Index> memberIndexes = Arrays.asList(TypicalIndexes.INDEX_FIRST_MEMBER);

        DeleteSessionCommand deleteSessionCommand = new DeleteSessionCommand(
                new SessionName("NonExistentSession"), memberIndexes);
        assertThrows(CommandException.class, () -> deleteSessionCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        List<Index> indexesAlice = Arrays.asList(TypicalIndexes.INDEX_FIRST_MEMBER);
        List<Index> indexesBob = Arrays.asList(TypicalIndexes.INDEX_SECOND_MEMBER);
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
    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
            throw new AssertionError("This method should not be called.");
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
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredMemberList(Predicate<Member> predicate) {
            throw new AssertionError("This method should not be called.");
        }
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
