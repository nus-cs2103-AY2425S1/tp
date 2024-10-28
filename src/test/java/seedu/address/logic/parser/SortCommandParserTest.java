package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_FIELD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_NUMPROP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_HIGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_LOW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIELD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // valid field and order
        assertParseSuccess(parser, " " + PREFIX_FIELD + VALID_FIELD_NAME + " " + PREFIX_ORDER + VALID_ORDER_LOW,
                new SortCommand(VALID_FIELD_NAME, VALID_ORDER_LOW));

        assertParseSuccess(parser, " " + PREFIX_FIELD + VALID_FIELD_NUMPROP + " " + PREFIX_ORDER + VALID_ORDER_HIGH,
                new SortCommand(VALID_FIELD_NUMPROP, VALID_ORDER_HIGH));
    }

    @Test
    public void parse_invalidField_failure() {
        // invalid field
        assertParseFailure(parser, " " + PREFIX_FIELD + INVALID_FIELD + " " + PREFIX_ORDER + VALID_ORDER_LOW,
                SortCommand.MESSAGE_AVAILABLE_FIELDS);
    }

    @Test
    public void parse_invalidOrder_failure() {
        // invalid order
        assertParseFailure(parser, " " + PREFIX_FIELD + VALID_FIELD_NAME + " " + PREFIX_ORDER + INVALID_ORDER,
                SortCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void parse_missingField_failure() {
        // missing field
        assertParseFailure(parser, " " + PREFIX_ORDER + VALID_ORDER_LOW, SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_missingOrder_failure() {
        // missing order
        assertParseFailure(parser, " " + PREFIX_FIELD + VALID_FIELD_NAME, SortCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        // duplicate prefixes
        assertParseFailure(
                parser, " " + PREFIX_FIELD + VALID_FIELD_NAME + " "
                        + PREFIX_FIELD + VALID_FIELD_NUMPROP + " " + PREFIX_ORDER + VALID_ORDER_LOW,
                Messages.MESSAGE_DUPLICATE_FIELDS + PREFIX_FIELD);
    }
}
