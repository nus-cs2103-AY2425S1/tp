package seedu.hiredfiredpro.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.hiredfiredpro.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_JOB;
import static seedu.hiredfiredpro.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.hiredfiredpro.logic.commands.HireCommand;
import seedu.hiredfiredpro.logic.parser.exceptions.ParseException;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;

/**
 * Parses input arguments and creates a new HireCommand object
 */
public class HireCommandParser implements Parser<HireCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HireCommand
     * and returns a HireCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public HireCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_JOB);

        if (!argMultimap.arePrefixesPresent(PREFIX_NAME, PREFIX_JOB) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HireCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(PREFIX_JOB).get());

        return new HireCommand(name, job);
    }
}
