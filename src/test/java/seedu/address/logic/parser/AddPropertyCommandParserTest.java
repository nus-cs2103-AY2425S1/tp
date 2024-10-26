package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPropertyCommand;

/**
 * Test cases for AddPropertyCommandParser.
 */
public class AddPropertyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddPropertyCommand.MESSAGE_USAGE);

    private AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No index provided
        assertParseFailure(parser, PROPERTY_ADDRESS_DESC_AMY + TOWN_DESC_AMY + TYPE_DESC_AMY +
                SIZE_DESC_AMY + BEDROOMS_DESC_AMY + BATHROOMS_DESC_AMY + PRICE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // No fields provided
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // No index and fields
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // Negative index
        assertParseFailure(parser, "-1" + PROPERTY_ADDRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // Zero index
        assertParseFailure(parser, "0" + PROPERTY_ADDRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // Random text as preamble
        assertParseFailure(parser, "abc" + PROPERTY_ADDRESS_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // Invalid prefix in preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid size
        String expectedMessage = String.format(ParserUtil.MESSAGE_INVALID_DOUBLE, "invalidSize");
        assertParseFailure(parser, "1" + PROPERTY_ADDRESS_DESC_AMY + TOWN_DESC_AMY + TYPE_DESC_AMY +
                        " " + PREFIX_SIZE + "invalidSize" + BEDROOMS_DESC_AMY + BATHROOMS_DESC_AMY + PRICE_DESC_AMY,
                expectedMessage);

        // Invalid price
        String expectedPriceMessage = String.format(ParserUtil.MESSAGE_INVALID_DOUBLE, "invalidPrice");
        assertParseFailure(parser, "1" + PROPERTY_ADDRESS_DESC_AMY + TOWN_DESC_AMY + TYPE_DESC_AMY +
                        SIZE_DESC_AMY + BEDROOMS_DESC_AMY + BATHROOMS_DESC_AMY + " " + PREFIX_PRICE + "invalidPrice",
                expectedPriceMessage);
    }


    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PROPERTY_ADDRESS_DESC_AMY +
                TOWN_DESC_AMY + TYPE_DESC_AMY + SIZE_DESC_AMY + BEDROOMS_DESC_AMY +
                BATHROOMS_DESC_AMY + PRICE_DESC_AMY;

        AddPropertyCommand expectedCommand = new AddPropertyCommand(targetIndex,
                VALID_PROPERTY_ADDRESS_AMY, VALID_TOWN_AMY, VALID_TYPE_AMY,
                Double.parseDouble(VALID_SIZE_AMY), Integer.parseInt(VALID_BEDROOMS_AMY),
                Integer.parseInt(VALID_BATHROOMS_AMY), Double.parseDouble(VALID_PRICE_AMY));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
