package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
class CloseWindowCommandTest {

    private Model model;
    private CloseWindowCommand closeWindowCommand;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        closeWindowCommand = new CloseWindowCommand();
    }

    @BeforeAll
    public static void initJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            try {
                Platform.startup(() -> {});
                Platform.setImplicitExit(false);
            } catch (IllegalStateException e) {
                // JavaFX is already initialized, no need to do anything here
            }
        }
    }

    @AfterAll
    public static void closeJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.exit();
        }
    }

    @Test
    public void execute_closeWindowWhenNoWindow_failure() {
        // Ensure no AttendanceWindow is currently open
        GetAttendanceByTgCommand.closeCurrentWindow();

        // Execute CloseWindowCommand
        CommandResult result = closeWindowCommand.execute(model);

        // Verify that the correct message is returned when no window is open
        assertEquals(new CommandResult(CloseWindowCommand.NO_WINDOW), result);
    }

    @Test
    public void equals() {
        CloseWindowCommand closeWindowCommand1 = new CloseWindowCommand();
        CloseWindowCommand closeWindowCommand2 = new CloseWindowCommand();

        // Same object -> returns true
        assertTrue(closeWindowCommand1.equals(closeWindowCommand1));

        // Different object, same type -> returns true
        assertTrue(closeWindowCommand1.equals(closeWindowCommand2));

        // Different type -> returns false
        assertFalse(closeWindowCommand1.equals(new GetAttendanceByTgCommand(null)));
    }
}
