package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String userInput) throws ParseException {
        System.out.println("got input" + userInput);

        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(userInput, PREFIX_NRIC,
            PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME);

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
                AddAppointmentCommand.MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_DATE, PREFIX_START_TIME, PREFIX_END_TIME);

        Nric nric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_NRIC).get());
        LocalDate date = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_DATE).get());
        LocalTime startTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_START_TIME).get());
        LocalTime endTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_END_TIME).get());

        return new AddAppointmentCommand(nric, date, startTime, endTime);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
