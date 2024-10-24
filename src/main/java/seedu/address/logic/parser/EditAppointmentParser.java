package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FIND_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditAppointmentParser implements Parser<EditAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FIND_NRIC,
                PREFIX_FIND_DATE,
                PREFIX_FIND_START_TIME,
                PREFIX_NRIC,
                PREFIX_DATE,
                PREFIX_START_TIME,
                PREFIX_END_TIME);
        if (!argumentMultimap.getValue(PREFIX_FIND_NRIC).isPresent()) {
            System.out.println("Find NRIC not found");
        }
        if (!argumentMultimap.getValue(PREFIX_FIND_DATE).isPresent()) {
            System.out.println("Find date not found");
        }
        if (!argumentMultimap.getValue(PREFIX_FIND_START_TIME).isPresent()) {
            System.out.println("Find start time not found");
        }
        if (!argumentMultimap.getValue(PREFIX_NRIC).isPresent()) {
            System.out.println("NRIC not found");
        }
        if (!argumentMultimap.getValue(PREFIX_DATE).isPresent()) {
            System.out.println("Date not found");
        }
        if (!argumentMultimap.getValue(PREFIX_START_TIME).isPresent()) {
            System.out.println("Start time not found");
        }
        if (!argumentMultimap.getValue(PREFIX_END_TIME).isPresent()) {
            System.out.println("End time not found");
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NRIC, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME)) {
            System.out.println("Prefixes not found");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FIND_NRIC,
                PREFIX_FIND_DATE,
                PREFIX_FIND_START_TIME,
                PREFIX_NRIC,
                PREFIX_DATE,
                PREFIX_START_TIME,
                PREFIX_END_TIME);
        Nric findNric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_FIND_NRIC).get());
        LocalDate findDate = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_FIND_DATE).get());
        LocalTime findStartTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_FIND_START_TIME).get());
        Nric nric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_NRIC).get());
        LocalDate date = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_DATE).get());
        LocalTime startTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_START_TIME).get());
        LocalTime endTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_END_TIME).get());

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        editAppointmentDescriptor.setNric(nric);
        editAppointmentDescriptor.setStartTime(LocalDateTime.of(date, startTime));
        editAppointmentDescriptor.setEndTime(LocalDateTime.of(date, endTime));

        LocalDateTime findStartDateTime = LocalDateTime.of(findDate, findStartTime);

        return new EditAppointmentCommand(findNric, findStartDateTime, editAppointmentDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
