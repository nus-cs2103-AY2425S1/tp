package seedu.address.logic.parser.listingcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listingcommands.RemoveBuyersFromListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class RemoveBuyersFromListingCommandParserTest {
    private static final String VALID_LISTING_NAME = "Pasir Ris HDB";
    private static final String VALID_BUYER_NAME_1 = "Sean Dias";
    private static final String VALID_BUYER_NAME_2 = "Wen Xuan";
    private final RemoveBuyersFromListingCommandParser parser = new RemoveBuyersFromListingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = VALID_LISTING_NAME + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_1 + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_2;

        Set<Name> expectedBuyerNames = new HashSet<>();
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_1));
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_2));
        RemoveBuyersFromListingCommand expectedCommand = new RemoveBuyersFromListingCommand(
                new Name(VALID_LISTING_NAME), expectedBuyerNames);

        RemoveBuyersFromListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_multipleBuyers_success() throws Exception {
        String userInput = VALID_LISTING_NAME + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_1 + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_2;

        Set<Name> expectedBuyerNames = new HashSet<>();
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_1));
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_2));
        RemoveBuyersFromListingCommand expectedCommand = new RemoveBuyersFromListingCommand(
                new Name(VALID_LISTING_NAME), expectedBuyerNames);

        RemoveBuyersFromListingCommand result = parser.parse(userInput);
        assertTrue(expectedCommand.equals(result));
    }

    @Test
    public void parse_missingListingName_throwsParseException() {
        String userInput = RemoveBuyersFromListingCommand.COMMAND_WORD
                + PREFIX_BUYER + VALID_BUYER_NAME_1;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noBuyersSpecified_throwsParseException() {
        String userInput = RemoveBuyersFromListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveBuyersFromListingCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_emptyBuyerNames_throwsParseException() {
        String userInput = VALID_LISTING_NAME;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveBuyersFromListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }
}
