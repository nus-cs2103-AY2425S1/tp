package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.DeleteProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;

/**
 * Parses input arguments and creates a new {@code DeleteProfileCommand} object.
 */
public class DeleteProfileCommandParser implements Parser<DeleteProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProfileCommand
     * and returns a DeleteProfileCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteProfileCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProfileCommand.MESSAGE_USAGE));
        }
        Profile profileName = ParserUtil.parseProfileName(args.toLowerCase());
        return new DeleteProfileCommand(profileName);
    }
}
