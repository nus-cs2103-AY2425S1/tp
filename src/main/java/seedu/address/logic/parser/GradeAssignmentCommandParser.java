package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_INDEX;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GradeAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GradeAssignmentCommand object
 */
public class GradeAssignmentCommandParser implements Parser<GradeAssignmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GradeAssignmentCommandParser
     * and returns an GradeAssignmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeAssignmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX,
                        PREFIX_ASSIGNMENT_INDEX, PREFIX_ASSIGNMENT_SCORE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX, PREFIX_ASSIGNMENT_SCORE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GradeAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_SCORE, PREFIX_STUDENT_INDEX,
                PREFIX_ASSIGNMENT_INDEX);

        Index studentIndex;
        Index assignmentIndex;
        int score;

        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            assignmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSIGNMENT_INDEX).get());
            score = ParserUtil.parseScore(argMultimap.getValue(PREFIX_ASSIGNMENT_SCORE).get());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }
        return new GradeAssignmentCommand(studentIndex, assignmentIndex, score);
    }
}
