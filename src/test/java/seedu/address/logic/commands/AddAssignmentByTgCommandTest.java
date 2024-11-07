package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.AddAssignmentByTgCommand.MESSAGE_NO_TUTORIAL_GROUP_FOUND;
import static seedu.address.logic.commands.AddAssignmentByTgCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_DIDDY;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalStudents.DIDDY;
import static seedu.address.testutil.TypicalStudents.HUGH;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.AssignmentBuilder;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.StudentBuilder;

class AddAssignmentByTgCommandTest {

    private ModelStub modelStub;
    private TutorialGroup tutorialGroup;
    private Assignment assignment;

    @BeforeEach
    void setUp() {
        modelStub = new ModelStubWithStudents();
        tutorialGroup = new TutorialGroup(VALID_TUTORIAL_GROUP_DIDDY);
        assignment = new AssignmentBuilder(MATH_ASSIGNMENT_SUBMITTED).build();
    }

    @Test
    void execute_validTutorialGroup_success() throws CommandException {
        AddAssignmentByTgCommand command = new AddAssignmentByTgCommand(assignment, tutorialGroup);

        CommandResult result = command.execute(modelStub);
        String expectedMessage = String.format(MESSAGE_SUCCESS, assignment.getAssignmentName(), tutorialGroup);

        assertEquals(expectedMessage, result.getFeedbackToUser());
        for (Student student : modelStub.getStudentsByTutorialGroup(tutorialGroup)) {
            assertEquals(assignment, student.getAssignment(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()));
        }
    }

    @Test
    void execute_invalidTutorialGroup_throwsCommandException() {
        AddAssignmentByTgCommand command = new AddAssignmentByTgCommand(assignment, new TutorialGroup("T99"));

        assertThrows(CommandException.class, () -> command.execute(modelStub), MESSAGE_NO_TUTORIAL_GROUP_FOUND);
    }

    @Test
    void undo_success() throws CommandException {
        AddAssignmentByTgCommand command = new AddAssignmentByTgCommand(assignment, tutorialGroup);
        command.execute(modelStub);

        command.undo(modelStub);
        for (Student student : modelStub.getStudentsByTutorialGroup(tutorialGroup)) {
            assertEquals(null, student.getAssignment(MATH_ASSIGNMENT_SUBMITTED.getAssignmentName()));
        }
    }

    // ModelStub class to simulate behavior of Model for testing
    private class ModelStubWithStudents extends ModelStub {
        private List<Student> students = new ArrayList<>();

        ModelStubWithStudents() {
            students.add(new StudentBuilder(DIDDY).build());
            students.add(new StudentBuilder(HUGH).build());
        }

        @Override
        public List<Student> getStudentsByTutorialGroup(TutorialGroup tutorialGroup) {
            if (this.students.stream().anyMatch(student -> student.getTutorialGroup().equals(tutorialGroup))) {
                return this.students;
            }
            return new ArrayList<>();
        }
    }
}
