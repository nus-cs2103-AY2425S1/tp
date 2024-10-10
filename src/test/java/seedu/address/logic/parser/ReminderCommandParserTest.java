package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_REMINDER_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {
    public static final String VALID_NAME = "John Doe";
    public static final String VALID_DATE = "2024-10-04 1000";
    public static final String VALID_REMINDER_TIME = "1 day";
    public static final String INVALID_DATE = "invalid_date";
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, "d/" + VALID_DATE + " r/" + VALID_REMINDER_TIME, MESSAGE_INVALID_DATE_FORMAT);

        // no date specified
        assertParseFailure(parser, VALID_NAME + " r/" + VALID_REMINDER_TIME, MESSAGE_INVALID_DATE_FORMAT);

        // no reminder specified
        assertParseFailure(parser, VALID_NAME + " d/" + VALID_DATE, MESSAGE_INVALID_REMINDER_FORMAT);

        // no name and no date and no reminder specified
        assertParseFailure(parser, "", MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_invalidDate_failure() {
        // invalid date format
        assertParseFailure(parser, VALID_NAME + " d/" + INVALID_DATE
                + " r/" + VALID_REMINDER_TIME, MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_validInput_success() {
        // valid input with name, date, and reminder
        String userInput = VALID_NAME + " d/" + VALID_DATE + " r/" + VALID_REMINDER_TIME;
        ReminderCommand expectedCommand = new ReminderCommand(VALID_NAME, VALID_DATE, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "     d/" + VALID_DATE + "   r/" + VALID_REMINDER_TIME + "   ";
        ReminderCommand expectedCommand = new ReminderCommand(VALID_NAME, VALID_DATE, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
