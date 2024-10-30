package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.NewtagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code NewtagCommand} object.
 */
public class NewtagCommandParser implements Parser<NewtagCommand> {

    /**
     * Parses a list of string arguments and converts them into a list of {@code Tag} objects.
     *
     * @param arguments A list of arguments as String objects, representing tag names.
     * @return A list of {@code Tag} objects corresponding to the provided tag names.
     * @throws ParseException If any of the given arguments are not valid tag names,
     *                       as determined by the {@code isValidTagName} method.
     */
    private List<Tag> parseTagsFromArgs(List<String> arguments) throws ParseException {
        List<Tag> argumentsAsTags = new ArrayList<>();
        for (String argument : arguments) {
            validateTagName(argument); // Validate each tag name before creating Tag object
            Tag tag = new Tag(argument);
            argumentsAsTags.add(tag);
        }
        return argumentsAsTags;
    }

    /**
     * Validates the given tag name to ensure it adheres to the tag naming conventions.
     *
     * @param tagName The name of the tag to validate.
     * @throws ParseException If the provided tag name is invalid.
     */
    private void validateTagName(String tagName) throws ParseException {
        if (!isValidTagName(tagName)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Validates the length of the provided list of arguments.
     *
     * @param arguments The list of arguments to validate.
     * @throws ParseException If the list of arguments is empty, indicating no tags were provided.
     */
    private void validateArgumentLength(List<String> arguments) throws ParseException {
        if (arguments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses a string of arguments into a list of strings based on the specified prefix.
     *
     * @param arguments The raw input string containing the arguments.
     * @return A list of strings extracted from the input arguments, corresponding to the tag names.
     */
    private List<String> parseArgumentsToList(String arguments) {
        String lowerCaseArguments = arguments.toLowerCase();
        ArgumentMultimap tokenisedArguments = ArgumentTokenizer.tokenize(lowerCaseArguments, PREFIX_TAG);
        List<String> argumentsAsList = tokenisedArguments.getAllValues(PREFIX_TAG);
        return argumentsAsList;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the {@code NewtagCommand}
     * and returns a {@code NewtagCommand} object for execution.
     *
     * @param args The input string containing the arguments to parse.
     * @return A {@code NewtagCommand} object containing the tags to be added.
     * @throws ParseException If the user input does not conform to the expected format,
     *                       including invalid tag names or lack of arguments.
     */
    public NewtagCommand parse(String args) throws ParseException {
        List<String> arguments = parseArgumentsToList(args);

        requireAllNonNull(arguments);
        validateArgumentLength(arguments);

        List<Tag> tagsToAdd = parseTagsFromArgs(arguments);
        return new NewtagCommand(tagsToAdd);
    }
}
