package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static final int MAX_INDEXES = 10;

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns a TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        List<String> tagStrings = argMultimap.getAllValues(PREFIX_TAG);
        if (tagStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        try {
            List<String> indexStrings = List.of(argMultimap.getPreamble().trim().split("\\s+"));
            if (indexStrings.size() == 1 && indexStrings.get(0).isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            }
            checkIndexLength(indexStrings);

            for (String indexStr : indexStrings) {
                if (!indexStr.isEmpty()) {
                    checkIndex(indexStr);
                    indexSet.add(ParserUtil.parseIndex(indexStr.trim()));
                }
            }
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        for (String tagString : tagStrings) {
            tagString = tagString.trim().toLowerCase();
            boolean isEmpty = tagString.isEmpty();
            boolean isTooLong = tagString.length() > MAX_LENGTH;
            if (isEmpty) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
            } else if (isTooLong) {
                throw new ParseException(String.format(Messages.MESSAGE_INPUT_LENGTH_EXCEEDED, MAX_LENGTH));
            }
            tagSet.add(new Tag(tagString));
        }

        return new TagCommand(new ArrayList<>(indexSet), tagSet);
    }

    private void checkIndex(String indexStr) throws ParseException {
        if (!indexStr.matches("\\d+")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    TagCommand.MESSAGE_USAGE));
        }
    }

    private void checkIndexLength(List<String> indexStrings) throws ParseException {
        if (indexStrings.size() > MAX_INDEXES) {
            throw new ParseException(String.format(Messages.MESSAGE_TOO_MANY_INDEXES, MAX_INDEXES));
        }
    }
}
