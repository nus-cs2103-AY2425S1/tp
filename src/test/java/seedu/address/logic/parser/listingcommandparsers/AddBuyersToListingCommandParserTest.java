package seedu.address.logic.parser.listingcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listingcommands.AddBuyersToListingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class AddBuyersToListingCommandParserTest {
    private static final String VALID_LISTING_NAME = "Kent Ridge Condo";
    private static final String VALID_BUYER_NAME_1 = "John Doe";
    private static final String VALID_BUYER_NAME_2 = "Jane Smith";
    private static final String INVALID_BUYER_NAME = "";
    private final AddBuyersToListingCommandParser parser = new AddBuyersToListingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = VALID_LISTING_NAME + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_1 + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_2;

        Set<Name> expectedBuyerNames = new HashSet<>();
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_1));
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_2));
        AddBuyersToListingCommand expectedCommand =
                new AddBuyersToListingCommand(new Name(VALID_LISTING_NAME), expectedBuyerNames);

        AddBuyersToListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingListingName_throwsParseException() {
        String userInput = AddBuyersToListingCommand.COMMAND_WORD
                + PREFIX_BUYER + VALID_BUYER_NAME_1;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBuyersToListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_missingBuyerNames_throwsParseException() {
        String userInput = AddBuyersToListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBuyersToListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_invalidBuyerName_throwsParseException() {
        String userInput = AddBuyersToListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME + " "
                + PREFIX_BUYER + INVALID_BUYER_NAME;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_multipleBuyersPresent_success() throws Exception {
        String userInput = VALID_LISTING_NAME + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_1 + " "
                + PREFIX_BUYER + VALID_BUYER_NAME_2;

        Set<Name> expectedBuyerNames = new HashSet<>();
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_1));
        expectedBuyerNames.add(new Name(VALID_BUYER_NAME_2));
        AddBuyersToListingCommand expectedCommand =
                new AddBuyersToListingCommand(new Name(VALID_LISTING_NAME), expectedBuyerNames);

        AddBuyersToListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }
    @Test
    public void parse_emptyListingName_throwsParseException() {
        String userInput = " "
                + PREFIX_BUYER + VALID_BUYER_NAME_1;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBuyersToListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_emptyBuyerNames_throwsParseException() {
        String userInput = VALID_LISTING_NAME;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBuyersToListingCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }
}
