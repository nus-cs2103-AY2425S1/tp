package seedu.edulog.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.edulog.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.edulog.testutil.TypicalEdulog.getTypicalEduLog;

import org.junit.jupiter.api.Test;

import seedu.edulog.commons.core.index.Index;
import seedu.edulog.model.Model;
import seedu.edulog.model.ModelManager;
import seedu.edulog.model.UserPrefs;

public class UnmarkAllTest {
    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_successful() {
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand();

        String expectedMessage = String.format(UnmarkAllCommand.MESSAGE_UNMARK_ALL_SUCCESS);

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.unmarkAllStudents();

        assertCommandSuccess(unmarkAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEquals() {
        UnmarkAllCommand command1 = new UnmarkAllCommand();
        UnmarkAllCommand command2 = new UnmarkAllCommand();
        assertEquals(command1, command2);
    }

    @Test
    public void testNotEquals() {
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand();
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(Index.fromOneBased(1));
        assertNotEquals(unmarkAllCommand, deleteIndexCommand);
    }

    @Test
    public void testToString() {
        UnmarkAllCommand unmarkAllCommand = new UnmarkAllCommand();
        assertEquals(unmarkAllCommand.toString(), UnmarkAllCommand.class.getCanonicalName() + "{}");
    }


}
