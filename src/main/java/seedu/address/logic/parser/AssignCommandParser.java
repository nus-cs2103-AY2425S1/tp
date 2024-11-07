package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.ProjectId;

/**
 * Parses input arguments and creates a new AssignCommand object
 */
public class AssignCommandParser implements Parser<AssignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AssignCommand
     * and returns an AssignCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID,
                PREFIX_EMPLOYEE_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID, PREFIX_EMPLOYEE_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID, PREFIX_EMPLOYEE_ID);

        AssignmentId assignmentId = ParserUtil.parseAssignmentId(argMultimap.getValue(PREFIX_ASSIGNMENT_ID).get());
        ProjectId projectId = ParserUtil.parseProjectId(argMultimap.getValue(PREFIX_PROJECT_ID).get());
        EmployeeId employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEE_ID).get());

        return new AssignCommand(assignmentId, projectId, employeeId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
