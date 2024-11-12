package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_WEDDING;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeletewCommand;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;

public class DeletewCommandParserTest {
    private DeletewCommandParser parser = new DeletewCommandParser();

    @Test
    public void parse_validArgs_returnsDeletewCommand() {
        assertParseSuccess(parser, "1", new DeletewCommand(INDEX_FIRST_WEDDING, null));

        DeletewCommand expectedDeletewCommand =
                new DeletewCommand(null, new NameMatchesWeddingPredicate(Arrays.asList("Alice", "Bob")));

        assertParseSuccess(parser, "Alice Bob", expectedDeletewCommand);

        assertParseSuccess(parser, " \n Alice Bob  \t", expectedDeletewCommand);

        assertParseSuccess(parser, " \n Alice       Bob  \t", expectedDeletewCommand);
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletewCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletewCommand.MESSAGE_USAGE));
    }

}
