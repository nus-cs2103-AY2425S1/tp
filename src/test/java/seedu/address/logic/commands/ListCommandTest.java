package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
                getTypicalClientBook());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getPropertyBook(),
                model.getClientBook());
    }

    @Test
    public void listBuyersCommandGeneration() {
        Command command = new ListBuyersCommand();

        // Check if command is an instance of ListBuyersCommand
        assertTrue(command instanceof ListBuyersCommand,
                "Command should be an instance of ListBuyersCommand");
    }

    @Test
    public void listSellersCommandGeneration() {
        Command command = new ListSellersCommand();

        // Check if command is an instance of ListSellersCommand
        assertTrue(command instanceof ListSellersCommand,
                "Command should be an instance of ListSellersCommand");
    }

    @Test
    public void listClientsCommandGeneration() {
        Command command = new ListClientsCommand();

        // Check if command is an instance of ListClientsCommand
        assertTrue(command instanceof ListClientsCommand,
                "Command should be an instance of ListClientsCommand");
    }

    @Test
    public void listPropertiesCommandGeneration() {
        Command command = new ListPropertiesCommand();

        // Check if command is an instance of ListPropertiesCommand
        assertTrue(command instanceof ListPropertiesCommand,
                "Command should be an instance of ListPropertiesCommand");
    }

    @Test
    public void testExecuteBuyers() throws CommandException {
        Command command = new ListBuyersCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all buyers");
    }

    @Test
    public void testExecuteSellers() throws CommandException {
        Command command = new ListSellersCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all sellers");
    }

    @Test
    public void testExecuteClients() throws CommandException {
        Command command = new ListClientsCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all clients");
    }

    @Test
    public void testExecuteProperties() throws CommandException {
        Command command = new ListPropertiesCommand();
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
