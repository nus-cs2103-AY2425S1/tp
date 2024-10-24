package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_ATTENDEE;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        PREFIX_DATE,
        PREFIX_ATTENDEES,
        PREFIX_REMOVE_ATTENDEE,
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
        int indexToUpdate = parseIndex(argMultimap);
        String name = parseName(argMultimap);
        LocalDate date = parseDate(argMultimap);
        Set<Index> addIndices = parseAttendeeIndices(argMultimap, PREFIX_ATTENDEES);
        Set<Index> removeIndices = parseAttendeeIndices(argMultimap, PREFIX_REMOVE_ATTENDEE);

        return new UpdateCommand(name, date, addIndices, removeIndices, indexToUpdate);
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
        return Stream.of(PREFIX_NAME, PREFIX_DATE, PREFIX_ATTENDEES, PREFIX_REMOVE_ATTENDEE)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }

    /**
     * Parses and returns the index of the event to update.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The index of the event to update.
     * @throws ParseException If index supplied is not a valid integer.
     */
    private int parseIndex(ArgumentMultimap argMultimap) throws ParseException {
        int indexToUpdate = -1;
        try {
            indexToUpdate = Integer.parseInt(argMultimap.getValue(PREFIX_INDEX).get().trim());
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Index must be an integer. \n%1$s",
                    UpdateCommand.MESSAGE_USAGE));
        }
        indexToUpdate -= 1; // subtract 1 because user's event list is not 0-indexed
        return indexToUpdate;
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
     * Parses and returns the new date of the event.
     *
     * @param argMultimap The map of arguments to their values.
     * @return The new date of the event.
     * @throws ParseException If the supplied date format is invalid.
     */
    private LocalDate parseDate(ArgumentMultimap argMultimap) throws ParseException {
        LocalDate date = null;
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            try {
                date = LocalDate.parse(argMultimap.getValue(PREFIX_DATE).get().trim());
            } catch (DateTimeParseException e) {
                throw new ParseException(String.format("Invalid date format. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return date;
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
            String[] attendeeIndicesString = argMultimap.getValue(prefix)
                    .orElse("")
                    .split(" ");
            for (String s : attendeeIndicesString) {
                try {
                    Index attendeeIdx = Index.fromOneBased(Integer.parseInt(s));
                    attendeeIndices.add(attendeeIdx);
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            String.format("Attendee index must be a valid integer. \n%1$s",
                            UpdateCommand.MESSAGE_USAGE));
                }
            }
            if (attendeeIndices.isEmpty()) {
                throw new ParseException(String.format("Attendee list cannot be empty. \n%1$s",
                        UpdateCommand.MESSAGE_USAGE));
            }
        }
        return attendeeIndices;
    }
}
