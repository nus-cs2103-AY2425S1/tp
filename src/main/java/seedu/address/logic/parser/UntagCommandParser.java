package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object
 */
public class UntagCommandParser implements Parser<UntagCommand> {
    public static final String VALIDATION_REGEX = "\\d+";

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns a UntagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);

        List<String> tagStrings = argMultimap.getAllValues(PREFIX_TAG);
        if (tagStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        Set<Index> indexes = parseIndexes(argMultimap.getPreamble());
        Set<Tag> tags = parseTags(argMultimap.getAllValues(PREFIX_TAG));

        return new UntagCommand(new ArrayList<>(indexes), tags);
    }

    private Set<Index> parseIndexes(String input) throws ParseException {
        Set<Index> indexSet = new HashSet<>();
        List<String> indexStrs = List.of(input.trim().split("\\s+"));
        checkIndexesValidity(indexStrs);
        checkIndexLength(indexStrs);
        addIndexToSet(indexStrs, indexSet);
        return indexSet;
    }

    private Set<Tag> parseTags(List<String> tagStrings) throws ParseException {
        Set<Tag> tags = new HashSet<>();
        for (String tagString : tagStrings) {
            String trimmedTag = tagString.trim().toLowerCase();
            validateTagName(trimmedTag);
            tags.add(new Tag(trimmedTag));
        }
        return tags;
    }

    private void validateTagName(String tagName) throws ParseException {
        if (!isValidTagName(tagName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }
    }

    private void checkIndex(String indexStr) throws ParseException {
        if (!indexStr.matches(VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }
    }

    private void checkIndexLength(List<String> indexStrings) throws ParseException {
        if (indexStrings.size() > Index.MAX_INDEXES) {
            throw new ParseException(String.format(Messages.MESSAGE_TOO_MANY_INDEXES, Index.MAX_INDEXES));
        }
    }

    private void checkIndexesValidity(List<String> indexStrs) throws ParseException {
        boolean isValidIndexes = indexStrs.size() != 1 || !indexStrs.get(0).isEmpty();
        if (!isValidIndexes) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }
    }

    private void addIndexToSet(List<String> indexStrs, Set<Index> indexSet) throws ParseException {
        for (String indexStr : indexStrs) {
            if (!indexStr.isEmpty()) {
                checkIndex(indexStr);
                indexSet.add(ParserUtil.parseIndex(indexStr));
            }
        }
    }
}
