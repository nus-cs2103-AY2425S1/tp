package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CheckAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;

/**
 * Parses input arguments and creates a new CheckAssignmentCommand object.
 */
public class CheckAssignmentCommandParser implements Parser<CheckAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the CheckAssignmentCommand
     * and returns an CheckAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public CheckAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckAssignmentCommand.MESSAGE_USAGE));
        }

        String title = argMultimap.getValue(PREFIX_NAME).get();
        if (title.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_EMPTY_ASSIGNMENT_TITLE);
        }

        Assignment assignment = new Assignment(title, LocalDateTime.now());
        return new CheckAssignmentCommand(assignment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
