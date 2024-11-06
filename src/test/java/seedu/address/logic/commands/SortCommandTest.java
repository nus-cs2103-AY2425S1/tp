package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDateComparator;
import seedu.address.model.person.PersonDateReverseComparator;
import seedu.address.model.person.PersonDepartmentComparator;
import seedu.address.model.person.PersonNameComparator;
import seedu.address.model.person.PersonRoleComparator;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEmployeeCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person person = new PersonBuilder().withName("Aaa").withContractEndDate("2000-01-01").build();
        model.addPerson(person);
        expectedModel.addPerson(person);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByName() {
        expectedModel.updateSortedPersonList(new PersonNameComparator());
        assertCommandSuccess(new SortNameCommand(false), model,
                SortNameCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsOnlyEmployees() {
        // filter both lists to show all employees
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        expectedModel.updateSortedPersonList(new PersonNameComparator());
        assertCommandSuccess(new SortNameCommand(false), model,
                SortNameCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByDate() {
        expectedModel.updateSortedPersonList(new PersonDateComparator());
        assertCommandSuccess(new SortDateCommand(false), model,
                SortDateCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByDept() {
        expectedModel.updateSortedPersonList(new PersonDepartmentComparator());
        assertCommandSuccess(new SortDepartmentCommand(false), model,
                SortDepartmentCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByRole() {
        expectedModel.updateSortedPersonList(new PersonRoleComparator());
        assertCommandSuccess(new SortRoleCommand(false), model,
                SortRoleCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByDepartment() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        expectedModel.updateSortedPersonList(new PersonDepartmentComparator());
        assertCommandSuccess(new SortDepartmentCommand(false), model,
                SortDepartmentCommand.MESSAGE_SUCCESS + SortCommand.ASCENDING_SUCCESS, expectedModel);
    }


    @Test
    public void execute_listIsNotFiltered_sortsByDateDesc() {
        expectedModel.updateSortedPersonList(new PersonDateReverseComparator());
        assertCommandSuccess(new SortDateCommand(true), model,
                SortDateCommand.MESSAGE_SUCCESS + SortCommand.DESCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByDeptDesc() {
        expectedModel.updateSortedPersonList(new PersonDepartmentComparator().reversed());
        assertCommandSuccess(new SortDepartmentCommand(true), model,
                SortDepartmentCommand.MESSAGE_SUCCESS + SortCommand.DESCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByNameDesc() {
        expectedModel.updateSortedPersonList(new PersonNameComparator().reversed());
        assertCommandSuccess(new SortNameCommand(true), model,
                SortNameCommand.MESSAGE_SUCCESS + SortCommand.DESCENDING_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByRoleDesc() {
        expectedModel.updateSortedPersonList(new PersonRoleComparator().reversed());
        assertCommandSuccess(new SortRoleCommand(true), model,
                SortRoleCommand.MESSAGE_SUCCESS + SortCommand.DESCENDING_SUCCESS, expectedModel);
    }

}
