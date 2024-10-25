package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMPLOYEE_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT_ID;

import java.util.stream.Stream;

import seedu.address.logic.commands.UnassignCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.employee.EmployeeId;
import seedu.address.model.project.ProjectId;


/**
 * Parses input arguments and creates a new UnassignCommand object
 */
public class UnassignCommandParser implements Parser<UnassignCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * UnassignCommand
     * and returns an UnassignCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnassignCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID,
                PREFIX_EMPLOYEE_ID);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID, PREFIX_EMPLOYEE_ID);

        if ((!arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_ID)
                && !arePrefixesPresent(argMultimap, PREFIX_PROJECT_ID, PREFIX_EMPLOYEE_ID))
                || arePrefixesPresent(argMultimap, PREFIX_ASSIGNMENT_ID, PREFIX_PROJECT_ID, PREFIX_EMPLOYEE_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_ASSIGNMENT_ID).isPresent()) {
            AssignmentId assignmentId = ParserUtil.parseAssignmentId(argMultimap.getValue(PREFIX_ASSIGNMENT_ID).get());
            return new UnassignCommand(assignmentId);
        } else if (argMultimap.getValue(PREFIX_PROJECT_ID).isPresent()
                && argMultimap.getValue(PREFIX_EMPLOYEE_ID).isPresent()) {
            ProjectId projectId = ParserUtil.parseProjectId(argMultimap.getValue(PREFIX_PROJECT_ID).get());
            EmployeeId employeeId = ParserUtil.parseEmployeeId(argMultimap.getValue(PREFIX_EMPLOYEE_ID).get());
            return new UnassignCommand(projectId, employeeId);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignCommand.MESSAGE_USAGE));
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
