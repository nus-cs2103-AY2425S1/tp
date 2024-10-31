package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListAllCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listAll_success() {
        ListAllCommand listAllCommand = new ListAllCommand();

        // Update expectedModel lists to show all entities
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateFilteredCompanyList(Model.PREDICATE_SHOW_ALL_COMPANIES);
        expectedModel.updateFilteredJobList(Model.PREDICATE_SHOW_ALL_JOBS);

        // Execute the command and get the result
        CommandResult result = listAllCommand.execute(model);

        // Verify success message
        assertEquals(ListAllCommand.MESSAGE_SUCCESS, result.getFeedbackToUser());

        // Verify the lists are updated to show all entities
        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
        assertEquals(expectedModel.getFilteredCompanyList(), model.getFilteredCompanyList());
        assertEquals(expectedModel.getFilteredJobList(), model.getFilteredJobList());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        ListAllCommand listAllCommand = new ListAllCommand();
        assertThrows(NullPointerException.class, () -> listAllCommand.execute(null));
    }
}
