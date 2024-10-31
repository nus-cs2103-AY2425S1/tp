package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.participation.Participation;
import seedu.address.model.person.Person;
import seedu.address.model.tutorial.Tutorial;
import seedu.address.testutil.PersonBuilder;



public class UnenrollCommandTest {

    private Model model;
    private Person testStudent;
    private Tutorial testTutorial;
    private Participation testParticipation;

    @BeforeEach
    public void setUp() {
        // Initialize model with empty lists
        model = new ModelManager();

        // Create test data for a student and tutorial
        testStudent = new PersonBuilder().build();
        testTutorial = new Tutorial("physics");

        // Add the student and tutorial to the model
        model.addPerson(testStudent);
        model.createTutorial(testTutorial);

        // Create the participation record
        testParticipation = new Participation(testStudent, testTutorial, new ArrayList<>());
    }

    @Test
    public void execute_successfulUnenroll() throws CommandException {
        // First, add participation to the model to simulate an enrolled student
        model.addParticipation(testParticipation);

        // Create UnenrollCommand with a valid index and existing tutorial subject
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(0), "physics");

        // Execute the command and check for successful unenrollment
        CommandResult result = command.execute(model);

        assertEquals("Amy Bee(student) no longer enrolled in physics(tutorial)", result.getFeedbackToUser());
        assertFalse(model.hasParticipation(testParticipation));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Create an UnenrollCommand with an invalid index (out of bounds)
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(1), "physics");

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    void execute_studentIndexOutOfBounds_throwsCommandException() {
        // Only 1 student in list
        Index invalidIndex = Index.fromOneBased(2);
        EnrollCommand enrollCommand = new EnrollCommand(invalidIndex, "Physics");

        Exception exception = assertThrows(CommandException.class, () -> enrollCommand.execute(model));
        assertEquals(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        // Create an UnenrollCommand with a tutorial subject that does not exist
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(0), "math");

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_SUBJECT, exception.getMessage());
    }

    @Test
    public void execute_noSuchParticipation_throwsCommandException() {
        // Create an UnenrollCommand for a student not enrolled in the tutorial
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(0), "physics");

        Exception exception = assertThrows(CommandException.class, () -> command.execute(model));
        assertEquals(UnenrollCommand.MESSAGE_NO_SUCH_PARTICIPATION, exception.getMessage());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        UnenrollCommand command1 = new UnenrollCommand(Index.fromZeroBased(0), "physics");
        UnenrollCommand command2 = new UnenrollCommand(Index.fromZeroBased(0), "physics");

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        UnenrollCommand command1 = new UnenrollCommand(Index.fromZeroBased(0), "physics");
        UnenrollCommand command2 = new UnenrollCommand(Index.fromZeroBased(1), "math");

        assertNotEquals(command1, command2);
    }

    @Test
    public void toString_containsIndexAndSubject() {
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(0), "physics");

        String result = command.toString();

        assertTrue(result.contains("toUnenroll"));
        assertTrue(result.contains("0")); // Check that index is present
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        UnenrollCommand command = new UnenrollCommand(Index.fromZeroBased(0), "physics");

        assertThrows(AssertionError.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullIndexOrSubject_throwsNullPointerException() {
        // Test null index
        assertThrows(NullPointerException.class, () -> new UnenrollCommand(null, "physics"));

        // Test null subject
        assertThrows(NullPointerException.class, () -> new UnenrollCommand(Index.fromZeroBased(0), null));
    }
}

