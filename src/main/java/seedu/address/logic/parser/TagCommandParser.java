package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

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

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        try {
            // Parse the index from the preamble
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), ive);
        }

        List<String> tagValues = argMultimap.getAllValues(PREFIX_TAG);
        if (tagValues.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        // Convert tag values to Tag objects
        List<Tag> tags = tagValues.stream()
                .map(TagName::new)  // Convert each string to a TagName object
                .map(Tag::new)
                .collect(Collectors.toList());

        return new TagCommand(index, tags);
    }
}
