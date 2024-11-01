package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalListings.KENT_RIDGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteListingCommand;

public class DeletingListingCommandParserTest {
    private DeleteListingCommandParser parser = new DeleteListingCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, PREFIX_NAME + "Kent Ridge Condo",
                new DeleteListingCommand(KENT_RIDGE.getName()));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        assertParseFailure(parser, PREFIX_NAME.toString(), Messages.MISSING_LISTING_NAME);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteListingCommand.MESSAGE_USAGE));
    }
}
