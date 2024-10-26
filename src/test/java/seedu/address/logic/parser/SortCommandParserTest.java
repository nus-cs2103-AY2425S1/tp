package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMPTY_SORT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FIELD_SORT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_EMAIL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_PHONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_ADDRESS;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_EMAIL;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_NAME;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_PHONE;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_ROLE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @BeforeEach
    public void setUp() {
        parser = new SortCommandParser();
    }

    @Test
    public void parse_missingParts_failure() {
        // no param specified
        assertParseFailure(parser, INVALID_EMPTY_SORT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidField_failure() {
        // no param specified
        assertParseFailure(parser, INVALID_FIELD_SORT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_name_success() {
        // name
        SortCommand expectedCommand = new SortCommand("name", COMPARATOR_FOR_NAME);
        assertParseSuccess(parser, VALID_SORT_NAME, expectedCommand);
    }

    @Test
    public void parse_role_success() {
        // role
        SortCommand expectedCommand = new SortCommand("role", COMPARATOR_FOR_ROLE);
        assertParseSuccess(parser, VALID_SORT_ROLE, expectedCommand);
    }

    @Test
    public void parse_phone_success() {
        // phone
        SortCommand expectedCommand = new SortCommand("phone", COMPARATOR_FOR_PHONE);
        assertParseSuccess(parser, VALID_SORT_PHONE, expectedCommand);
    }

    @Test
    public void parse_email_success() {
        // email
        SortCommand expectedCommand = new SortCommand("email", COMPARATOR_FOR_EMAIL);
        assertParseSuccess(parser, VALID_SORT_EMAIL, expectedCommand);
    }

    @Test
    public void parse_address_success() {
        // address
        SortCommand expectedCommand = new SortCommand("address", COMPARATOR_FOR_ADDRESS);
        assertParseSuccess(parser, VALID_SORT_ADDRESS, expectedCommand);
    }
}
