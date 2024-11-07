package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemoveGradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveGradeCommand object
 */
public class RemoveGradeCommandParser implements Parser<RemoveGradeCommand> {

    @Override
    public RemoveGradeCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ASSIGNMENT);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveGradeCommand.MESSAGE_USAGE));
        }

        String name = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
        if (name.isEmpty()) {
            throw new ParseException("Name cannot be empty.");
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ASSIGNMENT);

        String assignmentName = argMultimap.getValue(PREFIX_ASSIGNMENT).orElse("").trim();
        if (assignmentName.isEmpty()) {
            throw new ParseException("Assignment name cannot be empty.");
        }

        return new RemoveGradeCommand(name, assignmentName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
