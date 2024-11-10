package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_DIDDY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAssignments.MATH_ASSIGNMENT_SUBMITTED;
import static seedu.address.testutil.TypicalStudents.HUGH;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.assignment.*;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.StudentBuilder;

public class AddAssignmentCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }
    @Test
    public void execute_newAssignment_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy2);
        assertCommandSuccess(new AddAssignmentCommand(HUGH.getName(), MATH_ASSIGNMENT_SUBMITTED), model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }

    @Test
    public void execute_newAssignment2_success() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy2);
        assertCommandSuccess(new AddAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED,
                        new StudentNumber(VALID_STUDENT_NUMBER_HUGH)),
                model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);
    }

    @Test
    public void undo_after_execute() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy2);
        AddAssignmentCommand addAssignmentCommand = new AddAssignmentCommand(HUGH.getName(), MATH_ASSIGNMENT_SUBMITTED);
        assertCommandSuccess(addAssignmentCommand, model,
                String.format(AddAssignmentCommand.MESSAGE_SUCCESS,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(), HUGH.getName()),
                expectedModel);

        assertTrue(addAssignmentCommand.undo(model));
        assertTrue(hughCopy2.getAssignments().size() == 0);
    }

    @Test
    public void execute_addAssignmentWithNoStudents_failure() {
        assertCommandFailure(new AddAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED),
                model,
                AddAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);

    }

    @Test
    public void execute_addAssignmentWithDuplicateStudents_failure() {
        Student hughCopy = new StudentBuilder(HUGH)
                .withStudentNumber(VALID_STUDENT_NUMBER_DIDDY)
                .build();
        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy);
        model.addStudent(hughCopy2);

        assertCommandFailure(new AddAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED),
                model,
                String.format(AddAssignmentCommand.MESSAGE_DUPLICATE_STUDENT,
                        VALID_STUDENT_NUMBER_DIDDY + ", " + VALID_STUDENT_NUMBER_HUGH));

    }

    @Test
    public void execute_addDuplicateAssignments_failure() {
        Student hughCopy = new StudentBuilder(HUGH)
                .build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        model.addStudent(hughCopy);

        assertCommandFailure(new AddAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED),
                model,
                String.format(AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT_FOUND,
                        MATH_ASSIGNMENT_SUBMITTED.getAssignmentName(),
                        hughCopy.getName()));
    }

    @Test
    public void execute_mismatchedStudentNumber_failure() {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Student hughCopy = new StudentBuilder(HUGH).build();
        hughCopy.addAssignment(MATH_ASSIGNMENT_SUBMITTED);
        expectedModel.addStudent(hughCopy);

        Student hughCopy2 = new StudentBuilder(HUGH).build();
        model.addStudent(hughCopy2);
        assertCommandFailure(new AddAssignmentCommand(HUGH.getName(),
                        MATH_ASSIGNMENT_SUBMITTED,
                        new StudentNumber(VALID_STUDENT_NUMBER_DIDDY)),
                model,
                AddAssignmentCommand.MESSAGE_NO_STUDENT_FOUND);
    }

    @Test
    public void equalsMethod() {
        Assignment assignment1 = new Assignment(new AssignmentName("Math Quiz"), new Deadline("2024-10-09"),
                new Status("N"), new Grade("NULL"));
        Assignment assignment2 = new Assignment(new AssignmentName("Science Quiz"), new Deadline("2024-10-10"),
                new Status("N"), new Grade("NULL"));
        Name name1 = new Name("Jane Doe");
        Name name2 = new Name("John Doe");
        StudentNumber studentNumber1 = new StudentNumber("A1234567L");
        StudentNumber studentNumber2 = new StudentNumber("A1234568M");

        AddAssignmentCommand command1 = new AddAssignmentCommand(name1, assignment1);
        AddAssignmentCommand command2 = new AddAssignmentCommand(name1, assignment1);
        AddAssignmentCommand command3 = new AddAssignmentCommand(name1, assignment2);
        AddAssignmentCommand command4 = new AddAssignmentCommand(name2, assignment1);
        AddAssignmentCommand command5 = new AddAssignmentCommand(name1, assignment1, studentNumber1);
        AddAssignmentCommand command6 = new AddAssignmentCommand(name1, assignment1, studentNumber2);

        // Same object
        assertEquals(command1, command1);

        // Different objects, same values
        assertEquals(command1, command2);

        // Different assignments
        assertNotEquals(command1, command3);

        // Different names
        assertNotEquals(command1, command4);

        // Different student numbers
        assertNotEquals(command5, command6);

        // Null
        assertNotEquals(command1, null);

        // Different types
        assertNotEquals(command1, new Object());
    }

}
