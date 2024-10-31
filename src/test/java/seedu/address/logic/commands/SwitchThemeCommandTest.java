package seedu.address.logic.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SwitchThemeCommand.
 */
public class SwitchThemeCommandTest {
    @Test
    public void valid_toString() {
        String expectedString = "seedu.address.logic.commands.SwitchThemeCommand{theme=LIGHT}";
        SwitchThemeCommand command = new SwitchThemeCommand("LIGHT");

        assertEquals(expectedString, command.toString());
    }

    @Test
    public void sameCommandObject_equals() {
        SwitchThemeCommand currentCommand = new SwitchThemeCommand("light");
        SwitchThemeCommand otherCommand = currentCommand;

        assertEquals(currentCommand.equals(otherCommand), true);
    }

    @Test
    public void differentCommand_equals() {
        SwitchThemeCommand currentCommand = new SwitchThemeCommand("light");
        ListCommand listCommand = new ListCommand();

        assertFalse(currentCommand.equals(listCommand));
    }
}
