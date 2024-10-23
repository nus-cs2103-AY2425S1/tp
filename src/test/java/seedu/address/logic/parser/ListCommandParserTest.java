package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand(null, false));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // sort by name
        assertParseSuccess(parser, " s/name", new ListCommand("name", false));

        // sort by email
        assertParseSuccess(parser, " s/email", new ListCommand("email", false));

        // sort by name with reverse
        assertParseSuccess(parser, " s/name r/", new ListCommand("name", true));

        // sort by email with reverse
        assertParseSuccess(parser, " s/email r/", new ListCommand("email", true));

        // case insensitive sort field
        assertParseSuccess(parser, " s/NAME", new ListCommand("name", false));
        assertParseSuccess(parser, " s/EmAiL", new ListCommand("email", false));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid sort field
        assertParseFailure(parser, " s/invalid", ListCommand.MESSAGE_INVALID_SORT_FIELD);

        // reverse without sort field
        assertParseFailure(parser, " r/", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        // invalid prefix
        assertParseFailure(parser, " x/name", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));

        // multiple sort fields
        assertParseFailure(parser, " s/name s/email", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListCommand.MESSAGE_USAGE));

        // multiple reverse flags
        assertParseFailure(parser, " s/name r/ r/", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            ListCommand.MESSAGE_USAGE));
    }
}
