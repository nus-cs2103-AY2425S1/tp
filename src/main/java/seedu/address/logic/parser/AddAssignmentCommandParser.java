package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAssignmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {
    @Override
    public AddAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_DEADLINE, PREFIX_STATUS, PREFIX_GRADE
        );

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_DEADLINE, PREFIX_STATUS,
                PREFIX_GRADE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(CliSyntax.PREFIX_DEADLINE).get());
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(
                argMultimap.getValue(CliSyntax.PREFIX_ASSIGNMENT).get());
        List<Status> statusList = ParserUtil.parseStatuses(argMultimap.getAllValues(CliSyntax.PREFIX_STATUS));
        if (statusList.size() != 2) {
            throw new ParseException("Provide exactly 2 status");
        }
        Status submissionStatus = statusList.get(0);
        Status gradingStatus = statusList.get(1);
        Grade grade = ParserUtil.parseGrade(argMultimap.getValue(CliSyntax.PREFIX_GRADE).get());
        return new AddAssignmentCommand(name,
                new Assignment(assignmentName, deadline, submissionStatus, gradingStatus, grade));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
