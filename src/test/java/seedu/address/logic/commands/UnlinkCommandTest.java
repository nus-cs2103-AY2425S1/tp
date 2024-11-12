package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnlinkCommand.MESSAGE_UNLINK_CONTACT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.BENSON_NAME;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Student;
import seedu.address.testutil.ParentBuilder;
import seedu.address.testutil.StudentBuilder;

public class UnlinkCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_studentWithLinkFound_success() {
        UnlinkCommand unlinkCommand = new UnlinkCommand(ALICE_NAME);

        Model changedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Student changedStudent = new StudentBuilder(ALICE).withParentName(null).build();
        changedModel.setPerson(ALICE, changedStudent);

        Parent changedParent = new ParentBuilder(ELLE).withChildren().build();
        changedModel.setPerson(ELLE, changedParent);

        String expectedMessage = String.format(MESSAGE_UNLINK_CONTACT_SUCCESS,
                changedStudent.getName(), changedParent.getName());

        assertCommandSuccess(unlinkCommand, model, expectedMessage, changedModel);
    }

    @Test
    public void equals() {
        UnlinkCommand unlinkFirstCommand = new UnlinkCommand(ALICE_NAME);
        UnlinkCommand unlinkSecondCommand = new UnlinkCommand(BENSON_NAME);
        // same object -> returns true
        assertTrue(unlinkFirstCommand.equals(unlinkFirstCommand));

        // same values -> returns true
        UnlinkCommand unlinkFirstCommandCopy = new UnlinkCommand(ALICE_NAME);
        assertTrue(unlinkFirstCommand.equals(unlinkFirstCommandCopy));

        // different types -> returns false
        assertFalse(unlinkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unlinkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unlinkFirstCommand.equals(unlinkSecondCommand));
    }
}
