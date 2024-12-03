package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRESENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_NUMBER;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.student.Name;
import seedu.address.model.student.StudentNumber;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {


    @Override
    public MarkAttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                CliSyntax.PREFIX_NAME,
                CliSyntax.PREFIX_STUDENT_NUMBER,
                CliSyntax.PREFIX_DATE,
                CliSyntax.PREFIX_PRESENT
        );
        argMultimap.verifyNoInvalidPrefixesFor(args);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_STUDENT_NUMBER, PREFIX_DATE, PREFIX_PRESENT);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE, PREFIX_PRESENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Attendance attendance = ParserUtil.parseAttendance(argMultimap.getValue(PREFIX_PRESENT).get());
        Optional<StudentNumber> studentNumber = argMultimap.getValue(PREFIX_STUDENT_NUMBER).isPresent()
                ? Optional.of(ParserUtil.parseStudentNumber(argMultimap.getValue(PREFIX_STUDENT_NUMBER).get()))
                : Optional.empty();

        return new MarkAttendanceCommand(name, date, attendance, studentNumber);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
