package seedu.address.logic.parser.listingcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AREA;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REGION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listingcommands.EditListingCommand;
import seedu.address.logic.commands.listingcommands.EditListingCommand.EditListingDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class EditListingCommandParserTest {
    private static final String VALID_LISTING_NAME_ONE = "Woodlands Block 153";
    private static final String VALID_LISTING_NAME_TWO = "Woodlands Block 154";
    private static final String VALID_PRICE = "500000";
    private static final String VALID_AREA = "1200";
    private static final String VALID_ADDRESS = "Woodlands Avenue 6";
    private static final String VALID_REGION = "North";
    private static final String VALID_SELLER_NAME = "John Doe";
    private final EditListingCommandParser parser = new EditListingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = VALID_LISTING_NAME_ONE + " "
                + PREFIX_NAME + VALID_LISTING_NAME_TWO + " "
                + PREFIX_PRICE + VALID_PRICE + " "
                + PREFIX_AREA + VALID_AREA + " "
                + PREFIX_ADDRESS + VALID_ADDRESS + " "
                + PREFIX_REGION + VALID_REGION + " "
                + PREFIX_SELLER + VALID_SELLER_NAME;

        EditListingDescriptor descriptor = new EditListingDescriptor();
        descriptor.setName(new Name(VALID_LISTING_NAME_TWO));
        descriptor.setPrice(ParserUtil.parsePrice(VALID_PRICE));
        descriptor.setArea(ParserUtil.parseArea(VALID_AREA));
        descriptor.setAddress(ParserUtil.parseAddress(VALID_ADDRESS));
        descriptor.setRegion(ParserUtil.parseRegion(VALID_REGION));
        descriptor.setSellerName(new Name(VALID_SELLER_NAME));

        EditListingCommand expectedCommand =
                new EditListingCommand(new Name(VALID_LISTING_NAME_ONE), descriptor);

        EditListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_partialFieldsPresent_success() throws Exception {
        String userInput = VALID_LISTING_NAME_ONE + " "
                + PREFIX_PRICE + VALID_PRICE;

        EditListingDescriptor descriptor = new EditListingDescriptor();
        descriptor.setPrice(ParserUtil.parsePrice(VALID_PRICE));

        EditListingCommand expectedCommand =
                new EditListingCommand(new Name(VALID_LISTING_NAME_ONE), descriptor);

        EditListingCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingListingName_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD
                + PREFIX_PRICE + VALID_PRICE;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noFieldsEdited_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + VALID_LISTING_NAME_ONE;
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                EditListingCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(""),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_multipleNames_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_NAME + VALID_LISTING_NAME_TWO + " "
                + PREFIX_NAME + VALID_LISTING_NAME_TWO + " "
                + PREFIX_PRICE + VALID_PRICE;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrices_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_PRICE + VALID_PRICE + " "
                + PREFIX_PRICE + VALID_PRICE;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleAreas_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_AREA + VALID_AREA + " "
                + PREFIX_AREA + VALID_AREA;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleAddresses_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_ADDRESS + VALID_ADDRESS + " "
                + PREFIX_ADDRESS + VALID_ADDRESS;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleRegions_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_REGION + VALID_REGION + " "
                + PREFIX_REGION + VALID_REGION;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSellers_throwsParseException() {
        String userInput = EditListingCommand.COMMAND_WORD + " "
                + PREFIX_NAME + VALID_LISTING_NAME_ONE + " "
                + PREFIX_SELLER + VALID_SELLER_NAME + " "
                + PREFIX_SELLER + VALID_SELLER_NAME;

        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditListingCommand.MESSAGE_USAGE));
    }

}
