package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new DeleteAssignmentCommand object
 */
public class DeleteAssignmentCommandParser implements Parser<DeleteAssignmentCommand> {

    @Override
    public DeleteAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_DEADLINE, PREFIX_STATUS, PREFIX_GRADE
        );
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteAssignmentCommand.MESSAGE_USAGE));
        }

        // Initializing compulsory fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        AssignmentName queryAssignment = ParserUtil.parseAssignmentName(argMultimap.getValue(PREFIX_ASSIGNMENT).get());

        // Initializing non-compulsory fields
        Deadline queryDeadline;
        Status querySubmissionStatus;
        Status queryGradingStatus;
        Grade queryGrade;

        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            queryDeadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        } else {
            queryDeadline = null;
        }

        if (argMultimap.getAllValues(PREFIX_STATUS).size() > 0) {
            querySubmissionStatus = ParserUtil.parseStatus(argMultimap.getAllValues(PREFIX_STATUS).get(0));
        } else {
            querySubmissionStatus = null;
        }

        if (argMultimap.getAllValues(PREFIX_STATUS).size() > 1) {
            queryGradingStatus = ParserUtil.parseStatus(argMultimap.getAllValues(PREFIX_STATUS).get(1));
        } else {
            queryGradingStatus = null;
        }

        if (argMultimap.getValue(PREFIX_GRADE).isPresent()) {
            queryGrade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
        } else {
            queryGrade = null;
        }

        return new DeleteAssignmentCommand(name,
                new AssignmentQuery(queryAssignment, queryDeadline,
                        querySubmissionStatus, queryGradingStatus, queryGrade));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
