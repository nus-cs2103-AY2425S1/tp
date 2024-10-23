package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalClients.getTypicalClientBook;
import static seedu.address.testutil.TypicalMeetings.getTypicalMeetingBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalProperty.getTypicalPropertyBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ListClientsCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalPropertyBook(),
                getTypicalClientBook(), getTypicalMeetingBook());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), model.getPropertyBook(),
                model.getClientBook(), model.getMeetingBook());
    }

    @Test
    public void listClientsCommandGeneration() {
        Command command = new ListClientsCommand();

        // Check if command is an instance of ListClientsCommand
        assertTrue(command instanceof ListClientsCommand,
                "Command should be an instance of ListClientsCommand");
    }

    @Test
    public void testExecuteClients() throws CommandException {
        Command command = new ListClientsCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all clients");
    }

    @Test
    public void keywordEqualsClients() {
        assertEquals("clients", ListClientsCommand.KEY_WORD);
    }

    @Test
    void equals_sameCommand_returnsTrue() {
        // Arrange
        ListClientsCommand listClientsCommand1 = new ListClientsCommand();
        ListClientsCommand listClientsCommand2 = new ListClientsCommand(); // Same command

        // Act & Assert
        assertEquals(listClientsCommand1, listClientsCommand2); // Different instances, same command type
    }

    @Test
    void equals_differentCommand_returnsFalse() {
        // Arrange
        ListClientsCommand listClientsCommand = new ListClientsCommand();
        ListBuyersCommand listBuyersCommand = new ListBuyersCommand(); // Different subclass of ListCommand

        // Act & Assert
        assertNotEquals(listClientsCommand, listBuyersCommand); // Commands should not be equal
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        ListClientsCommand listClientsCommand = new ListClientsCommand();
        Object differentObject = new Object(); // Completely different object type

        // Act & Assert
        assertNotEquals(listClientsCommand, differentObject); // Comparing with a different type of object
    }


}
