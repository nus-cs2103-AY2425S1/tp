package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.ViewCommand;

public class ViewCommandTest {

    @Test
    public void test_toString() {
        ViewCommand.ViewPersonDescriptor descriptor = new ViewCommand.ViewPersonDescriptor();
        ViewCommand viewCommand = new ViewCommand(descriptor);
        assertEquals("testing view command", viewCommand.toString());
    }
}