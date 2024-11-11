package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * List of valid arguments for the {@code update} command.
     */
    private static final Prefix[] VALID_ARG_LIST = {
        PREFIX_INDEX,
        PREFIX_NAME,
        PREFIX_START_DATE,
        PREFIX_END_DATE,
        PREFIX_ATTENDEES,
        PREFIX_REMOVE_ATTENDEE,
        PREFIX_LOCATION
    };

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        // Tokenize and check for duplicate prefixes
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, VALID_ARG_LIST);
        argMultimap.verifyNoDuplicatePrefixesFor(VALID_ARG_LIST);

        // Only index of the event to update is mandatory
        // The rest of the fields are optional
        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateCommand.MESSAGE_USAGE));
        }

        // Throw an error if user specified index of event to update but
        // did not provide any new fields
        if (hasNoPrefixesSupplied(argMultimap)) {
            throw new ParseException(String.format("No new field was provided. \n%1$s",
                    UpdateCommand.MESSAGE_USAGE));
        }

        // Parse input and create an UpdateCommand
        Index indexToUpdate = parseIndex(argMultimap);
        String name = parseName(argMultimap);
        LocalDate startDate = parseStartDate(argMultimap);
        LocalDate endDate = parseEndDate(argMultimap);
        if (startDate != null && endDate != null) {
            ParserUtil.checkValidDates(startDate, endDate);
        }
        Address location = parseLocation(argMultimap);
        Set<Index> addIndices = parseAttendeeIndices(argMultimap, PREFIX_ATTENDEES);
        Set<Index> removeIndices = parseAttendeeIndices(argMultimap, PREFIX_REMOVE_ATTENDEE);

        return new UpdateCommand(name, startDate, endDate, location, addIndices, removeIndices, indexToUpdate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if no prefixes other than the event index has been supplied.
     */
    private boolean hasNoPrefixesSupplied(ArgumentMultimap argumentMultimap) {
        return Stream.of(VALID_ARG_LIST)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

    /**
     * Parses and returns the index of the event to update.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The index of the event to update.
     * @throws ParseException If index supplied is not a valid integer.
     */
    private Index parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get().trim());
    }

    /**
     * Parses and returns the new name of the event.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The new name of the event.
     * @throws ParseException If the supplied name is empty.
     */
    private String parseName(ArgumentMultimap argMultimap) throws ParseException {
        String name = "";
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            name = argMultimap.getValue(PREFIX_NAME).orElse("").trim();
            if (name.isEmpty()) {
                throw new ParseException(String.format("Event name cannot be empty. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return name;
    }

    /**
     * Parses and returns the new start date of the event.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The new start date of the event.
     * @throws ParseException If the supplied date format is invalid.
     */
    private LocalDate parseStartDate(ArgumentMultimap argMultimap) throws ParseException {
        LocalDate startDate = null;
        if (arePrefixesPresent(argMultimap, PREFIX_START_DATE)) {
            try {
                startDate = LocalDate.parse(argMultimap.getValue(PREFIX_START_DATE).get().trim());
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format("Invalid date format. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return startDate;
    }

    /**
     * Parses and returns the new end date of the event.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The new start date of the event.
     * @throws ParseException If the supplied date format is invalid.
     */
    private LocalDate parseEndDate(ArgumentMultimap argMultimap) throws ParseException {
        LocalDate endDate = null;
        if (arePrefixesPresent(argMultimap, PREFIX_END_DATE)) {
            try {
                endDate = LocalDate.parse(argMultimap.getValue(PREFIX_END_DATE).get().trim());
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format("Invalid date format. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return endDate;
    }

    /**
     * Parses and returns the new location of the event.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The new location of the event.
     * @throws ParseException If the supplied location is empty or invalid.
     */
    private Address parseLocation(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_LOCATION).isPresent()) {
            try {
                return ParserUtil.parseAddress(argMultimap.getValue(PREFIX_LOCATION).get());
            } catch (ParseException e) {
                throw new ParseException(String.format("Invalid location format. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE), e);
            }
        }
        return null;
    }

    /**
     * Parses and returns the set of attendee indices to be added or deleted.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The set of attendee indices to be added or deleted.
     * @throws ParseException If attendee list is empty or
     *     the supplied index is not a valid integer.
     */
    private Set<Index> parseAttendeeIndices(ArgumentMultimap argMultimap, Prefix prefix)
            throws ParseException {
        Set<Index> attendeeIndices = new HashSet<>();
        if (arePrefixesPresent(argMultimap, prefix)) {
            attendeeIndices.addAll(ParserUtil.parseIndexes(argMultimap.getValue(prefix).orElse("")));
            if (attendeeIndices.isEmpty()) {
                throw new ParseException(String.format("Attendee list cannot be empty. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return attendeeIndices;
    }
}
