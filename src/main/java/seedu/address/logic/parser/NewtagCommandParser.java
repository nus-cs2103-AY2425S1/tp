package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.tag.Tag.isValidTagName;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.NewtagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new NewtagCommand object.
 */
public class NewtagCommandParser implements Parser<NewtagCommand> {

    /**
     * Returns a List of Tags with the corresponding names given in the arguments.
     * @param arguments List of arguments as String objects.
     * @return A List of Tag objects with names corresponding to the String objects.
     * @throws ParseException If any of the given arguments are not valid tag names.
     */
    private List<Tag> parseTagsFromArgs(List<String> arguments) throws ParseException {
        List<Tag> argumentsAsTags = new ArrayList<>();
        for (String argument : arguments) {
            requireNonNull(argument);
            if (!isValidTagName(argument)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
            }

            Tag tag = new Tag(argument);
            argumentsAsTags.add(tag);
        }
        return argumentsAsTags;
    }
    /**
     * Parses the given {@code String} of arguments in the context of the NewtagCommand
     * and returns a NewtagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public NewtagCommand parse(String args) throws ParseException {
        String lowerCaseArguments = args.toLowerCase();
        ArgumentMultimap tokenisedArguments = ArgumentTokenizer.tokenize(lowerCaseArguments, PREFIX_TAG);
        List<String> arguments = tokenisedArguments.getAllValues(PREFIX_TAG);

        requireNonNull(arguments);

        if (arguments.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
        }

        List<Tag> tagsToAdd = parseTagsFromArgs(arguments);
        return new NewtagCommand(tagsToAdd);
    }
}
