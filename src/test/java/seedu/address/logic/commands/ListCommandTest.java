package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalInternshipApplications.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.hireme.logic.commands.ListCommand;
import seedu.hireme.model.Model;
import seedu.hireme.model.ModelManager;
import seedu.hireme.model.UserPrefs;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model<InternshipApplication> model;
    private Model<InternshipApplication> expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager<>(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager<>(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //Todo when FILTER feature is implemented
    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showPersonAtIndex(model, INDEX_FIRST_INTERNSHIP_APPLICATION);
    //        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
