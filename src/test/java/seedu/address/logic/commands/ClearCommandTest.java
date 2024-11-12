package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalEmployees;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(TypicalEmployees.getTypicalAddressBook());
        Model expectedModel = new ModelManager(TypicalEmployees.getTypicalAddressBook());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookWithAssignments_success() {
        // Deleting all employees should also delete all assignments
        Model model = new ModelManager(TypicalAssignments.getTypicalAddressBook());
        Model expectedModel = new ModelManager(TypicalAssignments.getTypicalAddressBook());
        expectedModel.setAddressBookEmployee(new AddressBook());
        expectedModel.setAddressBookAssignments(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
