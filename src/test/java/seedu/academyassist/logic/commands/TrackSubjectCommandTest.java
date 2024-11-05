package seedu.academyassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.academyassist.model.Model;
import seedu.academyassist.model.ModelManager;

/**
 * Contains unit tests for {@code TrackSubjectCommand}.
 */
public class TrackSubjectCommandTest {

    private Model model = new ModelManager();

    @Test
    public void execute_trackSubject_success() {
        CommandResult expectedCommandResult = new CommandResult(
                TrackSubjectCommand.SHOWING_SUBJECT_TRACKER_MESSAGE, false, false, true, false, null);
        TrackSubjectCommand trackCommand = new TrackSubjectCommand();
        CommandResult result = trackCommand.execute(model);

        assertEquals(expectedCommandResult, result);
        assertFalse(result.isShowHelp());
        assertFalse(result.isExit());
        assertTrue(result.isShowSubjectTracker());
        assertFalse(result.isShowDetailWindow());
        assertEquals(null, result.getPersonToShow());
    }

    @Test
    public void equals() {
        TrackSubjectCommand trackCommand = new TrackSubjectCommand();

        // same object -> returns true
        assertTrue(trackCommand.equals(trackCommand));

        // null -> returns false
        assertFalse(trackCommand.equals(null));

        // different types -> returns false
        assertFalse(trackCommand.equals(1));
    }
}
