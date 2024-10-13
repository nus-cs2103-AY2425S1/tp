package seedu.address.logic.parser;


import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COST_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_APPLE_NAN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_APPLE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_APPLE_WRONG_DP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COST_APPLE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCT_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRODUCT_APPLE_EMPTY_SPACE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_APPLE_MISSING_UNITS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_APPLE_NAN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_APPLE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_QUANTITY_APPLE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUPPLIER_INDEX_APPLE_NEGATIVE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUPPLIER_INDEX_APPLE_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.QUANTITY_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_INDEX_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COST_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUPPLIER_INDEX_APPLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddDeliveryCommand;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.SupplierIndex;
import seedu.address.model.delivery.Time;
import seedu.address.model.product.Product;
import seedu.address.testutil.TypicalDeliveriesWithoutSender;

public class AddDeliveryCommandParserTest {
    private AddDeliveryCommandParser parser = new AddDeliveryCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Delivery expectedDelivery = TypicalDeliveriesWithoutSender.APPLE;
        System.out.println(PREAMBLE_WHITESPACE + TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE);
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE, new AddDeliveryCommand(expectedDelivery));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeliveryCommand.MESSAGE_USAGE);

        // missing time prefix
        assertParseFailure(parser, VALID_DATE_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                expectedMessage);

        // missing supplier index prefix
        assertParseFailure(parser, TIME_DESC_APPLE + VALID_SUPPLIER_INDEX_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                expectedMessage);

        // missing product prefix
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + VALID_PRODUCT_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                expectedMessage);

        // missing quantity prefix
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + VALID_QUANTITY_APPLE + COST_DESC_APPLE,
                expectedMessage);
        // missing cost prefix
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + VALID_COST_APPLE,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DATE_APPLE + VALID_SUPPLIER_INDEX_APPLE
                        + VALID_PRODUCT_APPLE + VALID_QUANTITY_APPLE + VALID_COST_APPLE,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_DATE_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                Time.MESSAGE_CONSTRAINTS);

        // negative Supplier index
        assertParseFailure(parser, TIME_DESC_APPLE + INVALID_SUPPLIER_INDEX_APPLE_NEGATIVE
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                SupplierIndex.MESSAGE_CONSTRAINTS);

        // zero supplier index
        assertParseFailure(parser, TIME_DESC_APPLE + INVALID_SUPPLIER_INDEX_APPLE_ZERO
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                SupplierIndex.MESSAGE_CONSTRAINTS);

        // non-alphanumeric product
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + INVALID_PRODUCT_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                Product.MESSAGE_CONSTRAINTS);

        // empty product name
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + INVALID_PRODUCT_APPLE_EMPTY_SPACE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                Product.MESSAGE_CONSTRAINTS);
        // Missing units quantitiy
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + INVALID_QUANTITY_APPLE_MISSING_UNITS + COST_DESC_APPLE,
                Quantity.MESSAGE_CONSTRAINTS);
        //Not a number quantitiy
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + INVALID_QUANTITY_APPLE_NAN + COST_DESC_APPLE,
                Quantity.MESSAGE_CONSTRAINTS);
        // Negative quantitiy
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + INVALID_QUANTITY_APPLE_NEGATIVE + COST_DESC_APPLE,
                Quantity.MESSAGE_CONSTRAINTS);
        // zero quantitiy
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + INVALID_QUANTITY_APPLE_ZERO + COST_DESC_APPLE,
                Quantity.MESSAGE_CONSTRAINTS);
        // Negative cost
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + INVALID_COST_APPLE_NEGATIVE,
                Cost.MESSAGE_CONSTRAINTS);
        // Too many decimal points cost
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + INVALID_COST_APPLE_WRONG_DP,
                Cost.MESSAGE_CONSTRAINTS);
        // Not a number cost
        System.out.println(TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + INVALID_COST_APPLE_NAN);
        System.out.println(Cost.isValidCost(INVALID_COST_APPLE_NAN));
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + INVALID_COST_APPLE_NAN,
                Cost.MESSAGE_CONSTRAINTS);
        // zero cost
        assertParseFailure(parser, TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + INVALID_COST_APPLE_ZERO,
                Cost.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DATE_APPLE + SUPPLIER_INDEX_DESC_APPLE
                + PRODUCT_DESC_APPLE + INVALID_QUANTITY_APPLE_NAN + COST_DESC_APPLE,
                Time.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TIME_DESC_APPLE + SUPPLIER_INDEX_DESC_APPLE
                        + PRODUCT_DESC_APPLE + QUANTITY_DESC_APPLE + COST_DESC_APPLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddDeliveryCommand.MESSAGE_USAGE));
    }
}
