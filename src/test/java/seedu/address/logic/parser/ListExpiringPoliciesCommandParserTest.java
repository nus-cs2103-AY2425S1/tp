package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListExpiringPoliciesCommandParserTest {

    private final ListExpiringPoliciesCommandParser parser = new ListExpiringPoliciesCommandParser();

    @Test
    public void parse_validArgs_returnsListExpiringPoliciesCommand() throws Exception {
        // valid usage of the command with no arguments
        assertTrue(parser.parse("") instanceof ListExpiringPoliciesCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid usage where extra arguments are provided
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE), () -> parser.parse("extraArgument"));
    }
}
