package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_COURSE_CODE;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_MATRICULATION_NUMBER;
import static tahub.contacts.logic.parser.CliSyntax.PREFIX_TUTORIAL_ID;

import java.util.stream.Stream;

import tahub.contacts.logic.commands.EnrollCommand;
import tahub.contacts.logic.parser.exceptions.ParseException;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.person.MatriculationNumber;


/**
 * Parses input arguments and creates a new EnrollCommand object
 */
public class EnrollCommandParser implements Parser<EnrollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EnrollCommand
     * and returns an EnrollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnrollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MATRICULATION_NUMBER, PREFIX_COURSE_CODE,
                        PREFIX_TUTORIAL_ID);

        if (!arePrefixesPresent(argMultimap, PREFIX_MATRICULATION_NUMBER, PREFIX_COURSE_CODE,
                PREFIX_TUTORIAL_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrollCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_MATRICULATION_NUMBER, PREFIX_COURSE_CODE,
                PREFIX_TUTORIAL_ID);


        // Extract key parameters from user input
        MatriculationNumber matricNumber = ParserUtil.parseMatriculationNumber(argMultimap
                .getValue(PREFIX_MATRICULATION_NUMBER).get());
        CourseCode courseCode = ParserUtil.parseCourseCode(argMultimap
                .getValue(PREFIX_COURSE_CODE).get());
        String tutorialId = ParserUtil.parseTutorialId(argMultimap
                .getValue(PREFIX_TUTORIAL_ID).get());

        return new EnrollCommand(matricNumber, courseCode, tutorialId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
