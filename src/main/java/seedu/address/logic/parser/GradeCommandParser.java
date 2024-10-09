package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GRADE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;

import java.util.stream.Stream;


import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;
import seedu.address.model.person.Grade;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class GradeCommandParser implements Parser<GradeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GradeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_STUDENTID, PREFIX_MODULE, PREFIX_GRADE);

        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENTID, PREFIX_MODULE, PREFIX_GRADE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENTID, PREFIX_MODULE, PREFIX_GRADE);
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENTID).get());
        Grade grade = ParserUtil.parseGrade(argMultimap.getValue(PREFIX_GRADE).get());
        Module module = ParserUtil.parseModule(argMultimap.getValue(PREFIX_MODULE).get());

        return new GradeCommand(studentId, module, grade);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
