package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input for filter and creates an instance of FilterCommand.
 */
public class FilterCommandParser implements Parser<FilterCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        // Extract tags and validate them
        Set<Tag> tags = new HashSet<>();
        for (String tagName : argMultimap.getAllValues(PREFIX_TAG)) {
            if (tagName.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            } else if (!Tag.isValidTagName(tagName)) {
                throw new ParseException(Tag.MESSAGE_TAG_NAMES_SHOULD_BE_ALPHANUMERIC);
            }
            tags.add(new Tag(tagName)); // Create Tag objects for each tag provided
        }

        Set<String> names = new HashSet<>();
        for (String name : argMultimap.getAllValues(PREFIX_NAME)) {
            if (name.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            names.add(name.trim()); // Create Tag objects for each tag provided
        }

        if (names.isEmpty() && tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        // Return a new FilterCommand with the parsed name and tags
        return new FilterCommand(names, tags);
    }
}
