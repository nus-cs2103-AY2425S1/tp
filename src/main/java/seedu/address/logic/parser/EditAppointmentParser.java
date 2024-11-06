package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_START_TIME;
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
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NRIC,
                PREFIX_DATE,
                PREFIX_START_TIME,
                PREFIX_NEW_DATE,
                PREFIX_NEW_START_TIME,
                PREFIX_NEW_END_TIME);

        if (!argumentMultimap.getPreamble().equals("")) {
            throw new ParseException("Please do not enter anything before the keywords!\n"
            + "Please remove this from your input: " + argumentMultimap.getPreamble());
        }

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NRIC,
                PREFIX_DATE,
                PREFIX_START_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditAppointmentCommand.MESSAGE_USAGE));
        }

        argumentMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_NRIC,
                PREFIX_DATE,
                PREFIX_START_TIME,
                PREFIX_NEW_DATE,
                PREFIX_NEW_START_TIME,
                PREFIX_NEW_END_TIME);
        Nric originalNric = ParserUtil.parseNric(argumentMultimap.getValue(PREFIX_NRIC).get());
        LocalDate originalDate = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_DATE).get());
        LocalTime originalStartTime = ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_START_TIME).get());

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        if (argumentMultimap.getValue(PREFIX_NEW_DATE).isPresent()) {
            editAppointmentDescriptor.setDate(ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_NEW_DATE).get()));
        }
        if (argumentMultimap.getValue(PREFIX_NEW_START_TIME).isPresent()) {
            editAppointmentDescriptor
                    .setStartTime(ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_NEW_START_TIME).get()));
        }
        if (argumentMultimap.getValue(PREFIX_NEW_END_TIME).isPresent()) {
            editAppointmentDescriptor
                    .setEndTime(ParserUtil.parseTime(argumentMultimap.getValue(PREFIX_NEW_END_TIME).get()));
        }

        LocalDateTime originalStartDateTime = LocalDateTime.of(originalDate, originalStartTime);

        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(originalNric, originalStartDateTime, editAppointmentDescriptor);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
