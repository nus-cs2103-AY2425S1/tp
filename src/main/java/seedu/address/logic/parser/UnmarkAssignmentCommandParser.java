package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnmarkAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;

/**
 * Parses input arguments and creates a new UnmarkAssignmentCommand object.
 */
public class UnmarkAssignmentCommandParser implements Parser<UnmarkAssignmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnmarkAssignmentCommand
     * and returns an UnmarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UnmarkAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAssignmentCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkAssignmentCommand.MESSAGE_USAGE), pe);
        }

        String title = argMultimap.getValue(PREFIX_NAME).get();

        if (title.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_EMPTY_ASSIGNMENT_TITLE);
        }

        Assignment assignment = new Assignment(title, LocalDateTime.now());
        return new UnmarkAssignmentCommand(index.getZeroBased(), assignment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
