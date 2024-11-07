package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.TagCommand.MESSAGE_INVALID_INDEX_OR_STRING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @param args The user input arguments.
     * @return A new TagCommand object.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public TagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        if (argMultimap.getPreamble().isEmpty() || argMultimap.getAllValues(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getPreamble().equalsIgnoreCase("all")) {
            Set<Tag> tagsToAdd = parseTagsForAdd(argMultimap.getAllValues(PREFIX_TAG));
            return new TagCommand("all", tagsToAdd);
        } else {
            // Parses the index after format verification
            Index index;
            try {
                index = ParserUtil.parseIndex(argMultimap.getPreamble());
            } catch (CommandException | ParseException e) {
                throw new ParseException(String.format(MESSAGE_INVALID_INDEX_OR_STRING));
            }

            Set<Tag> tagsToAdd = parseTagsForAdd(argMultimap.getAllValues(PREFIX_TAG));

            return new TagCommand(index, tagsToAdd);
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contains an empty string, it will throw a {@code ParseException}.
     * @param tags The collection of tag strings.
     * @return A set of tags.
     * @throws ParseException If any tag is invalid.
     */
    private Set<Tag> parseTagsForAdd(Collection<String> tags) throws ParseException {
        assert tags != null;

        for (String tag : tags) {
            if (tag.trim().isEmpty()) {
                throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
            }
        }

        return ParserUtil.parseTags(tags);
    }
}
