package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.regex.Pattern;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.UntagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object.
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    private static final Pattern VALID_TAG_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns an UntagCommand object for execution.
     * @param args the user input arguments
     * @return UntagCommand object to be executed
     * @throws ParseException if the user input does not conform to the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_TAG);

        if (argMultimap.getAllValues(PREFIX_TAG).size() > 1) {
            throw new ParseException("Error: More than one 't/' detected. Please use only one 't/' for untagging.");
        }

        if (argMultimap.getValue(PREFIX_TAG).orElse("").trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException("Error: " + Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ".");
        }

        // Handle the case where the user inputs "all" to remove all tags where null set signifies removing all tags
        if (argMultimap.getAllValues(PREFIX_TAG).size() == 1
                && argMultimap.getAllValues(PREFIX_TAG).get(0).equalsIgnoreCase("all")) {

            return new UntagCommand(index, null);
        }

        String untagString = argMultimap.getValue(PREFIX_TAG).orElse("");

        String[] tagsArray = untagString.split("\\s+");
        for (String tag : tagsArray) {
            if (!Tag.isValidTagName(tag)) {
                throw new ParseException("Error: Tags names should be alphanumeric.");
            }
        }

        Set<Tag> tagsToRemove = Tag.stringToTagSet(untagString);

        if (tagsToRemove.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE));
        }

        return new UntagCommand(index, tagsToRemove);
    }



}
