package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

/**
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given String of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     *
     * @param args the user input string containing the index and tags to be added
     * @return a new TagCommand object that contains the parsed index and list of tags
     * @throws ParseException if the input does not conform to the expected format (i.e., invalid index or missing tags)
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Trim and split input to separate index from tags
        String trimmedArgs = args.trim();
        String[] splitArgs = trimmedArgs.split("\\s+", 2);

        if (splitArgs.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), ive);
        }

        String[] tagValues = splitArgs[1].split("\\s+");
        if (tagValues.length == 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        List<Tag> tags = Arrays.stream(tagValues)
                .map(TagName::new) // Convert each string to a TagName object
                .map(Tag::new)
                .collect(Collectors.toList());

        return new TagCommand(index, tags);
    }
}
