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
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_DATE_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_ITEM_BOTTLE;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_QUANTITY_ATLAS;
import static seedu.sellsavvy.logic.commands.ordercommands.OrderCommandTestUtil.VALID_QUANTITY_BOTTLE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ITEM;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_SECOND_ORDER;
import static seedu.sellsavvy.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.core.index.Index;
import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand;
import seedu.sellsavvy.logic.commands.ordercommands.EditOrderCommand.EditOrderDescriptor;
import seedu.sellsavvy.logic.commands.personcommands.EditPersonCommand;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.testutil.EditOrderDescriptorBuilder;

public class EditOrderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOrderCommand.MESSAGE_USAGE);

    private EditOrderCommandParser parser = new EditOrderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ITEM_ATLAS, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditPersonCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + QUANTITY_DESC_ATLAS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + QUANTITY_DESC_ATLAS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 n/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ITEM_DESC, Item.MESSAGE_CONSTRAINTS); // invalid item
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_VALUE, Date.MESSAGE_INVALID_DATE); // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_DIGIT, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_DATE_NO_HYPHEN, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_QUANTITY_NEGATIVE, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_QUANTITY_ZERO, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity
        assertParseFailure(parser, "1" + INVALID_QUANTITY_STRING, Quantity.MESSAGE_CONSTRAINTS); // invalid quantity

        // invalid date followed by valid quantity
        assertParseFailure(parser, "1" + INVALID_DATE_VALUE + QUANTITY_DESC_ATLAS, Date.MESSAGE_INVALID_DATE);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ITEM_DESC + INVALID_DATE_DESC + VALID_QUANTITY_ATLAS,
                Item.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ORDER;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_BOTTLE + DATE_DESC_BOTTLE + ITEM_DESC_ATLAS;

        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withItem(VALID_ITEM_ATLAS)
                .withDate(VALID_DATE_BOTTLE).withQuantity(VALID_QUANTITY_BOTTLE).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // item
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + ITEM_DESC_BOTTLE;
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder().withItem(VALID_ITEM_BOTTLE).build();
        EditOrderCommand expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // quantity
        targetIndex = INDEX_SECOND_ORDER;
        userInput = targetIndex.getOneBased() + QUANTITY_DESC_BOTTLE;
        descriptor = new EditOrderDescriptorBuilder().withQuantity(VALID_QUANTITY_BOTTLE).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        targetIndex = INDEX_FIRST_ORDER;
        userInput = targetIndex.getOneBased() + DATE_DESC_ATLAS;
        descriptor = new EditOrderDescriptorBuilder().withDate(VALID_DATE_ATLAS).build();
        expectedCommand = new EditOrderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + QUANTITY_DESC_ATLAS + INVALID_QUANTITY_NEGATIVE;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_QUANTITY_NEGATIVE + QUANTITY_DESC_ATLAS;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_QUANTITY));

        // multiple valid fields repeated
        userInput = targetIndex.getOneBased() + ITEM_DESC_ATLAS + QUANTITY_DESC_ATLAS + DATE_DESC_ATLAS
                + ITEM_DESC_BOTTLE + QUANTITY_DESC_BOTTLE + DATE_DESC_BOTTLE;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATE));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_ITEM_DESC + INVALID_QUANTITY_NEGATIVE + INVALID_DATE_DESC
                + INVALID_ITEM_DESC + INVALID_QUANTITY_ZERO + INVALID_DATE_DIGIT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ITEM, PREFIX_QUANTITY, PREFIX_DATE));
    }

}
