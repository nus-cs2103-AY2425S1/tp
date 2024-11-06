package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_MAX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
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
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX,
                        PREFIX_ASSIGNMENT_NAME, PREFIX_ASSIGNMENT_MAX_SCORE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAssignmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_INDEX, PREFIX_ASSIGNMENT_INDEX,
                PREFIX_ASSIGNMENT_NAME, PREFIX_ASSIGNMENT_MAX_SCORE);

        Index studentIndex;
        Index assignmentIndex;

        try {
            studentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_STUDENT_INDEX).get());
            assignmentIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_ASSIGNMENT_INDEX).get());
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage());
        }

        EditAssignmentCommand.EditAssignmentDescriptor editAssignmentDescriptor =
                new EditAssignmentCommand.EditAssignmentDescriptor();

        if (argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).isPresent()) {
            editAssignmentDescriptor.setName(ParserUtil.parseAssignmentName(
                    argMultimap.getValue(PREFIX_ASSIGNMENT_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_ASSIGNMENT_MAX_SCORE).isPresent()) {
            editAssignmentDescriptor.setMaxScore(ParserUtil.parseMaxScore(
                    argMultimap.getValue(PREFIX_ASSIGNMENT_MAX_SCORE).get()));
        }

        if (!editAssignmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAssignmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAssignmentCommand(studentIndex, assignmentIndex, editAssignmentDescriptor);
    }
}
