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

public class MarkAllTest {
    private Model model = new ModelManager(getTypicalEduLog(), new UserPrefs());

    @Test
    public void execute_successful() {
        MarkAllCommand markAllCommand = new MarkAllCommand();

        String expectedMessage = String.format(MarkAllCommand.MESSAGE_MARK_ALL_SUCCESS);

        Model expectedModel = new ModelManager(model.getEduLog(), new UserPrefs());
        expectedModel.markAllStudents();

        assertCommandSuccess(markAllCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void testEquals() {
        MarkAllCommand command1 = new MarkAllCommand();
        MarkAllCommand command2 = new MarkAllCommand();
        assertEquals(command1, command2);
    }

    @Test
    public void testNotEquals() {
        MarkAllCommand markAllCommand = new MarkAllCommand();
        DeleteIndexCommand deleteIndexCommand = new DeleteIndexCommand(Index.fromOneBased(1));
        assertNotEquals(markAllCommand, deleteIndexCommand);
    }

    @Test
    public void testToString() {
        MarkAllCommand markAllCommand = new MarkAllCommand();
        assertEquals(markAllCommand.toString(), MarkAllCommand.class.getCanonicalName() + "{}");
    }


}
