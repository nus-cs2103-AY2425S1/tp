package seedu.eventtory.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.eventtory.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.eventtory.logic.commands.CommandTestUtil.showVendorAtIndex;
import static seedu.eventtory.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.eventtory.testutil.TypicalVendors.getTypicalEventTory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.eventtory.model.Model;
import seedu.eventtory.model.ModelManager;
import seedu.eventtory.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListVendorCommand.
 */
public class ListVendorCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEventTory(), new UserPrefs());
        expectedModel = new ModelManager(model.getEventTory(), new UserPrefs());
    }

    @Test
    public void execute_eventListIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListVendorCommand(), model,
                ListVendorCommand.MESSAGE_LIST_VENDOR_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);
        assertCommandSuccess(new ListVendorCommand(), model,
                ListVendorCommand.MESSAGE_LIST_VENDOR_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListVendorCommand listVendorCommand = new ListVendorCommand();
        ListVendorCommand other = new ListVendorCommand();
        ListEventCommand listEventCommand = new ListEventCommand();
        ListCommand listCommand = new ListCommand();

        assertTrue(listVendorCommand.equals(other));
        // ListVendorCommand != ListEventCommand
        assertFalse(listVendorCommand.equals(listEventCommand));
        assertFalse(listVendorCommand.equals(listCommand));
    }
}
