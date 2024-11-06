package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalProjects;

public class ClearProjectCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearProjectCommand(), model, ClearProjectCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalProjects.getTypicalAddressBook());
        Model expectedModel = new ModelManager(TypicalProjects.getTypicalAddressBook());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearProjectCommand(), model, ClearProjectCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookWithAssignments_success() {
        // Deleting all projects should also delete all assignments
        Model model = new ModelManager(TypicalAssignments.getTypicalAddressBook());
        Model expectedModel = new ModelManager(TypicalAssignments.getTypicalAddressBook());
        expectedModel.setAddressBookProject(new AddressBook());
        expectedModel.setAddressBookAssignments(new AddressBook());

        assertCommandSuccess(new ClearProjectCommand(), model, ClearProjectCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
