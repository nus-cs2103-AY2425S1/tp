package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.participation.Participation;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.ParticipationBuilder;
import seedu.address.testutil.TutorialBuilder;

public class CloseTutorialCommandTest {

    private Model testModel;
    private Tutorial testTutorial;
    private Participation testParticipation;

    @BeforeEach
    public void setUp() {
        // Initialize the model
        testModel = new ModelManager();

        // Create test data for a tutorial and a participation
        testTutorial = new TutorialBuilder().withSubject("Physics").build();
        testParticipation = new ParticipationBuilder().withTutorial(testTutorial).build();

        // Add the tutorial and participation to the model
        testModel.createTutorial(testTutorial);
        testModel.addParticipation(testParticipation);
    }

    @Test
    public void execute_successfulClosure() throws CommandException {
        // Create a CloseTutorialCommand
        CloseTutorialCommand command = new CloseTutorialCommand(testTutorial);

        // Execute the command
        CommandResult result = command.execute(testModel);

        // Verify the result
        assertEquals(String.format(CloseTutorialCommand.MESSAGE_CLOSE_TUTORIAL_SUCCESS,
                Messages.formatTutorial(testTutorial)), result.getFeedbackToUser());
        assertFalse(testModel.hasTutorial(testTutorial)); // Tutorial should be closed
        assertFalse(testModel.getParticipationList().contains(testParticipation)); // Participation should be removed
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        // Create a CloseTutorialCommand with a non-existing tutorial
        Tutorial nonExistingTutorial = new TutorialBuilder().withSubject("Math").build();
        CloseTutorialCommand command = new CloseTutorialCommand(nonExistingTutorial);

        // Expect a CommandException
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(String.format(Messages.MESSAGE_TUTORIAL_NOT_FOUND, nonExistingTutorial.getSubject()),
                exception.getMessage());
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        CloseTutorialCommand command = new CloseTutorialCommand(testTutorial);
        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CloseTutorialCommand(null));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        CloseTutorialCommand command1 = new CloseTutorialCommand(testTutorial);
        CloseTutorialCommand command2 = new CloseTutorialCommand(testTutorial);

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        CloseTutorialCommand command1 = new CloseTutorialCommand(testTutorial);
        CloseTutorialCommand command2 = new CloseTutorialCommand(new TutorialBuilder().withSubject("Math").build());

        assertNotEquals(command1, command2);
    }

    @Test
    public void toString_containsTutorialSubject() {
        CloseTutorialCommand command = new CloseTutorialCommand(testTutorial);
        String result = command.toString();

        assertTrue(result.contains("Close Tutorial: "));
        assertTrue(result.contains(testTutorial.getSubject())); // Check that subject is present
    }

    @Test
    public void execute_removingParticipation_successful() throws CommandException {
        // Create a CloseTutorialCommand
        CloseTutorialCommand command = new CloseTutorialCommand(testTutorial);

        // Execute the command to close tutorial
        command.execute(testModel);

        // Verify that participation is removed
        assertFalse(testModel.getParticipationList().contains(testParticipation));
    }

    @Test
    public void execute_closingAlreadyClosedTutorial_throwsCommandException() throws CommandException {
        // Close the tutorial first
        CloseTutorialCommand command = new CloseTutorialCommand(testTutorial);
        command.execute(testModel);

        // Try to close the same tutorial again
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(String.format(Messages.MESSAGE_TUTORIAL_NOT_FOUND, testTutorial.getSubject()),
                exception.getMessage());
    }
}
