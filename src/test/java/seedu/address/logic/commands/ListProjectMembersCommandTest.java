package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PROJECT_MEMBERS_LISTED;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;
import static seedu.address.testutil.TypicalAssignments.getTypicalAddressBook;
import static seedu.address.testutil.TypicalEmployees.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.listcommands.ListProjectMembersCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentProjectPredicate;
import seedu.address.model.employee.Employee;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class ListProjectMembersCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        AssignmentProjectPredicate firstPredicate =
                new AssignmentProjectPredicate("first");
        AssignmentProjectPredicate secondPredicate =
                new AssignmentProjectPredicate("second");

        ListProjectMembersCommand listFirstCommand = new ListProjectMembersCommand(firstPredicate);
        ListProjectMembersCommand listSecondCommand = new ListProjectMembersCommand(secondPredicate);

        // same object -> returns true
        assertTrue(listFirstCommand.equals(listFirstCommand));

        // same values -> returns true
        ListProjectMembersCommand listFirstCommandCopy = new ListProjectMembersCommand(firstPredicate);
        assertTrue(listFirstCommand.equals(listFirstCommandCopy));

        // different types -> returns false
        assertFalse(listFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listFirstCommand.equals(null));

        // different employee -> returns false
        assertFalse(listFirstCommand.equals(listSecondCommand));
    }

    @Test
    public void execute_wrongName_noEmployeeFound() {
        String expectedMessage = String.format(MESSAGE_PROJECT_MEMBERS_LISTED, 0);
        AssignmentProjectPredicate predicate = preparePredicate("sda");
        ListProjectMembersCommand command = new ListProjectMembersCommand(predicate);

        expectedModel.updateFilteredAssignmentList(predicate);
        List<Assignment> filteredAssignments = expectedModel.getFilteredAssignmentList();
        expectedModel.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        List<Employee> projectMembers = filteredAssignments.stream()
                .map(Assignment::getEmployee)
                .distinct()
                .toList();
        expectedModel.updateFilteredEmployeeList(projectMembers::contains);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_partialName_noEmployeeFound() {
        String expectedMessage = String.format(MESSAGE_PROJECT_MEMBERS_LISTED, 0);
        AssignmentProjectPredicate predicate = preparePredicate("Alpha");
        ListProjectMembersCommand command = new ListProjectMembersCommand(predicate);

        expectedModel.updateFilteredAssignmentList(predicate);
        List<Assignment> filteredAssignments = expectedModel.getFilteredAssignmentList();
        expectedModel.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        List<Employee> projectMembers = filteredAssignments.stream()
                .map(Assignment::getEmployee)
                .distinct()
                .toList();
        expectedModel.updateFilteredEmployeeList(projectMembers::contains);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredEmployeeList());
    }

    @Test
    public void execute_fullProjectName_employeeFound() {
        String expectedMessage = String.format(MESSAGE_PROJECT_MEMBERS_LISTED, 1);
        AssignmentProjectPredicate predicate = preparePredicate("Project Alpha");
        ListProjectMembersCommand command = new ListProjectMembersCommand(predicate);

        expectedModel.updateFilteredAssignmentList(predicate);
        List<Assignment> filteredAssignments = expectedModel.getFilteredAssignmentList();
        expectedModel.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        List<Employee> projectMembers = filteredAssignments.stream()
                .map(Assignment::getEmployee)
                .distinct()
                .toList();
        expectedModel.updateFilteredEmployeeList(projectMembers::contains);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getFilteredEmployeeList());
    }

    @Test
    public void toStringMethod() {
        AssignmentProjectPredicate predicate = new AssignmentProjectPredicate("keyword");
        ListProjectMembersCommand listCommand = new ListProjectMembersCommand(predicate);
        String expected = ListProjectMembersCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, listCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private AssignmentProjectPredicate preparePredicate(String userInput) {
        return new AssignmentProjectPredicate(userInput);
    }
}
