package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandSummaryTest {
    @Test
    void testGetAction() {
        CommandSummary commandSummary = new CommandSummary("Add", "add n/NAME");
        assertEquals("Add", commandSummary.getAction());
    }

    @Test
    void testGetAction_throwsNullPointerException() {
        assertThrows(AssertionError.class, () -> new CommandSummary(null, "add n/NAME"));
    }

    @Test
    void testGetFormat() {
        CommandSummary commandSummary = new CommandSummary("Add", "add n/NAME");
        assertEquals("add n/NAME", commandSummary.getFormat());
    }

    @Test
    void testGetFormat_throwsNullPointerException() {
        assertThrows(AssertionError.class, () -> new CommandSummary("Add", null));
    }

}
