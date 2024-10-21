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

public class ListPropertiesCommandTest {
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
    public void listPropertiesCommandGeneration() {
        Command command = new ListPropertiesCommand();

        // Check if command is an instance of ListPropertiesCommand
        assertTrue(command instanceof ListPropertiesCommand,
                "Command should be an instance of ListPropertiesCommand");
    }

    @Test
    public void testExecuteProperties() throws CommandException {
        Command command = new ListPropertiesCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all properties");
    }

    @Test
    public void keywordPropertiesBuyers() {
        assertEquals("properties", ListPropertiesCommand.KEY_WORD);
    }

    @Test
    void equals_sameCommand_returnsTrue() {
        // Arrange
        ListPropertiesCommand listPropertiesCommand1 = new ListPropertiesCommand();
        ListPropertiesCommand listPropertiesCommand2 = new ListPropertiesCommand(); // Same command

        // Act & Assert
        assertEquals(listPropertiesCommand1, listPropertiesCommand2); // Different instances, same command type
    }

    @Test
    void equals_differentCommand_returnsFalse() {
        // Arrange
        ListPropertiesCommand listPropertiesCommand = new ListPropertiesCommand();
        ListSellersCommand listSellersCommand = new ListSellersCommand(); // Different subclass of ListCommand

        // Act & Assert
        assertNotEquals(listPropertiesCommand, listSellersCommand); // Commands should not be equal
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        ListPropertiesCommand listPropertiesCommand = new ListPropertiesCommand();
        Object differentObject = new Object(); // Completely different object type

        // Act & Assert
        assertNotEquals(listPropertiesCommand, differentObject); // Comparing with a different type of object
    }

}
