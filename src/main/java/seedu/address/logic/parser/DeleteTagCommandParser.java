package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new DeleteTagCommand object.
 */
public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {
    public static final int MAX_LENGTH = 50;
    public static final String VALIDATION_REGEX = "[\\p{Alnum}'() ]+";
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase();

        // Check if the input is empty, exceeds the maximum length,
        // or contains non-alphanumeric characters other than spaces.
        boolean isEmpty = trimmedArgs.isEmpty();
        boolean isTooLong = trimmedArgs.length() > MAX_LENGTH;
        boolean isValidCharacters = trimmedArgs.matches(VALIDATION_REGEX);
        if (isEmpty || isTooLong || !isValidCharacters) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        Tag tag = new Tag(trimmedArgs);
        return new DeleteTagCommand(tag);
    }
}
