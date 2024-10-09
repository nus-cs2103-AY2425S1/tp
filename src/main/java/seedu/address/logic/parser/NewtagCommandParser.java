package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.NewtagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new NewtagCommand object.
 */
public class NewtagCommandParser implements Parser<NewtagCommand> {
    public static final int MAX_LENGTH = 50;
    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]+";

    /**
     * Parses the given {@code String} of arguments in the context of the NewtagCommand
     * and returns a NewtagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public NewtagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        // Check if the input is empty, exceeds the maximum length,
        // or contains non-alphanumeric characters other than spaces.
        boolean isEmpty = trimmedArgs.isEmpty();
        boolean isTooLong = trimmedArgs.length() > MAX_LENGTH;
        boolean isAlphanumeric = trimmedArgs.matches(VALIDATION_REGEX);
        if (isEmpty || isTooLong || !isAlphanumeric) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, NewtagCommand.MESSAGE_USAGE));
        }

        // Create and return the NewtagCommand.
        Tag tag = new Tag(trimmedArgs);
        return new NewtagCommand(tag);
    }
}
