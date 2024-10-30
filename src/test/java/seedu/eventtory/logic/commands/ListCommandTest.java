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
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEventTory(), new UserPrefs());
        expectedModel = new ModelManager(model.getEventTory(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_MULTIVIEW_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showVendorAtIndex(model, INDEX_FIRST_VENDOR);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_MULTIVIEW_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand listCommand = new ListCommand();
        ListCommand other = new ListCommand();
        ListEventCommand listEventCommand = new ListEventCommand();
        ListVendorCommand listVendorCommand = new ListVendorCommand();

        assertTrue(listCommand.equals(listCommand));
        assertTrue(listCommand.equals(other));
        // ListCommand != ListEventCommand
        assertFalse(listCommand.equals(listEventCommand));
        // ListCommand != ListVendorCommand
        assertFalse(listCommand.equals(listVendorCommand));
        assertFalse(listCommand.equals(null));
    }
}
