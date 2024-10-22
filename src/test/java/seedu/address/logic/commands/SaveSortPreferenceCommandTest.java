package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class SaveSortPreferenceCommandTest {

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    @Test
    public void equals() {
        SaveSortPreferenceCommand saveByHighToLow = new SaveSortPreferenceCommand("high");
        SaveSortPreferenceCommand saveByLowToHigh = new SaveSortPreferenceCommand("low");
        SaveSortPreferenceCommand saveByDefault = new SaveSortPreferenceCommand("default");

        // equals itself
        assertTrue(saveByHighToLow.equals(saveByHighToLow));

        // equals another instance of the same preference
        assertTrue(saveByHighToLow.equals(new SaveSortPreferenceCommand("high")));

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
    public void execute_save_success() {
        CommandResult expectedCommandResult = new CommandResult(SaveSortPreferenceCommand.MESSAGE_SUCCESS);
        assertCommandSuccess(new SaveSortPreferenceCommand("high"), model, expectedCommandResult, expectedModel);
    }
}
