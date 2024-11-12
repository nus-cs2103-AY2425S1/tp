package seedu.hiredfiredpro.logic.parser;

import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.hiredfiredpro.logic.commands.ViewStatusCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;

/**
 * Parses input arguments and creates a new ViewStatusCommand object
 */
public class ViewStatusCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewStatusCommand
     * and returns an ViewStatusCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewStatusCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_JOB);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_JOB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());

        return new ViewStatusCommand(name, job);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
