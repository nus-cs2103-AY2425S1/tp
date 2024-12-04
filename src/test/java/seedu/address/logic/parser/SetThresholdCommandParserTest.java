package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_SYNTAX;
import static seedu.address.logic.Messages.MESSAGE_MISSING_REQUIRED_PREFIXES;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAX_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MIN_STOCK_LEVEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.SetThresholdCommandParser.MESSAGE_INVALID_THRESHOLD;
import static seedu.address.logic.parser.SetThresholdCommandParser.MESSAGE_MISSING_THRESHOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SetThresholdCommand;
import seedu.address.model.product.ProductName;

public class SetThresholdCommandParserTest {
    private SetThresholdCommandParser parser;
    private ProductName validProductName;
    private Integer validMinStockLevel;
    private Integer validMaxStockLevel;

    @BeforeEach
    public void setUp() {
        parser = new SetThresholdCommandParser();
        validProductName = new ProductName("ValidProduct");
        validMinStockLevel = 50;
        validMaxStockLevel = 200;
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // Both min/ and max/ provided
        assertParseSuccess(parser, " pr/Product1 min/50 max/200",
                new SetThresholdCommand(new ProductName("Product1"), 50, 200));

        // Only min/ provided
        assertParseSuccess(parser, " pr/Product1 min/50",
                new SetThresholdCommand(new ProductName("Product1"), 50, null));

        // Only max/ provided
        assertParseSuccess(parser, " pr/Product1 max/200",
                new SetThresholdCommand(new ProductName("Product1"), null, 200));

        // Multiple whitespaces between arguments
        assertParseSuccess(parser, "\n pr/Product1 \n min/50 \n max/200 \t",
                new SetThresholdCommand(new ProductName("Product1"), 50, 200));

        // Different values
        assertParseSuccess(parser, " pr/AnotherProduct min/30 max/150",
                new SetThresholdCommand(new ProductName("AnotherProduct"), 30, 150));
    }

    @Test
    public void parse_compulsoryAndMissingField_failure() {
        String expectedMessage = String.format(MESSAGE_MISSING_REQUIRED_PREFIXES,
                SetThresholdCommand.MESSAGE_USAGE);

        // missing product name prefix
        assertParseFailure(parser, " min/50", expectedMessage);

        // missing thresholds (both min/ and max/)
        assertParseFailure(parser, " pr/Product1", MESSAGE_MISSING_THRESHOLD);

        // missing all prefixes
        assertParseFailure(parser, "", expectedMessage);

        // missing values with prefix present
        assertParseFailure(parser, " pr/ min/50", ProductName.MESSAGE_CONSTRAINTS);

    }

    @Test
    public void parse_invalidValue_failure() {

        //no value provided for min or max prefix
        assertParseFailure(parser, " pr/Product1 min/", "Stock level is empty, please provide a value.");
        assertParseFailure(parser, " pr/Product1 max/", "Stock level is empty, please provide a value.");

        // invalid product name
        assertParseFailure(parser, " pr/Product/ min/50",
                String.format(MESSAGE_INVALID_SYNTAX, SetThresholdCommand.MESSAGE_USAGE));

        // invalid min stock level (non-numeric)
        assertParseFailure(parser, " pr/Product min/abc",
                "Invalid stock level: Threshold levels should be positive integers.");

        // invalid max stock level (non-numeric)
        assertParseFailure(parser, " pr/Product max/abc",
                "Invalid stock level: Threshold levels should be positive integers.");
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate product name
        assertParseFailure(parser, " pr/Product1 pr/Product2 min/50",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCT_NAME));

        // duplicate min stock level
        assertParseFailure(parser, " pr/Product1 min/50 min/60",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MIN_STOCK_LEVEL));

        // duplicate max stock level
        assertParseFailure(parser, " pr/Product1 max/200 max/250",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_MAX_STOCK_LEVEL));

        // multiple duplicates
        assertParseFailure(parser, " pr/Product1 pr/Product2 min/50 min/60 max/200 max/250",
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_PRODUCT_NAME, PREFIX_MIN_STOCK_LEVEL, PREFIX_MAX_STOCK_LEVEL));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, "some random string pr/Product1 min/50",
                String.format(MESSAGE_UNEXPECTED_PREAMBLE, SetThresholdCommand.MESSAGE_USAGE));

        // preamble with numbers
        assertParseFailure(parser, "123 pr/Product1 min/50",
                String.format(MESSAGE_UNEXPECTED_PREAMBLE, SetThresholdCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroAndNegativeValues_failure() {
        // zero min stock level
        assertParseFailure(parser, " pr/Product1 min/0", MESSAGE_INVALID_THRESHOLD);

        // negative min stock level
        assertParseFailure(parser, " pr/Product1 min/-50", MESSAGE_INVALID_THRESHOLD);

        // zero max stock level
        assertParseFailure(parser, " pr/Product1 max/0", MESSAGE_INVALID_THRESHOLD);

        // negative max stock level
        assertParseFailure(parser, " pr/Product1 max/-100", MESSAGE_INVALID_THRESHOLD);
    }

    @Test
    public void parse_missingThresholds_failure() {
        // missing both min/ and max/
        assertParseFailure(parser, " pr/Product1", MESSAGE_MISSING_THRESHOLD);
    }

    @Test
    public void parse_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
