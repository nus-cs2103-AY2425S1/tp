package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object.
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    /**
     * Parses the given String of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     *
     * @param args the user input string containing the index and tags to be removed
     * @return a new UntagCommand object that contains the parsed index and list of tags
     * @throws ParseException if the input does not conform to the expected format (i.e., invalid index or missing tags)
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim and split input to separate index from tags
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+", 2);

        if (splitArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            // Parse the index from the first part of the split arguments
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE), ive);
        }

        // Split the second part (tags) by whitespace
        String[] tagValues = splitArgs[1].split("\\s+");
        if (tagValues.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        // Convert tag values to Tag objects
        List<Tag> tags = Arrays.stream(tagValues)
                .map(Tag::new)
                .collect(Collectors.toList());

        return new UntagCommand(index, tags);
    }
}
