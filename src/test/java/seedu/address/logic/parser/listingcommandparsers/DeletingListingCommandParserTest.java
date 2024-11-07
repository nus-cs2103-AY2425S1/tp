package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LISTING;
import static seedu.address.testutil.TypicalListings.KENT_RIDGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listingcommands.DeleteListingCommand;

public class DeletingListingCommandParserTest {
    private DeleteListingCommandParser parser = new DeleteListingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", new DeleteListingCommand(INDEX_FIRST_LISTING));
    }

    @Test
    public void parse_outOfBoundsIndex_throwsParseException() {
        // Test an index out of bounds (example: 100)
        assertParseFailure(parser, "100", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$$", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteListingCommand.MESSAGE_USAGE));
    }
}
