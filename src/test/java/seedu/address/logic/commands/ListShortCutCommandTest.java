package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;
import seedu.address.logic.commands.exceptions.CommandException;

class ListShortCutCommandTest {

    // Use the provided model initialization
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ListShortCutCommand listShortCutCommand;

    @BeforeEach
    public void setUp() {
        // Initialize the ListShortCutCommand
        listShortCutCommand = new ListShortCutCommand();

        // Add some sample shortcuts to the model and expected model
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        ShortCut shortCut2 = new ShortCut(new Alias("vg"), new FullTagName("Vegetarian"));

        model.addShortCut(shortCut1);
        model.addShortCut(shortCut2);
        expectedModel.addShortCut(shortCut1);
        expectedModel.addShortCut(shortCut2);
    }

    @Test
    void execute_listShortCut_success() throws CommandException {
        // Expected formatted shortcuts
        String expectedFormattedShortcuts = "v : Vegan\nvg : Vegetarian";

        // Execute the command and capture the result
        CommandResult result = listShortCutCommand.execute(model);

        // Assert that the message contains the correct shortcuts
        assertEquals(ListShortCutCommand.MESSAGE_SUCCESS + expectedFormattedShortcuts, result.getFeedbackToUser());
    }

    @Test
    void formatShortCuts_validShortCuts_correctFormatting() {
        // Use model's shortcut list and convert to string
        String shortcuts = model.getShortCutList().toString();

        // Expected formatted string
        String expectedFormattedShortcuts = "v : Vegan\nvg : Vegetarian";

        // Call the formatShortCuts method
        String formattedResult = listShortCutCommand.formatShortCuts(shortcuts);

        // Check if the formatted result matches the expected result
        assertEquals(expectedFormattedShortcuts, formattedResult);
    }

    @Test
    void formatShortCuts_emptyShortCuts_emptyString() {
        // Test with empty shortcut list
        String shortcuts = "[]";
        String formattedResult = listShortCutCommand.formatShortCuts(shortcuts);

        // Expect an empty string
        assertEquals("", formattedResult);
    }

    @Test
    void execute_noShortCuts_success() throws CommandException {
        // Set the model and expectedModel to return an empty shortcut list
        Model emptyModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // Execute the command
        CommandResult result = listShortCutCommand.execute(emptyModel);

        // Assert that it shows no shortcuts
        assertEquals(ListShortCutCommand.MESSAGE_SUCCESS + "", result.getFeedbackToUser());
    }

    @Test
    void equals_sameObject_returnsTrue() {
        assertEquals(listShortCutCommand, listShortCutCommand);
    }

    @Test
    void equals_differentObject_returnsFalse() {
        ListCommand otherCommand = new ListCommand();
        assertNotEquals(listShortCutCommand, otherCommand);
    }

    @Test
    void equals_null_returnsFalse() {
        assertNotEquals(listShortCutCommand, null);
    }
}