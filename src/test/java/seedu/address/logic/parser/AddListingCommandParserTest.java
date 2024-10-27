package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.AREA_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.BUYER_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.PRICE_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.REGION_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.SELLER_DESC_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AREA_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIRST_BUYER_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REGION_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SECOND_BUYER_PASIR_RIS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SELLER_PASIR_RIS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SELLER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddListingCommand;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;

public class AddListingCommandParserTest {

    private AddListingCommandParser parser = new AddListingCommandParser();
    @Test
    public void parse_validArgs_returnsAddListingCommand() {
        String userInput = NAME_DESC_PASIR_RIS + PRICE_DESC_PASIR_RIS + AREA_DESC_PASIR_RIS
                + ADDRESS_DESC_PASIR_RIS + REGION_DESC_PASIR_RIS + SELLER_DESC_PASIR_RIS + BUYER_DESC_PASIR_RIS;

        assertParseSuccess(parser, userInput, new AddListingCommand(
                new Name(VALID_NAME_PASIR_RIS),
                new Price(VALID_PRICE_PASIR_RIS, new BigDecimal(VALID_PRICE_PASIR_RIS)),
                new Area(VALID_AREA_PASIR_RIS),
                new Address(VALID_ADDRESS_PASIR_RIS),
                Region.fromString(VALID_REGION_PASIR_RIS),
                new Name(VALID_SELLER_PASIR_RIS),
                new HashSet<Name>(Arrays.asList(new Name(VALID_FIRST_BUYER_PASIR_RIS),
                        new Name(VALID_SECOND_BUYER_PASIR_RIS)))));
    }

    @Test
    public void parse_missingField_throwsParseException() {
        // missing buyer
        String userInput = NAME_DESC_PASIR_RIS + PRICE_DESC_PASIR_RIS + AREA_DESC_PASIR_RIS
                + ADDRESS_DESC_PASIR_RIS + REGION_DESC_PASIR_RIS + SELLER_DESC_PASIR_RIS + " " + PREFIX_BUYER;

        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);

        // missing seller
        userInput = NAME_DESC_PASIR_RIS + PRICE_DESC_PASIR_RIS + AREA_DESC_PASIR_RIS
                + ADDRESS_DESC_PASIR_RIS + REGION_DESC_PASIR_RIS + " " + PREFIX_SELLER + " " + BUYER_DESC_PASIR_RIS;

        assertParseFailure(parser, userInput, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddListingCommand.MESSAGE_USAGE));
    }
}
