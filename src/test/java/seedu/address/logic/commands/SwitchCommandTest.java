package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.profile.Profile;

public class SwitchCommandTest {
    private Model model;
    private Profile currentProfile;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        currentProfile = new Profile("current-profile");
        model.setAddressBookFilePath(currentProfile.toPath());
    }

    @Test
    public void execute_switchToValidDifferentProfile_success() throws CommandException {
        Profile validProfile = new Profile("new-profile");
        model.addToProfiles(validProfile);

        SwitchCommand switchCommand = new SwitchCommand(validProfile);
        CommandResult result = switchCommand.execute(model);

        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "new-profile"), result.getFeedbackToUser());
    }

    @Test
    public void execute_switchToMinLengthProfile_success() throws CommandException {
        Profile minLengthProfile = new Profile("a");
        model.addToProfiles(minLengthProfile);

        SwitchCommand minSwitchCommand = new SwitchCommand(minLengthProfile);
        CommandResult minResult = minSwitchCommand.execute(model);

        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "a"), minResult.getFeedbackToUser());
    }

    @Test
    public void execute_switchToMaxLengthProfile_success() throws CommandException {
        Profile maxLengthProfile = new Profile("a".repeat(30));
        model.addToProfiles(maxLengthProfile);

        SwitchCommand maxSwitchCommand = new SwitchCommand(maxLengthProfile);
        CommandResult maxResult = maxSwitchCommand.execute(model);

        assertEquals(String.format(SwitchCommand.MESSAGE_SUCCESS, "a".repeat(30)), maxResult.getFeedbackToUser());
    }

    @Test
    public void execute_noProfilesAvailable_throwsCommandException() {
        model = new ModelManager(); // Reset model to ensure no profiles
        SwitchCommand switchCommand = new SwitchCommand(Profile.getEmptyProfile());

        assertThrows(CommandException.class,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE), () ->
                        switchCommand.execute(model)
        );
    }

    @Test
    public void execute_profilesAvailable_showsAvailableProfiles() {
        model.addToProfiles(new Profile("profile1"));
        model.addToProfiles(new Profile("profile2"));
        SwitchCommand switchCommand = new SwitchCommand(Profile.getEmptyProfile());

        assertThrows(CommandException.class,
                String.format(SwitchCommand.MESSAGE_SHOW_PROFILES, "profile1, profile2", "profile1"), () ->
                        switchCommand.execute(model)
        );
    }

    @Test
    public void execute_switchToCurrentProfile_throwsError() {
        SwitchCommand switchCommand = new SwitchCommand(currentProfile);

        assertThrows(CommandException.class,
                String.format(SwitchCommand.MESSAGE_NO_SWITCH, "current-profile"), () -> switchCommand.execute(model)
        );
    }

    @Test
    public void execute_invalidProfileName_throwsException() {
        String illegalModifiedProfileName = " space-in-name";
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalModifiedProfileName));

        SwitchCommand switchCommand = new SwitchCommand(new Profile("valid-profile"));

        assertThrows(CommandException.class,
                SwitchCommand.MESSAGE_ILLEGAL_MODIFICATION, () -> switchCommand.execute(model)
        );
    }

    @Test
    public void execute_invalidProfilePathWithSubDir_throwsException() {
        String illegalModifiedProfilePath = "data/name";
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalModifiedProfilePath));

        SwitchCommand switchCommand = new SwitchCommand(new Profile("valid-profile"));

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
        assertNotEquals(switchCommand1, switchCommand3);
        assertNotEquals("not a SwitchCommand", switchCommand1);
        assertNotEquals(null, switchCommand1);
    }
}
