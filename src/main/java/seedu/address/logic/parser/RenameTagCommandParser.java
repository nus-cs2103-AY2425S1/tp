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
     * Parses the given {@code String} of arguments in the context of the RenameTagCommand
     * and returns a RenameTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public RenameTagCommand parse(String args) throws ParseException {
        String lowerCaseArguments = args.toLowerCase();
        ArgumentMultimap tokenisedArguments = ArgumentTokenizer.tokenize(lowerCaseArguments, PREFIX_TAG);
        List<String> arguments = tokenisedArguments.getAllValues(PREFIX_TAG);

        if (arguments.size() != EXPECTED_ARGUMENT_LENGTH) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        for (String argument : arguments) {
            if (!isValidTagName(argument)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
        }

        Tag existingTag = new Tag(arguments.get(0));
        String newTagName = arguments.get(1);
        return new RenameTagCommand(existingTag, newTagName);
    }
}
