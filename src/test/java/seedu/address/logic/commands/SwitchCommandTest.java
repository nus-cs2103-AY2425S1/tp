package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.profile.Profile;
public class SwitchCommandTest {
    @Test
    public void execute_switchToValidDifferentProfile_success() throws CommandException {
        Profile validProfile = new Profile("new-profile");
        Model model = new ModelManager();
        model.addToProfiles(new Profile("current-profile"));
        model.setAddressBookFilePath(Profile.profileStringToPath("current-profile"));

        SwitchCommand switchCommand = new SwitchCommand(validProfile);
        CommandResult result = switchCommand.execute(model);

        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "new-profile"), result.getFeedbackToUser());
    }

    @Test
    public void execute_boundaryProfileNames_success() throws CommandException {
        Profile minLengthProfile = new Profile("a");
        Model model = new ModelManager();
        model.addToProfiles(new Profile("current-profile"));
        model.addToProfiles(minLengthProfile);
        model.setAddressBookFilePath(Profile.profileStringToPath("current-profile"));

        SwitchCommand minSwitchCommand = new SwitchCommand(minLengthProfile);
        CommandResult minResult = minSwitchCommand.execute(model);
        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "a"), minResult.getFeedbackToUser());

        Profile maxLengthProfile = new Profile("a".repeat(30));
        model.addToProfiles(maxLengthProfile);

        SwitchCommand maxSwitchCommand = new SwitchCommand(maxLengthProfile);
        CommandResult maxResult = maxSwitchCommand.execute(model);
        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "a".repeat(30)),
                maxResult.getFeedbackToUser());
    }

    @Test
    public void execute_emptyProfileListAvailableProfiles() {
        Model model = new ModelManager();
        model.addToProfiles(new Profile("profile1"));
        model.addToProfiles(new Profile("profile2"));

        SwitchCommand switchCommand = new SwitchCommand(Profile.getEmptyProfile());

        assertThrows(CommandException.class,
                String.format(SwitchCommand.MESSAGE_SHOW_PROFILES, "profile1, profile2", "profile1"), () ->
                        switchCommand.execute(model)
        );
    }

    @Test
    public void execute_noProfilesAvailable_errorMessage() {
        Model model = new ModelManager();
        SwitchCommand switchCommand = new SwitchCommand(Profile.getEmptyProfile());

        assertThrows(CommandException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE), () ->
                        switchCommand.execute(model)
        );
    }

    @Test
    public void execute_switchToCurrentProfile_error() {
        Profile currentProfile = new Profile("active-profile");
        Model model = new ModelManager();
        model.addToProfiles(currentProfile);
        model.setAddressBookFilePath(currentProfile.toPath());

        SwitchCommand switchCommand = new SwitchCommand(currentProfile);

        assertThrows(CommandException.class,
                String.format(SwitchCommand.MESSAGE_NO_SWITCH, "active-profile"), () -> switchCommand.execute(model)
        );
    }

    @Test
    public void execute_invalidProfileName_exceptionThrown() {
        Profile validProfile = new Profile("valid-profile");
        String illegalModifiedProfileName = " space-in-name";
        Model model = new ModelManager();
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalModifiedProfileName));

        SwitchCommand switchCommand = new SwitchCommand(validProfile);

        assertThrows(CommandException.class,
                SwitchCommand.MESSAGE_ILLEGAL_MODIFICATION, () -> switchCommand.execute(model)
        );
    }

    @Test
    public void execute_invalidProfileNameWithSubDir_exceptionThrown() {
        Profile validProfile = new Profile("valid-profile");
        String illegalModifiedProfileName = "data/name";
        Model model = new ModelManager();
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalModifiedProfileName));

        SwitchCommand switchCommand = new SwitchCommand(validProfile);

        assertThrows(CommandException.class,
                SwitchCommand.MESSAGE_ILLEGAL_MODIFICATION, () -> switchCommand.execute(model)
        );
    }

    @Test
    public void equals() {
        Profile profile1 = new Profile("profile1");
        Profile profile2 = new Profile("profile2");
        SwitchCommand switchCommand1 = new SwitchCommand(profile1);
        SwitchCommand switchCommand2 = new SwitchCommand(profile1);
        SwitchCommand switchCommand3 = new SwitchCommand(profile2);

        assertEquals(switchCommand1, switchCommand2);
        assertEquals(switchCommand1, switchCommand1);
        assertNotEquals(switchCommand1, switchCommand3);
        assertNotEquals("not a SwitchCommand", switchCommand1);
        assertNotEquals(null, switchCommand1);
    }

}
