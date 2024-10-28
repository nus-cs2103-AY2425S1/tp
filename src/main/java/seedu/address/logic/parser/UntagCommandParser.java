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

        Set<Index> indexSet = new HashSet<>();
        Set<Tag> tagSet = new HashSet<>();
        List<String> tagStrings = argMultimap.getAllValues(PREFIX_TAG);
        if (tagStrings.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        try {
            List<String> indexStrings = List.of(argMultimap.getPreamble().trim().split("\\s+"));
            boolean isValidIndexes = indexStrings.size() != 1 || !indexStrings.get(0).isEmpty();
            if (!isValidIndexes) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
            }
            checkIndexLength(indexStrings);
            addIndexToSet(indexStrings, indexSet);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        for (String tagString : tagStrings) {
            tagString = tagString.trim().toLowerCase();
            boolean isEmpty = tagString.isEmpty();
            boolean isTooLong = tagString.length() > Tag.MAX_CHARACTER_LENGTH;
            if (isEmpty) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
            } else if (isTooLong) {
                throw new ParseException(String.format(Messages.MESSAGE_INPUT_LENGTH_EXCEEDED,
                        Tag.MAX_CHARACTER_LENGTH));
            }
            tagSet.add(new Tag(tagString));
        }

        return new UntagCommand(new ArrayList<>(indexSet), tagSet);
    }

    private void checkIndex(String indexStr) throws ParseException {
        if (!indexStr.matches(VALIDATION_REGEX)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UntagCommand.MESSAGE_USAGE));
        }
    }

    private void checkIndexLength(List<String> indexStrings) throws ParseException {
        if (indexStrings.size() > Index.MAX_INDEXES) {
            throw new ParseException(String.format(Messages.MESSAGE_TOO_MANY_INDEXES, Index.MAX_INDEXES));
        }
    }

    private void addIndexToSet(List<String> indexStrings, Set<Index> indexSet) throws ParseException {
        for (String indexStr : indexStrings) {
            if (!indexStr.isEmpty()) {
                checkIndex(indexStr);
                indexSet.add(ParserUtil.parseIndex(indexStr.trim()));
            }
        }
    }
}
