package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.AMY;
import static seedu.address.testutil.TypicalEmployees.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.employee.Employee;
import seedu.address.testutil.EmployeeBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newEmployee_success() {
        Employee validEmployee = new EmployeeBuilder().build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addEmployee(validEmployee);

        assertCommandSuccess(new AddCommand(validEmployee), model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validEmployee)),
                expectedModel);
    }

    @Test
    public void execute_duplicateEmployee_throwsCommandException() {
        Employee employeeInList = model.getAddressBook().getEmployeeList().get(0);
        assertCommandFailure(new AddCommand(employeeInList), model,
                AddCommand.MESSAGE_DUPLICATE_EMPLOYEE);
    }

    @Test
    public void execute_duplicateEmployeeId_throwsCommandException() {
        Employee employeeInList = model.getAddressBook().getEmployeeList().get(0);
        Employee employeeToAdd = new EmployeeBuilder(AMY)
                .withEmployeeId(employeeInList.getEmployeeId().toString()).build();
        assertCommandFailure(new AddCommand(employeeToAdd), model,
                AddCommand.MESSAGE_DUPLICATE_EMPLOYEE);
    }

}
