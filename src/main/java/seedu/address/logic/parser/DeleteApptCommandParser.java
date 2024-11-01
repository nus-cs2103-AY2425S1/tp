package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteApptCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new DeleteApptCommand object
 * @throws ParseException
 */
public class DeleteApptCommandParser implements Parser<DeleteApptCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteApptCommand
     * and returns a DeleteApptCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteApptCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATETIME)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteApptCommand.MESSAGE_USAGE));
        }

        LocalDateTime apptDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_DATETIME).get());
        Nric nric = ParserUtil.parseNric(argMultimap.getPreamble());

        return new DeleteApptCommand(nric, apptDateTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
