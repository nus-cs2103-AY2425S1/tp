package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EmergencyPhoneCommand.MESSAGE_INVALID_NAME;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.EmergencyPhone;
import seedu.address.model.person.Name;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEmergencyContactNumberCommand.
 */
public class EmergencyPhoneCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Name name = ALICE.getName();
        final Name invalidName = new Name("Invalid");
        final EmergencyPhone emergencyPhone = new EmergencyPhone(VALID_EMERGENCY_PHONE_AMY);
        assertCommandFailure(new EmergencyPhoneCommand(invalidName, emergencyPhone), model,
                String.format(MESSAGE_INVALID_NAME));
    }
}
