package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EmergencyPhoneCommand.MESSAGE_EMERGENCY_PHONE_SUCCESS;
import static seedu.address.logic.commands.EmergencyPhoneCommand.MESSAGE_INVALID_NAME;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmergencyPhone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEmergencyContactNumberCommand.
 */
public class EmergencyPhoneCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addEmergencyPhoneSuccess() {
        final Name name = ALICE.getName();
        final EmergencyPhone emergencyPhone = new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY);
        assertCommandSuccess(new EmergencyPhoneCommand(name, emergencyPhone), model,
                String.format(MESSAGE_EMERGENCY_PHONE_SUCCESS, name));
    }

    @Test
    public void execute_addEmergencyPhoneInvalidNameFailure() {
        final Name invalidName = new Name("Invalid");
        final EmergencyPhone emergencyPhone = new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY);
        assertCommandFailure(new EmergencyPhoneCommand(invalidName, emergencyPhone), model,
                String.format(MESSAGE_INVALID_NAME));
    }

    @Test
    public void equals() {
        final EmergencyPhoneCommand standardCommand = new EmergencyPhoneCommand(
                new Name(VALID_NAME_AMY), new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY));

        // same values -> returns true
        EmergencyPhoneCommand commandWithSameValues = new EmergencyPhoneCommand(
                new Name(VALID_NAME_AMY), new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different name -> returns false
        assertFalse(standardCommand.equals(new EmergencyPhoneCommand(
                new Name(VALID_NAME_BOB), new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY))));

        // different emergencyPhone -> returns false
        assertFalse(standardCommand.equals(new EmergencyPhoneCommand(
                new Name(VALID_NAME_AMY), new EmergencyPhone(VALID_EMERGENCY_PHONE_BOB))));

    }
}
