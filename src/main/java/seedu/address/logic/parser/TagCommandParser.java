package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {
    public static final int MAX_LENGTH = 50;

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TAG).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        String trimmedTagName = argMultimap.getValue(PREFIX_TAG).get().trim().toLowerCase();
        boolean isEmpty = trimmedTagName.isEmpty();
        boolean isTooLong = trimmedTagName.length() > MAX_LENGTH;

        if (isEmpty) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        if (isTooLong) {
            throw new ParseException(Messages.MESSAGE_INPUT_LENGTH_EXCEEDED);
        }

        Tag tag = new Tag(trimmedTagName);

        return new TagCommand(index, tag);
    }
}
