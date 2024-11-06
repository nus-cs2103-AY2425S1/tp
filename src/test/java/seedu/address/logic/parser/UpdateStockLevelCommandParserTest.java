package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_STOCK_LEVEL;
import static seedu.address.logic.Messages.MESSAGE_MISSING_REQUIRED_PREFIXES;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.SetThresholdCommandParser.MESSAGE_MISSING_THRESHOLD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateStockLevelCommand;
import seedu.address.model.product.ProductName;

public class UpdateStockLevelCommandParserTest {
    private UpdateStockLevelCommandParser parser;
    private ProductName validProductName;
    private int validStockLevel;

    @BeforeEach
    public void setUp() {
        parser = new UpdateStockLevelCommandParser();
        validProductName = new ProductName("ValidProduct");
        validStockLevel = 1000;
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // basic case
        assertParseSuccess(parser, " pr/Product1 stk/10500",
                new UpdateStockLevelCommand(new ProductName("Product1"), 10500));

        // multiple whitespaces between arguments
        assertParseSuccess(parser, "\n pr/Product1 \n stk/5200 \t",
                new UpdateStockLevelCommand(new ProductName("Product1"), 5200));

        // different values
        assertParseSuccess(parser, " pr/AnotherProduct stk/500",
                new UpdateStockLevelCommand(new ProductName("AnotherProduct"), 500));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_MISSING_REQUIRED_PREFIXES,
                UpdateStockLevelCommand.MESSAGE_USAGE);

        // missing product name prefix
        assertParseFailure(parser, " stk/1000", expectedMessage);

        // missing stock level prefix
        assertParseFailure(parser, " pr/Product", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, "", expectedMessage);

        // missing values with prefix present
        assertParseFailure(parser, " pr/ stk/1000", ProductName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " pr/Product stk/", "Stock Level not provided!");
    }

    @Test
    public void parse_invalidValue_failure() {

        //stock level missing
        assertParseFailure(parser, " pr/Product stk/", "Stock Level not provided!");

        // invalid product name
        assertParseFailure(parser, " pr/Product#1 stk/1000",
                "Names should only contain alphanumeric characters and spaces, and it should not be blank");

        // invalid stock level (non-numeric)
        assertParseFailure(parser, " pr/Product stk/abc", "Value for stock level is Invalid"
                + "\nKindly enter a valid number");

    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate product name
        assertParseFailure(parser, " pr/Product1 pr/Product2 stk/1000",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCT_NAME));

        // duplicate stock level
        assertParseFailure(parser, " pr/Product1 stk/1000 stk/2000",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STOCK_LEVEL));

        // multiple duplicates
        assertParseFailure(parser, " pr/Product1 pr/Product2 stk/1000 stk/2000",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCT_NAME, PREFIX_STOCK_LEVEL));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // non-empty preamble
        assertParseFailure(parser, "some random string pr/Product1 stk/1000",
                String.format(MESSAGE_UNEXPECTED_PREAMBLE, UpdateStockLevelCommand.MESSAGE_USAGE));

        // preamble with numbers
        assertParseFailure(parser, "123 pr/Product1 stk/1000",
                String.format(MESSAGE_UNEXPECTED_PREAMBLE, UpdateStockLevelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeValues_failure() {
        // negative stock level
        assertParseFailure(parser, " pr/Product1 stk/-1000", MESSAGE_INVALID_STOCK_LEVEL);
    }

    @Test
    public void parse_missingThresholds_failure() {
        // missing both min/ and max/
        assertParseFailure(parser, " pr/Product", "Missing required prefixes. "
                + "\nupdate_stock: Edits the details of the products identified by the product name used in the "
                + "displayed product list. Existing values will be overwritten by the input values."
        + "\nParameters:  pr/PRODUCT_NAME stk/STOCK_LEVEL Example: update_stock pr/Sweaters stk/25000 ");
    }

    @Test
    public void parse_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
