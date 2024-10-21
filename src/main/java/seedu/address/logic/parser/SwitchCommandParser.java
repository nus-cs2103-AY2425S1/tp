package seedu.address.logic.parser;

import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.profile.Profile;

/**
 * Parses input arguments and creates a new {@code SwitchCommand} object.
 */
public class SwitchCommandParser implements Parser<SwitchCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code SwitchCommand}
     * and returns a {@code SwitchCommand} object for execution.
     * If a valid profile name is provided, the command will switch to that profile.
     * If the input is empty, an {@code EmptyProfile} singleton will be returned.
     *
     * @param args The user input representing the profile name.
     * @return A {@code SwitchCommand} object for execution.
     * @throws ParseException if the user input is not the expected format.
     */
    public SwitchCommand parse(String args) throws ParseException {
        if (args.isEmpty()) {
            Profile p = Profile.getEmptyProfile();
            return new SwitchCommand(p);
        }
        Profile profileName = ParserUtil.parseProfileName(args.toLowerCase());
        return new SwitchCommand(profileName);
    }
}
