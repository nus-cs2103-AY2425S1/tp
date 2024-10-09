package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListDoctorCommand;

/**
 * Contains unit tests for {@code ListDoctorCommandParser}.
 */
public class ListDoctorCommandParserTest {

    private ListDoctorCommandParser parser = new ListDoctorCommandParser();

    @Test
    public void parse_validArgs_returnsListDoctorCommand() {
        // No arguments should parse successfully
        assertParseSuccess(parser, "", new ListDoctorCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Any arguments should cause a parse failure
        assertParseFailure(parser, "unexpectedArgs",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListDoctorCommand.MESSAGE_USAGE));
    }
}
