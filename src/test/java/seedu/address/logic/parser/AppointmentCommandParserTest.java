package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

public class AppointmentCommandParserTest {
    private static final String VALID_DATE = "01-11-24";
    private static final String VALID_FROM = "09:00";
    private static final String VALID_TO = "10:00";
    private static final String INVALID_DATE = "01/11/2024";
    private static final String INVALID_FROM = "9am";
    private static final String INVALID_TO = "10";
    private final AppointmentCommandParser parser = new AppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        Appointment expectedAppointment = new Appointment(
                new Date(VALID_DATE), new From(VALID_FROM), new To(VALID_TO));
        AppointmentCommand expectedCommand = new AppointmentCommand(INDEX_FIRST_PERSON, expectedAppointment);

        AppointmentCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse(
                INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_FROM + VALID_FROM + " "
                        + PREFIX_TO + VALID_TO), expectedMessage);

        assertThrows(ParseException.class, () -> parser.parse(
                INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TO + VALID_TO), expectedMessage);

        assertThrows(ParseException.class, () -> parser.parse(
                INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_FROM + VALID_FROM), expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse("a "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO), expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + INVALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidFromFormat_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + INVALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidToFormat_failure() {
        String userInput = INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + INVALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
