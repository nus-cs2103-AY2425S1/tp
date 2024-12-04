package seedu.address.logic.parser.clientcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.clientcommands.DeleteClientProfileCommand;

public class DeleteClientProfileCommandParserTest {

    private DeleteClientProfileCommandParser parser = new DeleteClientProfileCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteClientProfileCommand(INDEX_FIRST_LISTING));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, PREFIX_NAME.toString(), String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteClientProfileCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$$", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteClientProfileCommand.MESSAGE_USAGE));
    }
}
