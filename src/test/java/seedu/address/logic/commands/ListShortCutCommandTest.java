package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;


class ListShortCutCommandTest {

    // Use the provided model initialization
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ListShortCutCommand listShortCutCommand;

    @BeforeEach
    public void setUp() {
        listShortCutCommand = new ListShortCutCommand();
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        ShortCut shortCut2 = new ShortCut(new Alias("vg"), new FullTagName("Vegetarian"));

        model.addShortCut(shortCut1);
        model.addShortCut(shortCut2);
        expectedModel.addShortCut(shortCut1);
        expectedModel.addShortCut(shortCut2);
    }

    @Test
    void execute_listShortCut_success() throws CommandException {
        String expectedFormattedShortcuts = "v : Vegan\nvg : Vegetarian";
        String expectedFeedbackToUsers = ListShortCutCommand.MESSAGE_SUCCESS + expectedFormattedShortcuts;
        CommandResult result = listShortCutCommand.execute(model);
        assertEquals(expectedFeedbackToUsers, result.getFeedbackToUser());
    }

    @Test
    void formatShortCuts_validShortCuts_correctFormatting() {
        String shortcuts = model.getShortCutList().toString();
        String expectedFormattedShortcuts = "v : Vegan\nvg : Vegetarian";
        String formattedResult = listShortCutCommand.formatShortCuts(shortcuts);
        assertEquals(expectedFormattedShortcuts, formattedResult);
    }

    @Test
    void formatShortCuts_emptyShortCuts_emptyString() {
        String shortcuts = "[]";
        String formattedResult = listShortCutCommand.formatShortCuts(shortcuts);

        // Expect an empty string
        assertEquals("", formattedResult);
    }

    @Test
    void execute_noShortCuts_success() throws CommandException {
        // Set the model and expectedModel to return an empty shortcut list
        Model emptyModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        CommandResult result = listShortCutCommand.execute(emptyModel);
        String expectedFeedbackToUser = ListShortCutCommand.MESSAGE_SUCCESS + "";

        // Assert that it shows no shortcuts
        assertEquals(expectedFeedbackToUser, result.getFeedbackToUser());
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
