package seedu.address.logic.parser.reminder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminder.EditReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class EditReminderCommandParserTest {
    private EditReminderCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new EditReminderCommandParser();
    }

    @Test
    public void parse_noFieldsSpecified_throwsParseException() {
        String userInput = "1"; // Only index provided
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                EditReminderCommand.MESSAGE_REMINDER_NOT_EDITED);
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "invalidIndex " + PREFIX_DATE_TIME + "2022-01-01 00:00";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateFields_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE_TIME + "2022-01-01 00:00 "
                + PREFIX_DATE_TIME + "2022-02-01 00:00"; // Duplicate dateTime prefix
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDateTime_throwsParseException() {
        String userInput = "1 " + PREFIX_DATE_TIME + "invalidDateTime";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidDescription_throwsParseException() {
        String userInput = "1 " + PREFIX_DESCRIPTION + "";
        assertThrows(ParseException.class, () -> parser.parse(userInput),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE));
    }
}
