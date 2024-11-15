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

    private void assertParseSuccess(String userInput, int expectedDays) throws Exception {
        ListExpiringPoliciesCommand command = parser.parse(userInput);
        assertTrue(command instanceof ListExpiringPoliciesCommand);
        assertEquals(expectedDays, command.getDaysFromExpiry());
    }

    private void assertParseFailure(String userInput, String expectedMessage) {
        assertThrows(ParseException.class, expectedMessage, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noArgs_returnsListExpiringPoliciesCommandWithDefaultDays() throws Exception {
        assertParseSuccess("", 30);
    }

    @Test
    public void parse_validArgs_returnsListExpiringPoliciesCommand() throws Exception {
        assertParseSuccess(" 60", 60);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE);

        // non-integer input
        assertParseFailure(" d/abc", expectedMessage);
        // zero days
        assertParseFailure(" d/0", expectedMessage);
        // negative days
        assertParseFailure(" d/-5", expectedMessage);
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListExpiringPoliciesCommand.MESSAGE_USAGE);
        // extra arguments after valid input
        assertParseFailure(" d/60 extraArgument", expectedMessage);
    }
}
