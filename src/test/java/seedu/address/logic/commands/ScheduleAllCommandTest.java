package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ScheduleAllCommand.
 */
public class ScheduleAllCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_scheduleIsNotFiltered_showsSameList() {
        expectedModel.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        assertCommandSuccess(new ScheduleAllCommand(), model, ScheduleAllCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_scheduleIsFiltered_showsEverything() {
        showAppointmentAtIndex(model, INDEX_FIRST_PERSON);
        expectedModel.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        assertCommandSuccess(new ScheduleAllCommand(), model, ScheduleAllCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
