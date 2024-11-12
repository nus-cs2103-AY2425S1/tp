package tutorease.address.logic.parser;

import static tutorease.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_FEE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static tutorease.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static tutorease.address.logic.parser.ParserUtil.validatePrefixesPresent;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.logic.commands.AddLessonCommand;
import tutorease.address.logic.parser.exceptions.ParseException;
import tutorease.address.model.lesson.EndDateTime;
import tutorease.address.model.lesson.Fee;
import tutorease.address.model.lesson.StartDateTime;
import tutorease.address.model.lesson.StudentId;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {
    private static Logger logger = LogsCenter.getLogger(AddLessonCommandParser.class);
    @Override
    public AddLessonCommand parse(String args) throws ParseException {
        logger.log(Level.INFO, "Parsing AddLessonCommand with args: " + args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_FEE,
                PREFIX_START_DATE, PREFIX_DURATION);
        validatePrefixesPresent(argMultimap, AddLessonCommand.MESSAGE_USAGE, PREFIX_STUDENT_ID, PREFIX_FEE,
                PREFIX_START_DATE, PREFIX_DURATION);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_FEE, PREFIX_START_DATE, PREFIX_DURATION);

        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        Fee fee = ParserUtil.parseFee(argMultimap.getValue(PREFIX_FEE).get());
        StartDateTime startDateTime = ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATE).get());
        EndDateTime endDateTime = ParserUtil.parseEndDateTime(startDateTime,
                argMultimap.getValue(PREFIX_DURATION).get());

        return new AddLessonCommand(studentId, fee, startDateTime, endDateTime);
    }
}
