package seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.clientcommands.appointmentcommands.AddAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

/**
 * Parses input arguments and creates a new AppointmentCommandParser object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AppointmentCommand}
     * and returns an {@code AppointmentCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_FROM, PREFIX_TO);

        Index index = ParserUtil.parseIndexWithInvalidCommandFormatMessage(argMultimap.getPreamble(),
                AddAppointmentCommand.MESSAGE_USAGE);

        boolean hasMissingPrefixes = !ArgumentMultimap.arePrefixesPresent(argMultimap,
                PREFIX_DATE, PREFIX_FROM, PREFIX_TO);

        if (hasMissingPrefixes) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddAppointmentCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        From from = ParserUtil.parseFrom(argMultimap.getValue(PREFIX_FROM).get());
        To to = ParserUtil.parseTo(argMultimap.getValue(PREFIX_TO).get());

        // Check if the date for From is less than or equal to that of To
        if (!Appointment.isValidPeriod(from, to)) {
            throw new ParseException(AddAppointmentCommand.MESSAGE_INVALID_PERIOD);
        }

        return new AddAppointmentCommand(index, new Appointment(date, from, to));
    }


}
