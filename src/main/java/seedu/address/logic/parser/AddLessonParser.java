package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import java.util.stream.Stream;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.EndDateTime;
import seedu.address.model.lesson.LocationIndex;
import seedu.address.model.lesson.StartDateTime;
import seedu.address.model.lesson.StudentId;

public class AddLessonParser implements Parser<AddLessonCommand> {

    @Override
    public AddLessonCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_STUDENT_ID, PREFIX_LOCATION_INDEX, PREFIX_START_DATE, PREFIX_DURATION);
        if (!arePrefixesPresent(argMultimap, PREFIX_STUDENT_ID, PREFIX_START_DATE, PREFIX_DURATION) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_STUDENT_ID, PREFIX_LOCATION_INDEX, PREFIX_START_DATE, PREFIX_DURATION);
        StudentId studentId = ParserUtil.parseStudentId(argMultimap.getValue(PREFIX_STUDENT_ID).get());
        LocationIndex locationId = ParserUtil.parseLocationIndex(argMultimap.getValue(PREFIX_LOCATION_INDEX).get());
        StartDateTime startDateTime = ParserUtil.parseStartDateTime(argMultimap.getValue(PREFIX_START_DATE).get());
        EndDateTime endDateTime = ParserUtil.parseEndDateTime(startDateTime, argMultimap.getValue(PREFIX_DURATION).get());

        return new AddLessonCommand(studentId, startDateTime, locationId, endDateTime);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
