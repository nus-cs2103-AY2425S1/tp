package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AddEmergencyContactNameCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

public class AddEmergencyContactNameCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        final Name studentName = ALICE.getName();
        final String eCName = "John Doe";
        assertCommandFailure(new AddEmergencyContactNameCommand(studentName, new Name(eCName)),
                model, String.format(MESSAGE_ARGUMENTS, studentName, eCName));
    }


    @Test
    public void equals() {
        final AddEmergencyContactNameCommand standardCommand = new AddEmergencyContactNameCommand(
                ALICE.getName(), new Name(VALID_ECNAME_AMY));

        // same values -> returns true
        AddEmergencyContactNameCommand commandWithSameValues = new AddEmergencyContactNameCommand(
                ALICE.getName(), new Name(VALID_ECNAME_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different person name -> returns false
        assertFalse(standardCommand.equals(new AddEmergencyContactNameCommand(BOB.getName(),
                new Name(VALID_ECNAME_AMY))));

        // different emergency contact name -> returns false
        assertFalse(standardCommand.equals(new AddEmergencyContactNameCommand(ALICE.getName(),
                new Name(VALID_ECNAME_BOB))));
    }
}

