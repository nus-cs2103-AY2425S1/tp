package careconnect.logic.parser;

import static careconnect.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static java.util.Objects.requireNonNull;

import careconnect.commons.core.index.Index;
import careconnect.logic.commands.SetAppointmentCommand;
import careconnect.logic.parser.exceptions.ParseException;
import careconnect.model.person.AppointmentDate;

/**
 * Parses input arguments and creates a new SetAppointmentCommand object
 */
public class SetAppointmentCommandParser implements Parser<SetAppointmentCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the SetAppointmentCommand
     * and returns a SetAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DATE);

        Index personIndex;
        AppointmentDate date;
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAppointmentCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!ParserUtil.arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetAppointmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(CliSyntax.PREFIX_DATE);

        date = ParserUtil.parseAppointmentDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());
        return new SetAppointmentCommand(personIndex, date);
    }

}
