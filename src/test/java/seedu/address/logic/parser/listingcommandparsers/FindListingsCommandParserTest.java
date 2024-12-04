package seedu.address.logic.parser.listingcommandparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listingcommands.FindListingCommand;
import seedu.address.model.listing.ListingContainsKeywordsPredicate;

public class FindListingsCommandParserTest {
    private FindListingsCommandParser parser = new FindListingsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindListingCommand expectedFindCommand =
                new FindListingCommand(
                        new ListingContainsKeywordsPredicate(Arrays.asList("Woodlands", "Marsiling")));
        assertParseSuccess(parser, "Woodlands Marsiling", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Woodlands \n \t Marsiling  \t", expectedFindCommand);
    }
}
