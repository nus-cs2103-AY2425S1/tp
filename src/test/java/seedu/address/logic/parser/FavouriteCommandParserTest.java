package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.FavouriteCommand;

public class FavouriteCommandParserTest {
    private FavouriteCommandParser parser = new FavouriteCommandParser();
    @Test
    public void parse_validArgs_returnsFavouriteCommand() {
        ArrayList<Index> contactIndexes = new ArrayList<Index>();
        contactIndexes.add(INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "favourite c/ 1", new FavouriteCommand(contactIndexes));
    }
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }
}
