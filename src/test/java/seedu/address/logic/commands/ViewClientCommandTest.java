package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Name;

public class ViewClientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_validNameAlice_success() {
        ViewClientCommand viewClientCommand = new ViewClientCommand(
                new Name("Alice Pauline"));
        String clientName = "Alice Pauline";
        String expectedMessage = clientName + "'s Client tab displayed!";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validNameFiona_success() {
        ViewClientCommand viewClientCommand = new ViewClientCommand(
                new Name("Fiona Kunz"));
        String clientName = "Fiona Kunz";
        String expectedMessage = clientName + "'s Client tab displayed!";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_validNameElle_success() {
        ViewClientCommand viewClientCommand = new ViewClientCommand(
                new Name("Elle Meyer"));
        String clientName = "Elle Meyer";
        String expectedMessage = clientName + "'s Client tab displayed!";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(viewClientCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        ViewClientCommand viewClientCommand = new ViewClientCommand(
                new Name("Bob"));
        String clientName = "Bob";
        String expectedMessage = clientName + "'s Client tab displayed!";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertThrows(CommandException.class, String.format(Messages.MESSAGE_INVALID_NAME_DISPLAYED), ()
                -> viewClientCommand.execute(model));
    }

    @Test
    public void equal_success() {
        ViewClientCommand commandOne = new ViewClientCommand(new Name("Bob"));
        ViewClientCommand commandTwo = commandOne;
        assertTrue(commandOne.equals(commandTwo));
    }

    @Test
    public void toString_success() {
        String name = "Bob";
        ViewClientCommand command = new ViewClientCommand(new Name(name));
        String expectedString =  "seedu.address.logic.commands.ViewClientCommand{toShowClient=" + name + "}";
        assertEquals(expectedString, command.toString());
    }

}
