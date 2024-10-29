package seedu.address.logic.parser.event.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPONSOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VOLUNTEER;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.commands.AddPersonToEventCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddPersonToEventCommand object
 */
public class AddPersonToEventParser implements Parser<AddPersonToEventCommand> {
    /**
     * Parses the given arguments string and creates an {@code AddPersonToEventCommand}.
     *
     * <p>The method tokenizes the input string into its constituent parts using specified prefixes.
     * It checks for the presence of required prefixes and validates that no unnecessary prefixes
     * are present. If any validation fails, a {@code ParseException} is thrown.</p>
     *
     * <p>This method extracts the event index and sets of attendees, volunteers, vendors,
     * and sponsors based on the parsed input.</p>
     *
     * @param args The input string containing the command arguments, which must include
     *             the necessary prefixes for event index, attendees, volunteers, vendors,
     *             and sponsors.
     *
     * @return An instance of {@code AddPersonToEventCommand} constructed from the parsed
     *         indices and sets.
     *
     * @throws ParseException If any of the following conditions are met:
     *         <ul>
     *         <li>Required prefixes are missing from the input.</li>
     *         <li>The input string contains extra characters that do not correspond to
     *             recognized prefixes.</li>
     *         <li>There are duplicate prefixes present in the input.</li>
     *         <li>Invalid indices are provided for attendees, volunteers, vendors, or
     *             sponsors.</li>
     *         </ul>
     */
    public AddPersonToEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_EVENT_INDEX, PREFIX_ATTENDEE, PREFIX_VOLUNTEER, PREFIX_SPONSOR, PREFIX_VENDOR);

        if (!areNecessaryPrefixesPresent(argMultimap, PREFIX_EVENT_INDEX, PREFIX_ATTENDEE, PREFIX_VOLUNTEER,
                PREFIX_SPONSOR, PREFIX_VENDOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddPersonToEventCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT_INDEX, PREFIX_ATTENDEE,
                PREFIX_VOLUNTEER, PREFIX_SPONSOR, PREFIX_VENDOR);

        Index eventIndex;
        Set<Index> attendees = new HashSet<>();
        Set<Index> volunteers = new HashSet<>();
        Set<Index> vendors = new HashSet<>();
        Set<Index> sponsors = new HashSet<>();

        eventIndex = getEventIndex(argMultimap);
        ParserUtil.parseStringOfIndices(attendees, argMultimap.getValue(PREFIX_ATTENDEE).orElse(null));
        ParserUtil.parseStringOfIndices(volunteers, argMultimap.getValue(PREFIX_VOLUNTEER).orElse(null));
        ParserUtil.parseStringOfIndices(vendors, argMultimap.getValue(PREFIX_VENDOR).orElse(null));
        ParserUtil.parseStringOfIndices(sponsors, argMultimap.getValue(PREFIX_SPONSOR).orElse(null));

        return new AddPersonToEventCommand(eventIndex, attendees, volunteers, vendors, sponsors);
    }

    private static Index getEventIndex(ArgumentMultimap argMultimap) throws ParseException {
        Index eventIndex;

        try {
            eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_EVENT_INDEX).get());
        } catch (ParseException e) {
            throw new ParseException("The Event Index is not a non-zero unsigned integer.");
        }
        return eventIndex;
    }

    private static boolean areNecessaryPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix compulsoryPrefix,
                                                       Prefix... prefixes) {
        boolean a = argumentMultimap.getValue(compulsoryPrefix).isPresent();
        return argumentMultimap.getValue(compulsoryPrefix).isPresent()
                && Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
