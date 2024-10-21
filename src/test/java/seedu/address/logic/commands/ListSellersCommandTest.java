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

public class ListSellersCommandTest {
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
    public void listSellersCommandGeneration() {
        Command command = new ListSellersCommand();

        // Check if command is an instance of ListSellersCommand
        assertTrue(command instanceof ListSellersCommand,
                "Command should be an instance of ListSellersCommand");
    }

    @Test
    public void testExecuteSellers() throws CommandException {
        Command command = new ListSellersCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all sellers");
    }

    @Test
    public void keywordEqualsSellers() {
        assertEquals("sellers", ListSellersCommand.KEY_WORD);
    }

    @Test
    void equals_sameCommand_returnsTrue() {
        // Arrange
        ListSellersCommand listSellersCommand1 = new ListSellersCommand();
        ListSellersCommand listSellersCommand2 = new ListSellersCommand(); // Same command

        // Act & Assert
        assertEquals(listSellersCommand1, listSellersCommand2); // Different instances, same command type
    }

    @Test
    void equals_differentCommand_returnsFalse() {
        // Arrange
        ListSellersCommand listSellersCommand = new ListSellersCommand();
        ListClientsCommand listClientsCommand = new ListClientsCommand(); // Different subclass of ListCommand

        // Act & Assert
        assertNotEquals(listSellersCommand, listClientsCommand); // Commands should not be equal
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        ListSellersCommand listSellersCommand = new ListSellersCommand();
        Object differentObject = new Object(); // Completely different object type

        // Act & Assert
        assertNotEquals(listSellersCommand, differentObject); // Comparing with a different type of object
    }

}
