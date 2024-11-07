package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
