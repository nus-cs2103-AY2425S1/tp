package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertMeetupCommandSuccess;
import static seedu.address.testutil.TypicalMeetUps.getTypicalMeetUpList;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ViewMeetupCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalMeetUpList());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getMeetUpList());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertMeetupCommandSuccess(new ViewMeetUpCommand(), model, ViewMeetUpCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        assertMeetupCommandSuccess(new ViewMeetUpCommand(), model, ViewMeetUpCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
