package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAssignment object
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAssignmentCommandParser
     * and returns an DeleteAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_INDEX, PREFIX_REMARK);

        Index studentIndex;
        Index assignmentIndex;
        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            assignmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSIGNMENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
        return new DeleteAssignmentCommand(studentIndex, assignmentIndex);
    }
}
