package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.shortcut.Alias;
import seedu.address.model.shortcut.FullTagName;
import seedu.address.model.shortcut.ShortCut;

class DelShortCutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_deleteExistingShortCut_success() throws Exception {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ShortCut shortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        model.addShortCut(shortCut);
        expectedModel.addShortCut(shortCut);
        expectedModel.removeShortCut(shortCut);
        DelShortCutCommand command = new DelShortCutCommand(shortCut);
        assertCommandSuccess(command, model,
                String.format(DelShortCutCommand.MESSAGE_SUCCESS, shortCut), expectedModel);
    }

    @Test
    void execute_shortCutNotFound_throwsCommandException() {
        //shortcut that does not exist in the model
        ShortCut shortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));

        // Attempt to delete a shortcut that doesn't exist, expect CommandException
        DelShortCutCommand command = new DelShortCutCommand(shortCut);
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    void equalsMethod() {
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        ShortCut shortCut2 = new ShortCut(new Alias("vg"), new FullTagName("Vegetarian"));

        DelShortCutCommand command1 = new DelShortCutCommand(shortCut1);
        DelShortCutCommand command2 = new DelShortCutCommand(shortCut1);
        DelShortCutCommand command3 = new DelShortCutCommand(shortCut2);
        assertEquals(command1, command1);
        assertEquals(command1, command2);
        assertNotEquals(command1, null);
        assertNotEquals(command1, command3);
    }

    @Test
    void toStringMethod() {
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        DelShortCutCommand command = new DelShortCutCommand(shortCut1);
        // Check that toString outputs the correct representation
        assertEquals(command.toString(), shortCut1.toString());
    }
}
