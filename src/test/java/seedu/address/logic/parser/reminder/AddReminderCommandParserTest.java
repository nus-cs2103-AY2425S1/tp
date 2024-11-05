package seedu.address.logic.parser.reminder;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;




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
}
