package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_NAME_DISPLAYED;
import static seedu.address.logic.Messages.MESSAGE_INVALID_REMINDER_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {
    public static final String VALID_NAME = "John Doe";
    public static final String VALID_REMINDER_TIME = "1 day";
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, " r/" + VALID_REMINDER_TIME, MESSAGE_INVALID_NAME_DISPLAYED);

        // no reminder specified
        assertParseFailure(parser, VALID_NAME, MESSAGE_INVALID_REMINDER_FORMAT);

        // no name and no date and no reminder specified
        assertParseFailure(parser, "", MESSAGE_INVALID_NAME_DISPLAYED);
    }

    @Test
    public void parse_validInput_success() {
        // valid input with name and reminder
        String userInput = VALID_NAME + " r/" + VALID_REMINDER_TIME;
        ReminderCommand expectedCommand = new ReminderCommand(VALID_NAME, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "   r/" + VALID_REMINDER_TIME + "   ";
        ReminderCommand expectedCommand = new ReminderCommand(VALID_NAME, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleReminderPrefixes_failure() {
        String userInput = VALID_NAME + "r/" + VALID_REMINDER_TIME + "r/3 days";

        assertParseFailure(parser, userInput, MESSAGE_INVALID_REMINDER_FORMAT);
    }
}
