package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        Set<Index> indices = new HashSet<>();

        String[] parts = args.trim().split("\\s+");
        for (String part : parts) {
            if (part.contains("-")) {
                // Handle range
                String[] range = part.split("-");
                if (range.length != 2) {
                    throw new ParseException(
                            DeleteCommand.MESSAGE_FULL_RANGE);
                }
                try {
                    int start = Integer.parseInt(range[0].trim());
                    int end = Integer.parseInt(range[1].trim());
                    if (start <= 0 || end <= 0) {
                        throw new ParseException(
                                DeleteCommand.MESSAGE_INVALID_INPUT);
                    }
                    // Check for valid range
                    if (start > end) {
                        throw new ParseException(
                                DeleteCommand.MESSAGE_INVALID_RANGE);
                    }
                    for (int i = start; i <= end; i++) {
                        indices.add(ParserUtil.parseIndex(String.valueOf(i)));
                    }
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            DeleteCommand.MESSAGE_INVALID_INPUT);
                }
            } else {
                // Handle individual index
                try {
                    int index = Integer.parseInt(part.trim());
                    if (index <= 0) {
                        throw new ParseException(
                                DeleteCommand.MESSAGE_INVALID_INPUT);
                    }
                    indices.add(ParserUtil.parseIndex(part.trim()));
                } catch (NumberFormatException e) {
                    throw new ParseException(
                            DeleteCommand.MESSAGE_INVALID_INPUT);
                }
            }
        }
        return new DeleteCommand(indices.stream().collect(Collectors.toList()));
    }
}
