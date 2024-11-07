package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddSupplyOrderCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.util.Remark;

public class AddSupplyOrderCommandParserTest {
    private AddSupplyOrderCommandParser parser = new AddSupplyOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Base case
        ArrayList<Integer> idList = new ArrayList<>(Arrays.asList(1, 2));
        AddSupplyOrderCommand expectedCommand = new AddSupplyOrderCommand(
                new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), idList, new Remark(VALID_REMARK_BOB));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + " " + PREFIX_ORDER + "1 2" + REMARK_DESC_BOB, expectedCommand);

        // multiple order IDs
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB
                        + " " + PREFIX_ORDER + "1 2 3" + REMARK_DESC_BOB,
                new AddSupplyOrderCommand(new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB),
                        new ArrayList<>(Arrays.asList(1, 2, 3)), new Remark(VALID_REMARK_BOB)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // missing name prefix - defaults to "Guest Supplier"
        ArrayList<Integer> idList = new ArrayList<>(Arrays.asList(1, 2));
        AddSupplyOrderCommand expectedCommand = new AddSupplyOrderCommand(
                new Name("Guest Supplier"), new Phone(VALID_PHONE_BOB), idList, new Remark(""));
        assertParseSuccess(parser, PHONE_DESC_BOB + " " + PREFIX_ORDER + "1 2", expectedCommand);

        // missing remark prefix - defaults to empty string
        expectedCommand = new AddSupplyOrderCommand(
                new Name(VALID_NAME_BOB), new Phone(VALID_PHONE_BOB), idList, new Remark(""));
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + " " + PREFIX_ORDER + "1 2",
                expectedCommand);
    }

    @Test
    public void parse_repeatedFields_failure() {
        String validOrderCommand = NAME_DESC_BOB + PHONE_DESC_BOB + " " + PREFIX_ORDER + "1 2" + REMARK_DESC_BOB;

//        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validOrderCommand,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validOrderCommand,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_AMY + validOrderCommand,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSupplyOrderCommand.MESSAGE_USAGE);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + " " + PREFIX_ORDER + "1",
                expectedMessage);

        // missing order prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB,
                expectedMessage);

        // empty order list
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + " " + PREFIX_ORDER,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid order ID (non-integer)
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + " " + PREFIX_ORDER + "abc",
                "ID must be a valid integer.");

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + " p/abcd1234" + " " + PREFIX_ORDER + "1 2",
                Phone.MESSAGE_CONSTRAINTS);

        // invalid name (contains non-alphanumeric characters)
        assertParseFailure(parser, " n/Bob!" + PHONE_DESC_BOB + " " + PREFIX_ORDER + "1 2",
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB
                + " " + PREFIX_ORDER + "1 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddSupplyOrderCommand.MESSAGE_USAGE));
    }
}