package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.profile.Profile.extractProfileNameFromPathOrThrow;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.IllegalProfileNameException;
import seedu.address.model.profile.exceptions.IllegalProfilePathException;


/**
 * Represents a command to switch to a different user profile in the application.
 * <p>
 * If the provided profile name is valid, this command will switch the current session to the new profile.
 * If no profile name is provided (empty input) and other profiles exist, the command will list all available profiles.
 * If the user attempts to switch to the currently active profile, the command will not perform the switch
 * </p>
 */
public class SwitchCommand extends Command {
    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to a different profile based on the provided name.\n"
            + "Parameters: PROFILE_NAME\n"
            + "Example: " + COMMAND_WORD + " john-doe";
    public static final String MESSAGE_SUCCESS = "Switched to the profile '%1$s'";
    public static final String MESSAGE_SHOW_PROFILES = "Switch to one of the other profiles in this list: [%1$s]\n"
            + "Example: " + COMMAND_WORD + " %2$s";
    public static final String MESSAGE_NO_SWITCH = "Already on: %1$s";
    public static final String MESSAGE_ILLEGAL_MODIFICATION =
            "switch command does not support illegal file modifications."
                    + " Please ensure the existing profile name fits our constraints before switching.\n"
                    + Profile.MESSAGE_CONSTRAINTS;

    private final Profile profile;
    public SwitchCommand(Profile profile) {
        this.profile = profile;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // display help usage or all other profiles
        if (profile instanceof Profile.EmptyProfile) {
            Set<Profile> profiles = model.getProfiles();
            if (profiles.isEmpty()) {
                throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
            }
            List<String> profilesList = profiles.stream()
                    .map(Profile::toPath)
                    .flatMap(Profile::extractNameFromPathOrIgnore)
                    .toList();
            assert !profilesList.isEmpty() : "There is no profile to switch to. Message usage should have been thrown";
            throw new CommandException(String.format(
                    MESSAGE_SHOW_PROFILES,
                    String.join(", ", profilesList),
                    profilesList.get(0)
            ));

        }

        Path filePath = profile.toPath();
        // updates the model to track the profile switch
        String curProfileName;
        try {
            curProfileName = extractProfileNameFromPathOrThrow(model.getAddressBookFilePath());
        } catch (IllegalProfilePathException | IllegalProfileNameException e) {
            throw new CommandException(MESSAGE_ILLEGAL_MODIFICATION);
        }
        if (curProfileName.equals(profile.toString())) {
            throw new CommandException(String.format(MESSAGE_NO_SWITCH, profile));
        }

        //add curProfile to other profiles, then update cur file path.
        model.addToProfiles(new Profile(curProfileName));
        model.setAddressBookFilePath(filePath);

        return new CommandResult(String.format(MESSAGE_SUCCESS, profile)).markProfileSwitched();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwitchCommand otherSwitchCommand)) {
            return false;
        }

        return profile.equals(otherSwitchCommand.profile);
    }

}
