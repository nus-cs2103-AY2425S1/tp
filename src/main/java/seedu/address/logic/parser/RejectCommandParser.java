package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.RejectCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Job;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new RejectCommand object
 */
public class RejectCommandParser implements Parser<RejectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RejectCommand
     * and returns a RejectCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public RejectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_JOB);

        if (!argMultimap.arePrefixesPresent(CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_JOB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RejectCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Job job = ParserUtil.parseJob(argMultimap.getValue(CliSyntax.PREFIX_JOB).get());

        return new RejectCommand(name, job);
    }
}
