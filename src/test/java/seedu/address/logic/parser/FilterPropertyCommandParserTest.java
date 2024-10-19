package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.model.property.Type;

public class FilterPropertyCommandParserTest {
    private FilterPropertyCommandParser parser = new FilterPropertyCommandParser();
    private final String type = "CONDO";

    @Test
    public void parse_nameSpecified_success() {
        // Test for valid phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type)));
    }

    @Test
    public void parse_emptyName_failure() {
        // Test for empty phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, FilterPropertyCommand.COMMAND_WORD, expectedMessage);
    }
}
