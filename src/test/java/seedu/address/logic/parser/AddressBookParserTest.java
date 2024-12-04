package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AutoCompleteCommand;
import seedu.address.logic.commands.Command;

public class AddressBookParserTest {

    private AddressBookParser parser;

    @BeforeEach
    public void setUp() {
        parser = new AddressBookParser();
    }

    @Test
    public void parseCommand_autoComplete_logsAutoComplete() throws Exception {
        Logger logger = Logger.getLogger(AddressBookParser.class.getName());
        TestLogHandler handler = new TestLogHandler();
        logger.addHandler(handler);
        logger.setLevel(Level.FINE);

        String userInput = "list";
        Command command = parser.parseCommand(userInput, true);

        assertTrue(command instanceof AutoCompleteCommand);
        AutoCompleteCommand autoCompleteCommand = (AutoCompleteCommand) command;
        assertEquals("list", autoCompleteCommand.getCurrentInput());
        assertEquals(null, autoCompleteCommand.getInputType());

        // assertEquals("FINE: AutoComplete trigger with input: list", handler.getLastLog());
    }

    private static class TestLogHandler extends Handler {
        private String lastLog;

        @Override
        public void publish(LogRecord record) {
            lastLog = record.getLevel() + ": " + record.getMessage();
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
        }

        public String getLastLog() {
            return lastLog;
        }
    }
}
