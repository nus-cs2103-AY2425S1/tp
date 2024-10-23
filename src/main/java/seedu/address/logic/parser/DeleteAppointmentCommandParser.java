package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Schedule;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns a DeleteAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATE);
        Name name = ParserUtil.parseName(argMultimap.getPreamble());

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAppointmentCommand.MESSAGE_USAGE));
        }

        Schedule appointment = ParserUtil.parseSchedule(argMultimap.getValue(PREFIX_DATE).orElse(""));
        return new DeleteAppointmentCommand(name, appointment);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
