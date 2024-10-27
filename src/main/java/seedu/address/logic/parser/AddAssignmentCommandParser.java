package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

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
import seedu.address.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new AddAssignmentCommand object
 */
public class AddAssignmentCommandParser implements Parser<AddAssignmentCommand> {

    public static final String MESSAGE_EXPECTED_GRADE = "Expected a grade if grading has already been done\n"
            + AddAssignmentCommand.MESSAGE_USAGE;



    @Override
    public AddAssignmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_DEADLINE, PREFIX_STATUS, PREFIX_GRADE, PREFIX_STUDENT_NUMBER
        );

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ASSIGNMENT, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAssignmentCommand.MESSAGE_USAGE));
        }

        // Initializing compulsory fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        AssignmentName assignmentName = ParserUtil.parseAssignmentName(
                argMultimap.getValue(PREFIX_ASSIGNMENT).get());

        // Initializing non-compulsory fields
        List<Status> statusList = ParserUtil.parseStatuses(argMultimap.getAllValues(PREFIX_STATUS), 2);
        Status submissionStatus = statusList.get(0);
        Status gradingStatus = statusList.get(1);

        Grade grade;
        if (gradingStatus.isGraded()) {
            grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE)
                    .orElseThrow(() -> new ParseException(MESSAGE_EXPECTED_GRADE)
            ));
        } else {
            grade = Grade.getDefault();
        }

        if (argMultimap.getValue(PREFIX_STUDENT_NUMBER).isPresent()) {
            StudentNumber studentNumber =
                    ParserUtil.parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get());
            return new AddAssignmentCommand(name,
                    new Assignment(assignmentName, deadline, submissionStatus, gradingStatus, grade),
                    studentNumber);
        }

        return new AddAssignmentCommand(name,
                new Assignment(assignmentName, deadline, submissionStatus, gradingStatus, grade));
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
