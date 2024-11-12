package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RenameTagCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.List;

import seedu.address.logic.commands.RenameTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new RenameTagCommand object.
 */
public class RenameTagCommandParser implements Parser<RenameTagCommand> {
    public static final int EXPECTED_ARGUMENT_LENGTH = 2;

    /**
     * Validates the given argument list to ensure there are exactly two.
     *
     * @param arguments The argument list to be validated.
     * @throws ParseException If the number of arguments provided is not exactly two.
     */
    private void validateArgumentLength(List<String> arguments) throws ParseException {
        if (arguments.size() != EXPECTED_ARGUMENT_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

    /**
     * Validates the given tag names to ensure they adhere to the tag naming conventions.
     *
     * @param tagNames The name of the tags to validate.
     * @throws ParseException If any of the provided tag names is invalid.
     */
    private void validateTagNames(List<String> tagNames) throws ParseException {
        for (String tagName : tagNames) {
            if (!isValidTagName(tagName)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }
    }

    /**
     * Parses a string of arguments into a list of strings based on the specified prefix.
     *
     * @param arguments The raw input string containing the arguments.
     * @return A list of strings extracted from the input arguments, corresponding to the tag names.
     */
    private List<String> parseArgumentsToList(String arguments) throws ParseException {
        if (!arguments.trim().matches("^(t/.*)?$")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
        String lowerCaseArguments = arguments.toLowerCase();
        ArgumentMultimap tokenisedArguments = ArgumentTokenizer.tokenize(lowerCaseArguments, PREFIX_TAG);
        List<String> argumentsAsList = tokenisedArguments.getAllValues(PREFIX_TAG);
        return argumentsAsList;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the RenameTagCommand
     * and returns a RenameTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RenameTagCommand parse(String args) throws ParseException {
        List<String> arguments = parseArgumentsToList(args);

        validateArgumentLength(arguments);
        validateTagNames(arguments);

        Tag existingTag = new Tag(arguments.get(0));
        String newTagName = arguments.get(1);
        return new RenameTagCommand(existingTag, newTagName);
    }
}
