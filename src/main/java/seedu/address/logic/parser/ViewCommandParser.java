package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new ApptCommand object
 *
 * @throws ParseException
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @param args
     * @return ViewCommand
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);

        if (!argMultimap.getPreamble().isEmpty() || !arePrefixesPresent(argMultimap, PREFIX_NRIC)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());

        return new ViewCommand(nric);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
