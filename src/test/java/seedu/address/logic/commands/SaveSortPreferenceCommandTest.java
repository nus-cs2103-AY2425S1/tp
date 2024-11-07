package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.SortPreference;

public class SaveSortPreferenceCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    private SortPreference high = new SortPreference("high");
    private SortPreference low = new SortPreference("low");
    private SortPreference recent = new SortPreference("recent");
    private SortPreference distant = new SortPreference("distant");
    private SortPreference standard = new SortPreference("default");
    @Test
    public void equals() {
        SaveSortPreferenceCommand saveByHighToLow = new SaveSortPreferenceCommand(high);
        SaveSortPreferenceCommand saveByLowToHigh = new SaveSortPreferenceCommand(low);
        SaveSortPreferenceCommand saveByDefault = new SaveSortPreferenceCommand(standard);

        // equals itself
        assertTrue(saveByHighToLow.equals(saveByHighToLow));

        // equals another instance of the same preference
        assertTrue(saveByHighToLow.equals(new SaveSortPreferenceCommand(new SortPreference("high"))));

        // checks for not equals
        assertFalse(saveByHighToLow.equals(saveByLowToHigh));
        assertFalse(saveByHighToLow.equals(saveByDefault));
        assertFalse(saveByLowToHigh.equals(saveByDefault));

        // not equals the same type
        assertFalse(saveByHighToLow.equals(1));

        // not equals null
        assertFalse(saveByHighToLow.equals(null));
    }

    @Test
    public void execute_high() {
        String expectedMessage = SaveSortPreferenceCommand.MESSAGE_SUCCESS;
        SaveSortPreferenceCommand command = new SaveSortPreferenceCommand(high);
        expectedModel.setSortPreference(high);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_low() {
        String expectedMessage = SaveSortPreferenceCommand.MESSAGE_SUCCESS;
        SaveSortPreferenceCommand command = new SaveSortPreferenceCommand(low);
        expectedModel.setSortPreference(low);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_recent() {
        String expectedMessage = SaveSortPreferenceCommand.MESSAGE_SUCCESS;
        SaveSortPreferenceCommand command = new SaveSortPreferenceCommand(recent);
        expectedModel.setSortPreference(recent);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_distant() {
        String expectedMessage = SaveSortPreferenceCommand.MESSAGE_SUCCESS;
        SaveSortPreferenceCommand command = new SaveSortPreferenceCommand(distant);
        expectedModel.setSortPreference(distant);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
}
