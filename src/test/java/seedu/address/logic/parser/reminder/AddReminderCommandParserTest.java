package seedu.address.logic.parser.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.reminder.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderDescription;


public class AddReminderCommandParserTest {

    private final AddReminderCommandParser parser = new AddReminderCommandParser();
    @Test
    public void parse_missingName_throwsParseException() {
        String userInput = "dt/2021-12-31 23:59 d/New Year's Eve";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDateTime_throwsParseException() {
        String userInput = "n/John Doe d/New Year's Eve";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingDescription_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 23:59";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 23:59 d/New Year's Eve extraArg";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateTimeFormat_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 25:00 d/New Year's Eve";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
    @Test
    public void parse_invalidDescriptionFormat_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 23:59 d/"; // Empty description
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidNameFormat_throwsParseException() {
        String userInput = "n/ dt/2021-12-31 23:59 d/New Year's Eve"; // Empty name
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidMonth_throwsParseException() {
        String userInput = "n/John Doe dt/2021-13-31 23:59 d/New Year's Eve"; // Invalid month
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String userInput = "n/John Doe n/Jane Doe dt/2021-12-31 23:59 d/New Year's Eve"; // Duplicate name prefix
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        String userInput = ""; // Empty input
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_onlyPrefixes_throwsParseException() {
        String userInput = "n/ d/ dt/"; // All prefixes are present, but values are empty
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_success_withLeadingAndTrailingSpaces() throws ParseException {
        String userInput = "   n/John Doe   dt/2021-12-31 23:59   d/New Year's Eve   ";
        AddReminderCommand command = parser.parse(userInput);

        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidCharactersInName_throwsParseException() {
        String userInput = "n/John@Doe dt/2021-12-31 23:59 d/New Year's Eve"; // Invalid character in name
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_validInput_success() throws ParseException {
        String userInput = " n/John Doe dt/2021-12-31 23:59 d/New Year's Eve";
        AddReminderCommand command = parser.parse(userInput);

        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);

        assertEquals(expectedCommand, command);
    }
    @Test
    public void parse_multipleSpacesBetweenArguments_success() throws ParseException {
        String userInput = " n/John Doe    dt/2021-12-31 23:59   d/New Year's Eve";
        AddReminderCommand command = parser.parse(userInput);

        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_validInput_withSpecialCharactersInDescription() throws ParseException {
        String userInput = " n/John Doe dt/2021-12-31 23:59 d/New Year's Eve Party!";
        AddReminderCommand command = parser.parse(userInput);
        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("New Year's Eve Party!"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);
        assertEquals(expectedCommand, command);
    }
    @Test
    public void parse_dateTimeInPast_throwsParseException() {
        String userInput = "n/John Doe dt/2000-01-01 12:00 d/Past Event";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_nameWithSpacesOnly_throwsParseException() {
        String userInput = "n/   dt/2021-12-31 23:59 d/Description";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_descriptionWithSpacesOnly_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 23:59 d/   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_trailingSpecialCharacterInName_throwsParseException() {
        String userInput = "n/John Doe! dt/2021-12-31 23:59 d/Description";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_untrimmedName_success() throws ParseException {
        String userInput = " n/ John Doe dt/2021-12-31 23:59 d/Description";
        AddReminderCommand command = parser.parse(userInput);

        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("Description"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);
        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_invalidDateWithoutTime_throwsParseException() {
        String userInput = "n/John Doe dt/2021-12-31 d/Event";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_validInput_withEmptyPreamble() throws ParseException {
        String userInput = " n/John Doe dt/2021-12-31 23:59 d/Valid Event";
        AddReminderCommand command = parser.parse(userInput);

        Reminder expectedReminder = new Reminder("John Doe",
                LocalDateTime.of(2021, 12, 31, 23, 59),
                new ReminderDescription("Valid Event"));
        AddReminderCommand expectedCommand = new AddReminderCommand(expectedReminder);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_prefixesWithEmptyValuesAndExtraSpaces_throwsParseException() {
        String userInput = "n/   dt/   d/   ";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_emptyDateTimeValue_throwsParseException() {
        String userInput = "n/John Doe dt/ d/Description";
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidDateFormat_throwsParseException() {
        String userInput = "n/John Doe dt/31-12-2021 23:59 d/Event"; // Incorrect date format
        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

}
