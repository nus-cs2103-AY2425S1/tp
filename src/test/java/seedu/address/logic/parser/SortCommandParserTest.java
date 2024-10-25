package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SORT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.sortPersonsComparators.*;

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
    public void parse_Name_success() {
        // name
        String userInput = "name";
        SortCommand expectedCommand = new SortCommand("name", comparatorForName);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_Role_success() {
        // role
        String userInput = "role";
        SortCommand expectedCommand = new SortCommand("role", comparatorForRole);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_Phone_success() {
        // phone
        String userInput = "phone";
        SortCommand expectedCommand = new SortCommand("phone", comparatorForPhone);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_Email_success() {
        // email
        String userInput = "email";
        SortCommand expectedCommand = new SortCommand("email", comparatorForEmail);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_Address_success() {
        // address
        String userInput = "address";
        SortCommand expectedCommand = new SortCommand("address", comparatorForAddress);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
