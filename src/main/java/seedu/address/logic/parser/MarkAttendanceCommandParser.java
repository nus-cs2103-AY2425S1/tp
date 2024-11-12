package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_INVALID_ATTENDANCE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkAttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns a MarkAttendanceCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public MarkAttendanceCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EVENT, PREFIX_INDEX);

        if (!argMultimap.getValue(PREFIX_EVENT).isPresent() || !argMultimap.getValue(PREFIX_INDEX).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkAttendanceCommand.MESSAGE_USAGE));
        }

        String eventName = argMultimap.getValue(PREFIX_EVENT).get().trim();

        validateEventName(eventName);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_EVENT);

        if (eventName.isEmpty()) {
            throw new ParseException("Event name cannot be empty.");
        }

        List<String> indexStrings = argMultimap.getAllValues(PREFIX_INDEX);
        List<Index> indices = new ArrayList<>();
        try {
            for (String indexString : indexStrings) {
                String trimmedIndex = indexString.trim();
                Index index = ParserUtil.parseIndex(trimmedIndex);
                indices.add(index);
            }
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_ATTENDANCE_INDEX);
        }

        if (hasDuplicateIndices(indices)) {
            throw new ParseException("Duplicate indices detected. Please specify each student only once.");
        }

        return new MarkAttendanceCommand(eventName, indices);
    }

    private boolean hasDuplicateIndices(List<Index> indices) {
        Set<Integer> uniqueIndexValues = new HashSet<>();
        for (Index index : indices) {
            int zeroBased = index.getZeroBased();
            if (!uniqueIndexValues.add(zeroBased)) {
                return true; // Duplicate found
            }
        }
        return false;
    }

    private void validateEventName(String eventName) throws ParseException {
        if (eventName.contains("/")) {
            throw new ParseException("Event name cannot contain '/'.");
        }
    }


}
