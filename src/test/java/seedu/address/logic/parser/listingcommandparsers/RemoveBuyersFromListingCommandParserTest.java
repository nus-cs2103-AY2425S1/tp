package seedu.address.logic.parser.listingcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.listingcommands.RemoveBuyersFromListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;


public class RemoveBuyersFromListingCommandParserTest {
    private static final String VALID_LISTING_INDEX = "1";
    private static final String INVALID_LISTING_INDEX = "-1";
    private static final String VALID_BUYER_INDEX_1 = "1";
    private static final String VALID_BUYER_INDEX_2 = "3";
    private static final String INVALID_BUYER_INDEX = "-1";
    private final RemoveBuyersFromListingCommandParser parser = new RemoveBuyersFromListingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = VALID_LISTING_INDEX + " "
                + PREFIX_BUYER + VALID_BUYER_INDEX_1 + " "
                + PREFIX_BUYER + VALID_BUYER_INDEX_2;

        Set<Index> expectedBuyerIndexes = new HashSet<>();
        expectedBuyerIndexes.add(Index.fromOneBased(Integer.parseInt(VALID_BUYER_INDEX_1)));
        expectedBuyerIndexes.add(Index.fromOneBased(Integer.parseInt(VALID_BUYER_INDEX_2)));
        RemoveBuyersFromListingCommand expectedCommand =
                new RemoveBuyersFromListingCommand(Index.fromOneBased(Integer.parseInt(VALID_LISTING_INDEX)),
                        expectedBuyerIndexes);

        RemoveBuyersFromListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingListingIndex_throwsParseException() {
        String userInput = RemoveBuyersFromListingCommand.COMMAND_WORD + " "
                + PREFIX_BUYER + VALID_BUYER_INDEX_1;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidListingIndex_throwsParseException() {
        String userInput = INVALID_LISTING_INDEX + " " + PREFIX_BUYER + VALID_BUYER_INDEX_1;
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_noBuyersSpecified_throwsParseException() {
        String userInput = RemoveBuyersFromListingCommand.COMMAND_WORD + " "
                + VALID_LISTING_INDEX;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingBuyerIndex_throwsParseException() {
        String userInput = VALID_LISTING_INDEX + " " + PREFIX_BUYER;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveBuyersFromListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_invalidBuyerIndex_throwsParseException() {
        String userInput = RemoveBuyersFromListingCommand.COMMAND_WORD + " "
                + VALID_LISTING_INDEX + " "
                + PREFIX_BUYER + INVALID_BUYER_INDEX;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyBuyerIndex_throwsParseException() {
        String userInput = VALID_LISTING_INDEX;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveBuyersFromListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }
}
