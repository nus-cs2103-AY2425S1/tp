package hallpointer.address.logic.commands;

import static hallpointer.address.testutil.Assert.assertThrows;
import static hallpointer.address.testutil.TypicalMembers.ALICE;
import static hallpointer.address.testutil.TypicalMembers.BOB;
import static hallpointer.address.testutil.TypicalSessions.ATTENDANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hallpointer.address.commons.core.index.Index;
import hallpointer.address.logic.commands.exceptions.CommandException;
import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.session.Session;
import hallpointer.address.testutil.MemberBuilder;
import hallpointer.address.testutil.SessionBuilder;
class AddSessionCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        // Initialize the model with typical members ALICE and BOB
        model = new ModelManager();
        model.addMember(new MemberBuilder(ALICE).build());
        model.addMember(new MemberBuilder(BOB).build());
    }

    @Test
    public void execute_addSessionToMultipleMembers_success() throws Exception {
        Session attendanceSession = new SessionBuilder(ATTENDANCE).build();
        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromZeroBased(0)); // Index of ALICE
        indices.add(Index.fromZeroBased(1)); // Index of BOB

        AddSessionCommand addSessionCommand = new AddSessionCommand(attendanceSession, indices);
        CommandResult commandResult = addSessionCommand.execute(model);

        String expectedMessage = String.format(
                AddSessionCommand.MESSAGE_SUCCESS,
                attendanceSession.getSessionName().sessionName,
                attendanceSession.getDate().fullDate,
                attendanceSession.getPoints().points,
                indices.size()
        );
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());

        Member updatedFirstMember = model.getFilteredMemberList().get(0);
        Member updatedSecondMember = model.getFilteredMemberList().get(1);

        assertTrue(updatedFirstMember.getSessions().contains(attendanceSession));
        assertTrue(updatedSecondMember.getSessions().contains(attendanceSession));
    }

    @Test
    public void execute_duplicateSession_throwsCommandException() {
        Session rehearsalSession = new SessionBuilder().withSessionName("Rehearsal").build();
        Member memberWithSession = new MemberBuilder().withSessions(rehearsalSession).build();
        model.setMember(model.getFilteredMemberList().get(0), memberWithSession);

        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromZeroBased(0)); // Index of memberWithSession

        AddSessionCommand addSessionCommand = new AddSessionCommand(rehearsalSession, indices);
        assertThrows(CommandException.class,
                AddSessionCommand.MESSAGE_DUPLICATE_SESSION, () -> addSessionCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Session newSession = new SessionBuilder().build();
        Set<Index> indices = new HashSet<>();
        indices.add(Index.fromZeroBased(2)); // Assuming only 2 members exist, so index 2 is invalid

        AddSessionCommand addSessionCommand = new AddSessionCommand(newSession, indices);
        assertThrows(CommandException.class, () -> addSessionCommand.execute(model));
    }
}
