package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import seedu.address.testutil.TypicalPersons;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertEquals(commandResult, new CommandResult("feedback"));
        assertEquals(commandResult, new CommandResult("feedback", false, false));

        // same object -> returns true
        assertEquals(commandResult, commandResult);

        // null -> returns false
        assertNotEquals(null, commandResult);

        // different types -> returns false
        assertNotEquals(0.5f, commandResult, String.valueOf(0.0));

        // different feedbackToUser value -> returns false
        assertNotEquals(commandResult, new CommandResult("different"));

        // different showHelp value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", true, false));

        // different exit value -> returns false
        assertNotEquals(commandResult, new CommandResult("feedback", false, true));

    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());
    }

    @Test
    public void toStringMethod() {
        CommandResult commandResult = new CommandResult("feedback");
        String expected = CommandResult.class.getCanonicalName() + "{feedbackToUser="
                + commandResult.getFeedbackToUser() + ", showHelp=" + commandResult.isShowHelp()
                + ", exit=" + commandResult.isExit() + "}";
        assertEquals(expected, commandResult.toString());
    }

    @Test
    public void equalsHandleNUll() {
        CommandResult test = new CommandResult("feedback", null, false);
        assertEquals(test, new CommandResult("feedback", null, false));
        assertNotEquals(new CommandResult("feedback", new SimpleObjectProperty<>(TypicalPersons.ALICE), false), test);
    }

    @Test
    public void isViewTestFalse() {
        CommandResult test = new CommandResult("feedback", true, false);
        assertFalse(test.isView());
    }

    @Test
    public void isViewTestTrue() {
        CommandResult test = new CommandResult("feedback", new SimpleObjectProperty<>(TypicalPersons.ALICE), false);
        assertTrue(test.isView());
    }

    @Test
    public void getPersonTest() {
        CommandResult test = new CommandResult("feedback", new SimpleObjectProperty<>(TypicalPersons.ALICE), false);
        assertEquals(TypicalPersons.ALICE, test.getPerson().get());
    }
}
