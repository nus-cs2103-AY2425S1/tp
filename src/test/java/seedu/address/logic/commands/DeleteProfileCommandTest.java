
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.profile.Profile;

public class DeleteProfileCommandTest {

    @Test
    public void execute_deleteExistingProfile_success() throws CommandException {
        Profile profileToDelete = new Profile("deletable-profile");
        Model model = new ModelManager();
        model.addToProfiles(profileToDelete);

        DeleteProfileCommand deleteCommand = new DeleteProfileCommand(profileToDelete);
        CommandResult result = deleteCommand.execute(model);

        assertEquals(
                String.format(DeleteProfileCommand.MESSAGE_SUCCESS, "deletable-profile"),
                result.getFeedbackToUser()
        );
        assertFalse(model.getProfiles().contains(profileToDelete));
    }

    @Test
    public void execute_boundaryProfileNames_success() throws CommandException {
        Profile minLengthProfile = new Profile("a");
        Model model = new ModelManager();
        model.addToProfiles(minLengthProfile);

        DeleteProfileCommand minDeleteCommand = new DeleteProfileCommand(minLengthProfile);
        CommandResult minResult = minDeleteCommand.execute(model);
        assertEquals(String.format(DeleteProfileCommand.MESSAGE_SUCCESS, "a"), minResult.getFeedbackToUser());
        assertFalse(model.getProfiles().contains(minLengthProfile));

        Profile maxLengthProfile = new Profile("a".repeat(30));
        model.addToProfiles(maxLengthProfile);

        DeleteProfileCommand maxDeleteCommand = new DeleteProfileCommand(maxLengthProfile);
        CommandResult maxResult = maxDeleteCommand.execute(model);
        assertEquals(
                String.format(DeleteProfileCommand.MESSAGE_SUCCESS, "a".repeat(30)),
                maxResult.getFeedbackToUser()
        );
        assertFalse(model.getProfiles().contains(maxLengthProfile));
    }

    @Test
    public void execute_deleteNonExistentProfile_errorMessage() {
        Profile nonExistentProfile = new Profile("non-existent-profile");
        Model model = new ModelManager();

        DeleteProfileCommand deleteCommand = new DeleteProfileCommand(nonExistentProfile);

        assertThrows(CommandException.class,
                DeleteProfileCommand.MESSAGE_NO_SUCH_PROFILE, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_deleteCurrentProfile_errorMessage() {
        Profile currentProfile = new Profile("active-profile");
        Model model = new ModelManager();
        model.addToProfiles(currentProfile);
        model.setAddressBookFilePath(currentProfile.toPath());

        DeleteProfileCommand deleteCommand = new DeleteProfileCommand(currentProfile);

        assertThrows(CommandException.class,
                String.format(DeleteProfileCommand.MESSAGE_IN_USE), () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_invalidProfileName_exceptionThrown() {
        Profile validProfile = new Profile("valid-profile");
        String illegalProfileName = " space-in-name";
        Model model = new ModelManager();
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalProfileName));

        DeleteProfileCommand deleteCommand = new DeleteProfileCommand(validProfile);

        assertThrows(CommandException.class,
                DeleteProfileCommand.MESSAGE_ILLEGAL_MODIFICATION, () -> deleteCommand.execute(model));
    }

    @Test
    public void execute_invalidProfileNameWithSubDir_exceptionThrown() {
        Profile validProfile = new Profile("valid-profile");
        String illegalProfilePath = "data/name";
        Model model = new ModelManager();
        model.setAddressBookFilePath(Profile.profileStringToPath(illegalProfilePath));

        DeleteProfileCommand deleteCommand = new DeleteProfileCommand(validProfile);

        assertThrows(CommandException.class,
                DeleteProfileCommand.MESSAGE_ILLEGAL_MODIFICATION, () -> deleteCommand.execute(model));
    }

    @Test
    public void equals() {
        Profile profile1 = new Profile("profile1");
        Profile profile2 = new Profile("profile2");
        DeleteProfileCommand deleteCommand1 = new DeleteProfileCommand(profile1);
        DeleteProfileCommand deleteCommand2 = new DeleteProfileCommand(profile1);
        DeleteProfileCommand deleteCommand3 = new DeleteProfileCommand(profile2);

        assertEquals(deleteCommand1, deleteCommand2);
        assertEquals(deleteCommand1, deleteCommand1);
        assertNotEquals(deleteCommand1, deleteCommand3);
        assertNotEquals("not a DeleteProfileCommand", deleteCommand1);
        assertNotEquals(null, deleteCommand1);
    }
}
