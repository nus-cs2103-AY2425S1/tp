package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STOCK_LEVEL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.UpdateMaxStockLevelCommandParser.MESSAGE_INVALID_MAXSTOCK_LEVEL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateMaxStockLevelCommand;
import seedu.address.model.product.ProductName;

public class UpdateMaxStockLevelCommandParserTest {
    private UpdateMaxStockLevelCommandParser parser;
    private ProductName validProductName;
    private int validStockLevel;

    @BeforeEach
    public void setUp() {
        parser = new UpdateMaxStockLevelCommandParser();
        validProductName = new ProductName("ValidProduct");
        validStockLevel = 1000;
    }

    @Test
    public void parse_allFieldsPresent_success() {
        // basic case
        assertParseSuccess(parser, " pr/Product1 stk/10500",
                new UpdateMaxStockLevelCommand(new ProductName("Product1"), 10500));

        // multiple whitespaces between arguments
        assertParseSuccess(parser, "\n pr/Product1 \n stk/5200 \t",
                new UpdateMaxStockLevelCommand(new ProductName("Product1"), 5200));

        // different values
        assertParseSuccess(parser, " pr/AnotherProduct stk/500",
                new UpdateMaxStockLevelCommand(new ProductName("AnotherProduct"), 500));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateMaxStockLevelCommand.MESSAGE_USAGE);

        // missing product name prefix
        assertParseFailure(parser, " stk/1000", expectedMessage);

        // missing stock level prefix
        assertParseFailure(parser, " pr/Product", expectedMessage);

        // missing all prefixes
        assertParseFailure(parser, "", expectedMessage);

        // missing values with prefix present
        assertParseFailure(parser, " pr/ stk/1000", ProductName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " pr/Product stk/", ProductName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid product name
        assertParseFailure(parser, " pr/Product#1 stk/1000", ProductName.MESSAGE_CONSTRAINTS);

        // invalid stock level (non-numeric)
        assertParseFailure(parser, " pr/Product1 stk/abc", ProductName.MESSAGE_CONSTRAINTS);

        // both invalid
        assertParseFailure(parser, " pr/Product#1 stk/abc", ProductName.MESSAGE_CONSTRAINTS);
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateMaxStockLevelCommand.MESSAGE_USAGE));

        // preamble with numbers
        assertParseFailure(parser, "123 pr/Product1 stk/1000",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateMaxStockLevelCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroAndNegativeValues_failure() {
        // zero stock level
        assertParseFailure(parser, " pr/Product1 stk/0", MESSAGE_INVALID_MAXSTOCK_LEVEL);

        // negative stock level
        assertParseFailure(parser, " pr/Product1 stk/-1000", MESSAGE_INVALID_MAXSTOCK_LEVEL);
    }

    @Test
    public void parse_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}

