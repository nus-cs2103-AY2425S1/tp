package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EmergencyPhoneCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddEmergencyContactNumberCommand.
 */
public class EmergencyPhoneCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Name name = ALICE.getName();
        final Phone phone = new Phone(VALID_EMERGENCY_PHONE_AMY);
        assertCommandFailure(new EmergencyPhoneCommand(name, phone), model,
                String.format(MESSAGE_ARGUMENTS, name, phone));
    }
}
