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

class AddShortCutCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_newShortCut_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        ShortCut shortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        expectedModel.addShortCut(shortCut);
        assertCommandSuccess(new AddShortCutCommand(shortCut), model,
                String.format(AddShortCutCommand.MESSAGE_SUCCESS, shortCut), expectedModel);
    }

    @Test
    void execute_duplicateShortCut_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ShortCut shortCut = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        model.addShortCut(shortCut);
        assertThrows(CommandException.class, () -> new AddShortCutCommand(shortCut).execute(model));
    }

    @Test
    void equalsMethod() {
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        ShortCut shortCut2 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        ShortCut shortCut3 = new ShortCut(new Alias("vg"), new FullTagName("Vegetarian"));

        AddShortCutCommand command1 = new AddShortCutCommand(shortCut1);
        AddShortCutCommand command2 = new AddShortCutCommand(shortCut2);
        AddShortCutCommand command3 = new AddShortCutCommand(shortCut3);
        assertEquals(command1, command1);
        assertNotEquals(command1, null);
        assertEquals(command1, command2);
        assertNotEquals(command1, command3);
    }

    @Test
    void toStringMethod() {
        ShortCut shortCut1 = new ShortCut(new Alias("v"), new FullTagName("Vegan"));
        assertEquals(shortCut1.toString(), "v : Vegan");
    }
}
