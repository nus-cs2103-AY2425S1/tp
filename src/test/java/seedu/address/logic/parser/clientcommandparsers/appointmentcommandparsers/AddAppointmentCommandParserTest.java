package seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.clientcommands.appointmentcommands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;
import seedu.address.model.person.Name;

public class AddAppointmentCommandParserTest {
    private static final String VALID_DATE = "01-11-24";
    private static final String VALID_FROM = "09:00";
    private static final String VALID_TO = "10:00";
    private static final String INVALID_DATE = "01/11/2024";
    private static final String INVALID_FROM = "9am";
    private static final String INVALID_TO = "10";
    private static final Name VALID_NAME = ALICE.getName();
    private final AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String userInput = ALICE.getName() + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        Appointment expectedAppointment = new Appointment(
                new Date(VALID_DATE), new From(VALID_FROM), new To(VALID_TO));
        AddAppointmentCommand expectedCommand = new AddAppointmentCommand(ALICE.getName(), expectedAppointment);

        AddAppointmentCommand result = parser.parse(userInput);
        assertEquals(expectedCommand, result);
    }

    @Test
    public void parse_invalidPeriod_throwsParseException() {
        String userInput = ALICE.getName() + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_TO + " "
                + PREFIX_TO + VALID_FROM;

        String expectedMessage = AddAppointmentCommand.MESSAGE_INVALID_PERIOD;

        assertThrows(ParseException.class, () -> parser.parse(userInput), expectedMessage);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing name
        assertThrows(ParseException.class, () -> parser.parse(
               PREFIX_DATE + VALID_DATE
               + PREFIX_FROM + VALID_FROM
               + PREFIX_TO + VALID_TO), expectedMessage);

        // missing date
        assertThrows(ParseException.class, () -> parser.parse(
                VALID_NAME + " "
                        + PREFIX_FROM + VALID_FROM + " "
                        + PREFIX_TO + VALID_TO), expectedMessage);

        // missing from
        assertThrows(ParseException.class, () -> parser.parse(
                VALID_NAME + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_TO + VALID_TO), expectedMessage);

        // missing to
        assertThrows(ParseException.class, () -> parser.parse(
                VALID_NAME + " "
                        + PREFIX_DATE + VALID_DATE + " "
                        + PREFIX_FROM + VALID_FROM), expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        assertThrows(ParseException.class, () -> parser.parse("$$" + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO), expectedMessage);
    }

    @Test
    public void parse_invalidDateFormat_failure() {
        String userInput = VALID_NAME + " "
                + PREFIX_DATE + INVALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidFromFormat_failure() {
        String userInput = VALID_NAME + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + INVALID_FROM + " "
                + PREFIX_TO + VALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidToFormat_failure() {
        String userInput = VALID_NAME + " "
                + PREFIX_DATE + VALID_DATE + " "
                + PREFIX_FROM + VALID_FROM + " "
                + PREFIX_TO + INVALID_TO;

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
