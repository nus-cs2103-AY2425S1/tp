package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.TutorialBuilder;

public class CreateTutorialCommandTest {
    private Model testModel;
    private Tutorial testTutorial;

    @BeforeEach
    public void setUp() {
        // Initialize the model
        testModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // Create a test tutorial
        testTutorial = new TutorialBuilder().withSubject("Physics").build();
    }

    @Test
    public void execute_successfulCreation() throws CommandException {
        // Create a CreateTutorialCommand with the test tutorial
        CreateTutorialCommand command = new CreateTutorialCommand(testTutorial);

        // Execute the command
        CommandResult result = command.execute(testModel);

        // Verify the result
        assertEquals(String.format(CreateTutorialCommand.MESSAGE_SUCCESS_TUTORIAL,
                        Messages.formatTutorial(testTutorial)),
                result.getFeedbackToUser());
        assertTrue(testModel.hasTutorial(testTutorial)); // Check that the tutorial was added to the model
    }

    @Test
    public void execute_duplicateTutorial_throwsCommandException() throws CommandException {
        // Add the tutorial to the model first
        testModel.createTutorial(testTutorial);

        // Create a CreateTutorialCommand for the same tutorial
        CreateTutorialCommand command = new CreateTutorialCommand(testTutorial);

        // Expect a CommandException due to duplicate tutorial
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(CreateTutorialCommand.MESSAGE_DUPLICATE_TUTORIAL, exception.getMessage());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        CreateTutorialCommand command = new CreateTutorialCommand(testTutorial);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullTutorial_throwsNullPointerException() {
        // Test null tutorial
        assertThrows(NullPointerException.class, () -> new CreateTutorialCommand(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CreateTutorialCommand command1 = new CreateTutorialCommand(testTutorial);
        CreateTutorialCommand command2 = new CreateTutorialCommand(testTutorial);

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        CreateTutorialCommand command1 = new CreateTutorialCommand(testTutorial);
        CreateTutorialCommand command2 = new CreateTutorialCommand(new TutorialBuilder().withSubject("Math").build());

        assertNotEquals(command1, command2);
    }

    @Test
    public void toString_containsTutorialSubject() {
        CreateTutorialCommand command = new CreateTutorialCommand(testTutorial);
        String result = command.toString();

        assertTrue(result.contains(testTutorial.getSubject())); // Check that subject is present
    }
}
