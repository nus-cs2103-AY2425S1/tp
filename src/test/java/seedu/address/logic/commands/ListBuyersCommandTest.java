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

public class ListBuyersCommandTest {
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
    public void listBuyersCommandGeneration() {
        Command command = new ListBuyersCommand();

        // Check if command is an instance of ListBuyersCommand
        assertTrue(command instanceof ListBuyersCommand,
                "Command should be an instance of ListBuyersCommand");
    }

    @Test
    public void testExecuteBuyers() throws CommandException {
        Command command = new ListBuyersCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all buyers");
    }

    @Test
    public void keywordEqualsBuyers() {
        assertEquals("buyers", ListBuyersCommand.KEY_WORD);
    }

    @Test
    void equals_sameCommand_returnsTrue() {
        // Arrange
        ListBuyersCommand listBuyersCommand1 = new ListBuyersCommand();
        ListBuyersCommand listBuyersCommand2 = new ListBuyersCommand(); // Same command

        // Act & Assert
        assertEquals(listBuyersCommand1, listBuyersCommand2); // Different instances, same command type
    }

    @Test
    void equals_differentCommand_returnsFalse() {
        // Arrange
        ListBuyersCommand listBuyersCommand = new ListBuyersCommand();
        ListClientsCommand listClientsCommand = new ListClientsCommand(); // Different subclass of ListCommand

        // Act & Assert
        assertNotEquals(listBuyersCommand, listClientsCommand); // Commands should not be equal
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        ListBuyersCommand listBuyersCommand = new ListBuyersCommand();
        Object differentObject = new Object(); // Completely different object type

        // Act & Assert
        assertNotEquals(listBuyersCommand, differentObject); // Comparing with a different type of object
    }
}
