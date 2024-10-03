package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMERGENCY_CONTACT_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.EmergencyContactCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class EmergencyContactCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final String emergencyContactName = "Lily";
        final String emergencyContactNumber = "12345678";
        assertCommandFailure(new EmergencyContactCommand(INDEX_FIRST_PERSON, emergencyContactName,
                        emergencyContactNumber), model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), emergencyContactName,
                        emergencyContactNumber));
    }
    @Test
    public void equals() {
        final EmergencyContactCommand standardCommand = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT_NAME_AMY, VALID_EMERGENCY_CONTACT_NUMBER_AMY);
        // same values -> returns true
        EmergencyContactCommand commandWithSameValues = new EmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT_NAME_AMY, VALID_EMERGENCY_CONTACT_NUMBER_AMY);
        assertTrue(standardCommand.equals(commandWithSameValues));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new EmergencyContactCommand(INDEX_SECOND_PERSON,
                VALID_EMERGENCY_CONTACT_NAME_AMY, VALID_EMERGENCY_CONTACT_NUMBER_AMY)));
        // different remark -> returns false
        assertFalse(standardCommand.equals(new EmergencyContactCommand(INDEX_FIRST_PERSON,
                VALID_EMERGENCY_CONTACT_NAME_BOB, VALID_EMERGENCY_CONTACT_NUMBER_BOB)));
    }
}
