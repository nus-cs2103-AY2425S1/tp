package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.profile.Profile.extractProfileNameFromPathOrThrow;

import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;
import seedu.address.model.profile.exceptions.IllegalProfileNameException;
import seedu.address.model.profile.exceptions.IllegalProfilePathException;

/**
 * Deletes a profile from the system if it exists and is not currently in use.
 */
public class DeleteProfileCommand extends Command {
    public static final String COMMAND_WORD = "deleteProfile";
    public static final String COMMAND_ALIAS = "delp";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes an existing profile based on the provided name.\n"
            + "Parameters: PROFILE_NAME\n"
            + "Example: " + COMMAND_WORD + " john-doe";
    public static final String MESSAGE_SUCCESS = "Successfully deleted the profile '%1$s'";
    public static final String MESSAGE_NO_SUCH_PROFILE = "Profile not found. Unable to delete a non-existing profile.";
    public static final String MESSAGE_IN_USE = "Unable to delete the profile currently in use. "
            + "Please switch to a different profile before attempting deletion.";
    public static final String MESSAGE_ILLEGAL_MODIFICATION =
            "deleteProfile command does not support illegal file modifications."
                    + " Please ensure the existing profile name fits our constraints before using this command.\n"
                    + Profile.MESSAGE_CONSTRAINTS;

    private final Profile profileName;
    public DeleteProfileCommand(Profile profileName) {
        this.profileName = (profileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        String curProfileName;
        try {
            curProfileName = extractProfileNameFromPathOrThrow(model.getAddressBookFilePath());
        } catch (IllegalProfilePathException | IllegalProfileNameException e) {
            throw new CommandException(MESSAGE_ILLEGAL_MODIFICATION);
        }
        if (curProfileName.equals(profileName.toString())) {
            throw new CommandException(MESSAGE_IN_USE);
        }
        Set<Profile> profiles = model.getProfiles();
        if (!profiles.contains(profileName)) {
            throw new CommandException(MESSAGE_NO_SUCH_PROFILE);
        }

        model.removeFromProfiles(profileName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, profileName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteProfileCommand otherDeleteProfile)) {
            return false;
        }

        return profileName.equals(otherDeleteProfile.profileName);
    }

}
