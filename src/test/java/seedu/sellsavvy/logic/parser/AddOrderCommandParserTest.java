package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DATE_DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.DATE_DESC_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_DATE_DESC;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_DATE_DIGIT;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_DATE_NO_HYPHEN;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_DATE_VALUE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_ITEM_DESC;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_QUANTITY_NEGATIVE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_QUANTITY_STRING;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.INVALID_QUANTITY_ZERO;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.ITEM_DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.ITEM_DESC_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.QUANTITY_DESC_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.QUANTITY_DESC_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_QUANTITY_ATLAS;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalOrders.ATLAS;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.ordercommands.AddOrderCommand;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.testutil.OrderBuilder;

public class AddOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOrderCommand.MESSAGE_USAGE);
    private static final String VALID_ORDER_STRING = ITEM_DESC_ATLAS + DATE_DESC_ATLAS + QUANTITY_DESC_ATLAS;
    private AddOrderCommandParser parser = new AddOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_ORDER_STRING;

        assertParseSuccess(parser, userInput, new AddOrderCommand(INDEX_FIRST_PERSON, ATLAS));
    }

    @Test
    public void parse_quantityFieldMissing_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + ITEM_DESC_ATLAS + DATE_DESC_ATLAS;
        Order expectedOrder = new OrderBuilder().withItem(VALID_ITEM_ATLAS)
                .withDate(VALID_DATE_ATLAS).withQuantity("1").build();

        assertParseSuccess(parser, userInput, new AddOrderCommand(INDEX_FIRST_PERSON, expectedOrder));
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ORDER_STRING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "2", MESSAGE_INVALID_FORMAT);

        // no index and no fieldspecified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_someFieldsPresent_failure() {
        // missing item prefix
        assertParseFailure(parser, "1 " + VALID_ITEM_ATLAS + DATE_DESC_ATLAS + QUANTITY_DESC_ATLAS,
                MESSAGE_INVALID_FORMAT);

        // missing date prefix
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + VALID_DATE_ATLAS + QUANTITY_DESC_ATLAS,
                MESSAGE_INVALID_FORMAT);

        // missing quantity prefix, leading to invalid date
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + DATE_DESC_ATLAS + VALID_QUANTITY_ATLAS,
                Date.MESSAGE_CONSTRAINTS);

        // all prefixes missing
        assertParseFailure(parser, "1 " + VALID_ITEM_ATLAS + VALID_DATE_ATLAS + VALID_QUANTITY_ATLAS,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-2" + VALID_ORDER_STRING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_ORDER_STRING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + VALID_ORDER_STRING, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 n/ string" + VALID_ORDER_STRING, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_ITEM_DESC + DATE_DESC_ATLAS + QUANTITY_DESC_ATLAS,
                Item.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + INVALID_DATE_DESC + QUANTITY_DESC_ATLAS,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + INVALID_DATE_DIGIT + QUANTITY_DESC_ATLAS,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + INVALID_DATE_NO_HYPHEN + QUANTITY_DESC_ATLAS,
                Date.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + INVALID_DATE_VALUE + QUANTITY_DESC_ATLAS,
                Date.MESSAGE_INVALID_DATE);

        // invalid quantity
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + DATE_DESC_ATLAS + INVALID_QUANTITY_ZERO,
                Quantity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + DATE_DESC_ATLAS + INVALID_QUANTITY_STRING,
                Quantity.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ITEM_DESC_ATLAS + DATE_DESC_ATLAS + INVALID_QUANTITY_NEGATIVE,
                Quantity.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String validUserInput = targetIndex.getOneBased() + VALID_ORDER_STRING;

        // multiple item names
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + ITEM_DESC_ATLAS,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // multiple dates
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + DATE_DESC_ATLAS,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple quantities
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + QUANTITY_DESC_ATLAS,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // multiple fields repeated
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING
                        + ITEM_DESC_BOTTLE + DATE_DESC_BOTTLE + QUANTITY_DESC_BOTTLE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM, PREFIX_DATE, PREFIX_QUANTITY));

        // invalid value followed by valid value

        // invalid item name
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_ITEM_DESC + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // invalid dates
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_DATE_DESC + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_DATE_DIGIT + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_DATE_VALUE + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_DATE_NO_HYPHEN + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid quantities
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_QUANTITY_NEGATIVE + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_QUANTITY_ZERO + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_QUANTITY_STRING + VALID_ORDER_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // valid value followed by invalid value

        // invalid item name
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_ITEM_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM));

        // invalid dates
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_DATE_DIGIT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_DATE_VALUE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_DATE_NO_HYPHEN,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid quantities
        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_QUANTITY_NEGATIVE,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_QUANTITY_ZERO,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        assertParseFailure(parser, targetIndex.getOneBased() + VALID_ORDER_STRING + INVALID_QUANTITY_STRING,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));
    }
}
