package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.profile.Profile;


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
    public static final String COMMAND_ALIAS = "s";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to a different profile based on the provided name.\n"
            + "Parameters: PROFILE_NAME\n"
            + Profile.MESSAGE_CONSTRAINTS + "\n"
            + "Example: " + COMMAND_WORD + " john-doe";
    public static final String MESSAGE_SUCCESS = "Switched to the profile '%1$s'";
    public static final String MESSAGE_SHOW_PROFILES = "Other profiles: %1$s";
    public static final String MESSAGE_NO_SWITCH = "Already on: %1$s";
    public static final String MESSAGE_ILLEGAL_MODIFICATION =
            "We do not support illegal file modifications. Please ensure the profile name fits our constraints:\n"
                    + Profile.MESSAGE_CONSTRAINTS;

    private final Profile profileName;
    public SwitchCommand(Profile profileName) {
        this.profileName = (profileName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // display all available commands
        if (profileName instanceof Profile.EmptyProfile) {
            Set<Profile> profiles = model.getProfiles();
            if (profiles.isEmpty()) {
                throw new CommandException(MESSAGE_USAGE);
            }
            String profilesList = profiles.stream()
                    .map(Profile::toString)
                    .collect(Collectors.joining(", "));
            return new CommandResult(String.format(MESSAGE_SHOW_PROFILES, profilesList));
        }

        Path filePath = Paths.get("data", profileName + ".json");
        // updates the model to track the profile switch
        String curProfileName = getProfileName(model.getAddressBookFilePath());
        if (!Profile.isValidProfile(curProfileName)) {
            throw new CommandException(MESSAGE_ILLEGAL_MODIFICATION);
        }
        if (curProfileName.equals(profileName.toString())) {
            return new CommandResult(String.format(MESSAGE_NO_SWITCH, profileName));
        }
        model.addToProfiles(new Profile(curProfileName));
        model.setAddressBookFilePath(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, profileName)).markProfileSwitched();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SwitchCommand)) {
            return false;
        }

        SwitchCommand otherSwitchCommand = (SwitchCommand) other;
        return profileName.equals(otherSwitchCommand.profileName);
    }

    private static String getProfileName(Path filePath) {
        boolean startsWithData = filePath.startsWith("data");
        boolean singleNameFile = filePath.getNameCount() == 2;
        boolean endsWithJson = filePath.toString().endsWith(".json");
        assert startsWithData && singleNameFile && endsWithJson : "This file path is not supported";
        String fileName = filePath.getFileName().toString();
        return fileName.substring(0, fileName.length() - 5);
    }
}
