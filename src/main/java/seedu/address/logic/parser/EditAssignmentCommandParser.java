package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GradeAssignmentCommand object
 */
public class EditAssignmentCommandParser implements Parser<EditAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the MarkAssignmentCommandParser
     * and returns an MarkAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX,
                        PREFIX_ASSIGNMENT_SCORE);
        if (!arePrefixesPresent(argumentMultimap, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX,
                PREFIX_ASSIGNMENT_SCORE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE));
        }
        Index studentIndex;
        Index assignmentIndex;
        int assignmentScore;
        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX,
                PREFIX_ASSIGNMENT_SCORE);
        try {
            studentIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            assignmentIndex = ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_ASSIGNMENT_INDEX).get());
            assignmentScore = ParserUtil.parseScore(argumentMultimap.getValue(PREFIX_ASSIGNMENT_SCORE).get());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE), pe);
        }
        return new EditAssignmentCommand(studentIndex, assignmentIndex, assignmentScore);
    }
}
