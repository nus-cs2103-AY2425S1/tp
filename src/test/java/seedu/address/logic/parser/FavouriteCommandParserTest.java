package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE);

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_indexSpecified_success() {
        // valid index provided
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased());
        FavouriteCommand expectedCommand = new FavouriteCommand(targetIndex);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyInput_success() {
        // no index provided, should trigger sort by favourite
        String userInput = "";
        FavouriteCommand expectedCommand = new FavouriteCommand();
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidInput_failure() {
        // non-numeric input
        String userInput = "a";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // negative index
        userInput = "-1";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // extra characters
        userInput = "1 abc";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }
}
