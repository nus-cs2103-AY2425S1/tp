package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnlinkCommand.MESSAGE_UNLINK_CONTACT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
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
}
