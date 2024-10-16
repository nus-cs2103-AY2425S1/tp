package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityLevel;
import seedu.address.testutil.PersonBuilder;

public class DeletePriorityCommandTest {
    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToEdit).withPriorityLevel(3).build();

        DeletePriorityCommand deletePriorityCommand = new DeletePriorityCommand(INDEX_FIRST_PERSON.getOneBased());
        String expectedMessage = String.format("Priority level reset to default for %s", editedPerson.getName());

        // Execute the command
        CommandResult commandResult = deletePriorityCommand.execute(model);

        // Check that the command executed successfully
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        // Verify the person's priority level has been reset to default
        assertEquals(new PriorityLevel(3), model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased()).getPriorityLevel());
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        DeletePriorityCommand deletePriorityCommand =
                new DeletePriorityCommand(model.getFilteredPersonList().size() + 1);
        assertThrows(CommandException.class, () -> deletePriorityCommand.execute(model));
    }

    @Test
    public void equals() {
        DeletePriorityCommand deleteFirstCommand = new DeletePriorityCommand(INDEX_FIRST_PERSON.getOneBased());
        DeletePriorityCommand deleteSecondCommand = new DeletePriorityCommand(INDEX_FIRST_PERSON.getOneBased());

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        assertEquals(deleteFirstCommand, deleteSecondCommand);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);
    }
}
