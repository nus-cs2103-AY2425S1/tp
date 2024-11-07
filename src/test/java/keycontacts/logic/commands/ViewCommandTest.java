package keycontacts.logic.commands;

import static keycontacts.logic.commands.CommandTestUtil.VALID_DATE;
import static keycontacts.logic.commands.CommandTestUtil.assertCommandSuccess;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import keycontacts.model.Model;
import keycontacts.model.ModelManager;
import keycontacts.model.lesson.Date;

public class ViewCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_view_success() {
        Date date = new Date(VALID_DATE);
        CommandResult expectedCommandResult = new CommandResult(ViewCommand.MESSAGE_SUCCESS, false, date, false);
        assertCommandSuccess(new ViewCommand(date), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        String date1 = "01-01-2024";
        String date2 = "01-02-2024";
        ViewCommand viewFirstCommand = new ViewCommand(new Date(date1));
        ViewCommand viewSecondCommand = new ViewCommand(new Date(date2));

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewFirstCommandCopy = new ViewCommand(new Date(date1));
        assertTrue(viewFirstCommand.equals(viewFirstCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different student -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Date date = new Date(VALID_DATE);
        ViewCommand viewCommand = new ViewCommand(date);
        String expected = ViewCommand.class.getCanonicalName() + "{date=" + date + "}";
        assertEquals(expected, viewCommand.toString());
    }
}
