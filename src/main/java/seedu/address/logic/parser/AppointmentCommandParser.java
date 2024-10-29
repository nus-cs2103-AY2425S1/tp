package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TO;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.From;
import seedu.address.model.appointment.To;

/**
 * Parses input arguments and creates a new AppointmentCommandParser object
 */
public class AppointmentCommandParser implements Parser<AppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code AppointmentCommand}
     * and returns an {@code AppointmentCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_FROM, PREFIX_TO);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE),
                    ive);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_FROM, PREFIX_TO)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointmentCommand.MESSAGE_USAGE));
        }


        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        From from = ParserUtil.parseFrom(argMultimap.getValue(PREFIX_FROM).get());
        To to = ParserUtil.parseTo(argMultimap.getValue(PREFIX_TO).get());
        return new AppointmentCommand(index, new Appointment(date, from, to));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
