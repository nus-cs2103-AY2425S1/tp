package hallpointer.address.logic.commands;

import static hallpointer.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static hallpointer.address.testutil.TypicalMembers.FIONA;
import static hallpointer.address.testutil.TypicalMembers.GEORGE;
import static hallpointer.address.testutil.TypicalMembers.getTypicalHallPointer;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import hallpointer.address.model.Model;
import hallpointer.address.model.ModelManager;
import hallpointer.address.model.UserPrefs;
import hallpointer.address.model.member.SessionContainsKeywordsPredicate;

public class FindSessionCommandTest {
    private Model expectedModel = new ModelManager(getTypicalHallPointer(), new UserPrefs());
    private Model model = new ModelManager(getTypicalHallPointer(), new UserPrefs());

    @Test
    public void execute_noMatchingSessions_noMatchesMessage() {
        String expectedMessage = FindSessionCommand.MESSAGE_NO_MATCHES;
        SessionContainsKeywordsPredicate predicate = new SessionContainsKeywordsPredicate(Arrays.asList("keyword"));
        FindSessionCommand command = new FindSessionCommand(predicate);
        expectedModel.updateFilteredMemberList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredMemberList());
    }

    @Test
    public void execute_matchingSessions_successMessage() {
        String expectedMessage = String.format(FindSessionCommand.MESSAGE_SUCCESS, "Rehearsal W1");
        SessionContainsKeywordsPredicate predicate = new SessionContainsKeywordsPredicate(Arrays.asList("rehearsal"));
        FindSessionCommand command = new FindSessionCommand(predicate);
        expectedModel.updateFilteredMemberList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), model.getFilteredMemberList());
    }

    @Test
    public void execute_multipleKeywords_multipleSessionsFound() {
        String sessionNames = "Meeting W1\nRehearsal W1\nMeeting W1";
        String expectedMessage = String.format(FindSessionCommand.MESSAGE_SUCCESS, sessionNames);
        SessionContainsKeywordsPredicate predicate =
                new SessionContainsKeywordsPredicate(Arrays.asList("rehearsal", "meeting"));
        FindSessionCommand command = new FindSessionCommand(predicate);
        expectedModel.updateFilteredMemberList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(FIONA, GEORGE), model.getFilteredMemberList());
    }
}
