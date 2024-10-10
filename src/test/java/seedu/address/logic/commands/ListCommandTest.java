package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalClientBook());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(),
                model.getClientBook());
    }


    @Test
    public void testEnumValues() {
        assertEquals(4, ListCommand.Key.values().length);
        assertTrue(Arrays.asList(ListCommand.Key.values()).contains(ListCommand.Key.BUYERS));
        assertTrue(Arrays.asList(ListCommand.Key.values()).contains(ListCommand.Key.SELLERS));
        assertTrue(Arrays.asList(ListCommand.Key.values()).contains(ListCommand.Key.CLIENTS));
        assertTrue(Arrays.asList(ListCommand.Key.values()).contains(ListCommand.Key.PROPERTIES));
    }

    //TODO: Update test to reflect new ListCommand @apollo-tan

    @Test
    public void listCommandGeneration() {
        Command command = new ListCommand(ListCommand.Key.BUYERS);

        // Check if command is an instance of ListCommand
        assertTrue(command instanceof ListCommand, "The command should be an instance of ListCommand");
    }

    // TODO: Add more robust testing
    @Test
    public void testExecuteBuyers() throws CommandException {
        Command command = new ListCommand(ListCommand.Key.BUYERS);
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all buyers");
    }

    @Test
    public void testExecuteSellers() throws CommandException {
        Command command = new ListCommand(ListCommand.Key.SELLERS);
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all sellers");
    }

    @Test
    public void testExecuteClients() throws CommandException {
        Command command = new ListCommand(ListCommand.Key.CLIENTS);
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all clients");
    }

    @Test
    public void testExecuteProperties() throws CommandException {
        Command command = new ListCommand(ListCommand.Key.PROPERTIES);
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all properties");
    }


    //    @Test
    //    public void execute_listIsNotFiltered_showsSameList() {
    //        assertCommandSuccess(
    //                new ListCommand(ListCommand.Key.SELLERS),
    //                model,
    //                ListCommand.MESSAGE_SUCCESS,
    //                expectedModel
    //        );
    //    }
    //
    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showPersonAtIndex(model, INDEX_FIRST_PERSON);
    //        assertCommandSuccess(
    //                new ListCommand(ListCommand.Key.SELLERS),
    //                model,
    //                ListCommand.MESSAGE_SUCCESS,
    //                expectedModel
    //        );
    //    }
}
