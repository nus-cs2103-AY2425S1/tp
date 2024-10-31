package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandInfoTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CommandInfo(null));
    }

    @Test
    public void getUsage_withSameUsageString_isEqual() {
        String usage = "abcd";
        CommandInfo commandInfo = new CommandInfo(usage);
        assertTrue(commandInfo.getUsage().equals(usage));

        String otherUsage = "dcba";
        assertFalse(commandInfo.getUsage().equals(otherUsage));
    }

    @Test
    public void equals() {
        CommandInfo commandInfo = new CommandInfo("abcd");

        assertTrue(commandInfo.equals(new CommandInfo("abcd")));
        assertTrue(commandInfo.equals(commandInfo));
        assertFalse(commandInfo.equals(null));
        assertFalse(commandInfo.equals(new CommandInfo("F9876543E")));
    }
}
