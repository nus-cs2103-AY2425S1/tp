package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListExpiringPoliciesCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ListExpiringPoliciesCommandParserTest {

    private final ListExpiringPoliciesCommandParser parser = new ListExpiringPoliciesCommandParser();

    @Test
    public void parse_noArgs_returnsListExpiringPoliciesCommandWithDefaultDays() throws Exception {
        // test that an empty argument defaults to 30 days
        ListExpiringPoliciesCommand command = parser.parse("");
        assertTrue(command instanceof ListExpiringPoliciesCommand);
        // check that the default value of 30 days is used
        assertEquals(30, command.getDaysFromExpiry());
    }

    @Test
    public void parse_validArgs_returnsListExpiringPoliciesCommand() throws Exception {
        // test a valid argument with a number of days (eg. 60)
        ListExpiringPoliciesCommand command = parser.parse(" 60");
        assertTrue(command instanceof ListExpiringPoliciesCommand);
        assertEquals(60, command.getDaysFromExpiry());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // test invalid arguments like non-integer input (eg. d/abc)
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE), () -> parser.parse(" d/abc"));

        // test invalid arguments with negative or zero days (eg. d/0, d/-5)
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE), () -> parser.parse(" d/0"));

        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE), () -> parser.parse(" d/-5"));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        // test extra arguments provided after valid input
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE), () -> parser.parse(" d/60 extraArgument"));
    }

}
