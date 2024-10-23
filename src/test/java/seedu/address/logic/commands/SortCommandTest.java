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
import seedu.address.model.person.PersonNameComparator;
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
        assertCommandSuccess(new SortNameCommand(), model, SortNameCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsOnlyEmployees() {
        // filter both lists to show all employees
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        expectedModel.updateSortedPersonList(new PersonNameComparator());
        assertCommandSuccess(new SortNameCommand(), model, SortNameCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByDate() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_EMPLOYEES);
        expectedModel.updateSortedPersonList(new PersonDateComparator());
        assertCommandSuccess(new SortDateCommand(), model, SortDateCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
