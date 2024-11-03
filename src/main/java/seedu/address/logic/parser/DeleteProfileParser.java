package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteProfileCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;

/**
 * Parses input arguments and creates a new {@code DeleteProfileCommand} object.
 */
public class DeleteProfileParser implements Parser<DeleteProfileCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProfileCommand
     * and returns a DeleteProfileCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteProfileCommand parse(String args) throws ParseException {
        Profile profileName = ParserUtil.parseProfileName(args.toLowerCase());
        return new DeleteProfileCommand(profileName);
    }
}
