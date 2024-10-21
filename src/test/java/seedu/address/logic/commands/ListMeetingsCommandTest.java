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

public class ListMeetingsCommandTest {
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
    public void listMeetingsCommandGeneration() {
        Command command = new ListMeetingsCommand();

        // Check if command is an instance of ListMeetingsCommand
        assertTrue(command instanceof ListMeetingsCommand,
                "Command should be an instance of ListMeetingsCommand");
    }

    @Test
    public void testExecuteMeetings() throws CommandException {
        Command command = new ListMeetingsCommand();
        CommandResult result = command.execute(this.model);
        assertEquals(result.getFeedbackToUser(), "Listed all meetings");
    }

    @Test
    public void keywordMeetingsBuyers() {
        assertEquals("meetings", ListMeetingsCommand.KEY_WORD);
    }

    @Test
    void equals_sameCommand_returnsTrue() {
        // Arrange
        ListMeetingsCommand listMeetingsCommand1 = new ListMeetingsCommand();
        ListMeetingsCommand listMeetingsCommand2 = new ListMeetingsCommand(); // Same command

        // Act & Assert
        assertEquals(listMeetingsCommand1, listMeetingsCommand2); // Different instances, same command type
    }

    @Test
    void equals_differentCommand_returnsFalse() {
        // Arrange
        ListMeetingsCommand listMeetingsCommand = new ListMeetingsCommand();
        ListPropertiesCommand listPropertiesCommand = new ListPropertiesCommand(); // Different subclass of ListCommand

        // Act & Assert
        assertNotEquals(listMeetingsCommand, listPropertiesCommand); // Commands should not be equal
    }

    @Test
    void equals_differentObject_returnsFalse() {
        // Arrange
        ListMeetingsCommand listMeetingsCommand = new ListMeetingsCommand();
        Object differentObject = new Object(); // Completely different object type

        // Act & Assert
        assertNotEquals(listMeetingsCommand, differentObject); // Comparing with a different type of object
    }

}
