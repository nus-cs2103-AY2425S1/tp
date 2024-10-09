package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListPatientCommand;

/**
 * Contains unit tests for {@code ListPatientCommandParser}.
 */
public class ListPatientCommandParserTest {

    private ListPatientCommandParser parser = new ListPatientCommandParser();

    @Test
    public void parse_validArgs_returnsListPatientCommand() {
        // No arguments should parse successfully
        assertParseSuccess(parser, "", new ListPatientCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Any arguments should cause a parse failure
        assertParseFailure(parser, "unexpectedArgs",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPatientCommand.MESSAGE_USAGE));
    }
}
