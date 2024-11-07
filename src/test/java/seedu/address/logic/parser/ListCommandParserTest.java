package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LIST_ARCHIVE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;


public class ListCommandParserTest {

    private static final String INVALID_COMMAND_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE);

    private static final String WHITESPACE = " ";

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArg_returnsListCommand() {
        assertParseSuccess(parser, "", ListCommand.ofCurrent());
        assertParseSuccess(parser, WHITESPACE, ListCommand.ofCurrent());
    }

    @Test
    public void parse_singleArgWithoutValue_returnsListCommand() {
        assertParseSuccess(parser, WHITESPACE + PREFIX_LIST_ALL, ListCommand.ofAll());
        assertParseSuccess(parser, WHITESPACE + PREFIX_LIST_ALL + WHITESPACE,
                ListCommand.ofAll());

        assertParseSuccess(parser, WHITESPACE + PREFIX_LIST_ARCHIVE,
                ListCommand.ofArchive());
        assertParseSuccess(parser, WHITESPACE + PREFIX_LIST_ARCHIVE + WHITESPACE,
                ListCommand.ofArchive());
    }

    @Test
    public void parse_singleArgWithValue_throwsParseException() {
        assertParseFailure(parser, WHITESPACE + PREFIX_LIST_ALL + WHITESPACE + "value",
                INVALID_COMMAND_FORMAT);
        assertParseFailure(parser, ListCommand.COMMAND_WORD + WHITESPACE + PREFIX_LIST_ARCHIVE + WHITESPACE + "value",
                INVALID_COMMAND_FORMAT);
    }

    @Test
    public void parse_multipleArgsWithoutValue_throwsParseException() {
        assertParseFailure(parser,
                WHITESPACE + PREFIX_LIST_ALL + WHITESPACE + PREFIX_LIST_ARCHIVE,
                INVALID_COMMAND_FORMAT);
    }
}
