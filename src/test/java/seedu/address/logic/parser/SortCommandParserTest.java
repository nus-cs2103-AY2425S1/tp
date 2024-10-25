package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_ADDRESS;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_EMAIL;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_NAME;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_PHONE;
import static seedu.address.testutil.SortPersonsComparators.COMPARATOR_FOR_ROLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no param specified
        assertParseFailure(parser, INVALID_SORT_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_name_success() {
        // name
        String userInput = "name";
        SortCommand expectedCommand = new SortCommand("name", COMPARATOR_FOR_NAME);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_role_success() {
        // role
        String userInput = "role";
        SortCommand expectedCommand = new SortCommand("role", COMPARATOR_FOR_ROLE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_phone_success() {
        // phone
        String userInput = "phone";
        SortCommand expectedCommand = new SortCommand("phone", COMPARATOR_FOR_PHONE);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_email_success() {
        // email
        String userInput = "email";
        SortCommand expectedCommand = new SortCommand("email", COMPARATOR_FOR_EMAIL);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_address_success() {
        // address
        String userInput = "address";
        SortCommand expectedCommand = new SortCommand("address", COMPARATOR_FOR_ADDRESS);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
