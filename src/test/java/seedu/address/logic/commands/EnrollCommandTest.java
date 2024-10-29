package seedu.address.logic.commands;

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

import static org.junit.jupiter.api.Assertions.*;

public class EnrollCommandTest {

    private Model testModel;
    private Person testStudent;
    private Tutorial testTutorial;
    private Participation testParticipation;

    @BeforeEach
    public void setUp() {
        // Initialize the model with empty lists
        testModel = new ModelManager();

        // Create test data for a student and a tutorial
        testStudent = new PersonBuilder().build();
        testTutorial = new Tutorial("physics");

        // Add the student and tutorial to the model
        testModel.addPerson(testStudent);
        testModel.createTutorial(testTutorial);

        // Create the participation record
        testParticipation = new Participation(testStudent, testTutorial);
    }

    @Test
    public void execute_successfulEnrollment() throws CommandException {
        // EnrollCommand with a valid index and existing tutorial subject
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(0), "physics");

        // Check for successful command execution
        CommandResult result = command.execute(testModel);

        // Check that participation is added successfully
        assertEquals(String.format(EnrollCommand.MESSAGE_SUCCESS, testStudent.getFullName(), testTutorial.getSubject()),
                result.getFeedbackToUser());
        assertTrue(testModel.hasParticipation(testParticipation));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        // Create an EnrollCommand with an invalid index (out of bounds)
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(1), "physics");

        // Expect a CommandException due to invalid index
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, exception.getMessage());
    }

    @Test
    public void execute_tutorialNotFound_throwsCommandException() {
        // Create an EnrollCommand with a tutorial subject that does not exist
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(0), "math");

        // Expect a CommandException due to nonexistent tutorial subject
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_SUBJECT, exception.getMessage());
    }

    @Test
    public void execute_duplicateParticipation_throwsCommandException() throws CommandException {
        // Add participation for the student to the tutorial
        testModel.addParticipation(testParticipation);

        // Create an EnrollCommand for the same student and tutorial
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(0), "physics");

        // Expect a CommandException due to duplicate participation
        Exception exception = assertThrows(CommandException.class, () -> command.execute(testModel));
        assertEquals(EnrollCommand.MESSAGE_DUPLICATE_PERSON, exception.getMessage());
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        EnrollCommand command1 = new EnrollCommand(Index.fromZeroBased(0), "physics");
        EnrollCommand command2 = new EnrollCommand(Index.fromZeroBased(0), "physics");

        assertEquals(command1, command2);
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        EnrollCommand command1 = new EnrollCommand(Index.fromZeroBased(0), "physics");
        EnrollCommand command2 = new EnrollCommand(Index.fromZeroBased(1), "math");

        assertNotEquals(command1, command2);
    }

    @Test
    public void toString_containsIndexAndSubject() {
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(0), "physics");

        String result = command.toString();

        assertTrue(result.contains("toEnroll"));
        assertTrue(result.contains("0")); // Check that index is present
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        EnrollCommand command = new EnrollCommand(Index.fromZeroBased(0), "physics");

        assertThrows(NullPointerException.class, () -> command.execute(null));
    }

    @Test
    public void constructor_nullIndexOrSubject_throwsNullPointerException() {
        // Test null index
        assertThrows(NullPointerException.class, () -> new EnrollCommand(null, "physics"));

        // Test null subject
        assertThrows(NullPointerException.class, () -> new EnrollCommand(Index.fromZeroBased(0), null));
    }
}

