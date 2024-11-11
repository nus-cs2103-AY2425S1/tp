package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.model.person.Name;

public class ReminderCommandParserTest {
    public static final String VALID_NAME = "John Doe";
    public static final Name NAME = new Name("John Doe");
    public static final String VALID_REMINDER_TIME = "1 day";
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no reminder specified
        assertParseFailure(parser, VALID_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_success() {
        // valid input with name and reminder
        String userInput = VALID_NAME + " r/" + VALID_REMINDER_TIME;
        ReminderCommand expectedCommand = new ReminderCommand(NAME, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_extraWhitespace_success() {
        // valid input with extra whitespaces
        String userInput = "    " + VALID_NAME + "   r/" + VALID_REMINDER_TIME + "   ";
        ReminderCommand expectedCommand = new ReminderCommand(NAME, VALID_REMINDER_TIME);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleReminderPrefixes_failure() {
        String userInput = VALID_NAME + " r/" + VALID_REMINDER_TIME + " r/3 days";

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMINDER));
    }

    @Test
    public void parse_emptyOrWhiteSpaceInput_failure() {
        // empty string
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReminderCommand.MESSAGE_USAGE));

        // only white space
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ReminderCommand.MESSAGE_USAGE));
    }
}
