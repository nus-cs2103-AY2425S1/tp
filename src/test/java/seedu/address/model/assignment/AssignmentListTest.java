package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAssignments.getTypicalAssignmentList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.model.AddressBook;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.assignment.exceptions.DuplicateAssignmentException;
import seedu.address.model.student.Student;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalStudents;

public class AssignmentListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignmentList(null));
    }

    @Test
    public void constructor_duplicateAssignment_throwsDuplicateAssignmentException() {
        ArrayList<Assignment> duplicateAssignments = new ArrayList<>();
        Assignment assignment = TypicalAssignments.ASSIGNMENT1;
        duplicateAssignments.add(assignment);
        duplicateAssignments.add(assignment);
        assertThrows(DuplicateAssignmentException.class, () -> new AssignmentList(duplicateAssignments));
    }

    @Test
    public void constructor_constructAssignmentsCorrectly() {
        LocalDateTime date1 = LocalDateTime.of(2024, 10, 14, 23, 59);
        LocalDateTime date2 = LocalDateTime.of(2025, 10, 14, 23, 59);
        Assignment assignment1 = new Assignment("Assignment 1", date1);
        Assignment assignment2 = new Assignment("Assignment 2", date2);
        ArrayList<Assignment> assignments = new ArrayList<>();
        assignments.add(assignment1);
        assignments.add(assignment2);
        AssignmentList assignmentList = new AssignmentList(assignments);
        assertTrue(assignmentList.getAssignments().contains(assignment1));
        assertTrue(assignmentList.getAssignments().contains(assignment2));
    }

    @Test
    public void addAssignment_duplicateAssignment_throwsException() {
        AssignmentList list = new AssignmentList();
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);
        LocalDateTime date2 = LocalDateTime.of(2025, 10, 14, 23, 59);
        Assignment duplicateAssignment = new Assignment("Assignment 1", date2);

        list.addAssignment(assignment);
        assertThrows(DuplicateAssignmentException.class, () -> list.addAssignment(assignment));
        assertThrows(DuplicateAssignmentException.class, () -> list.addAssignment(duplicateAssignment));
    }

    @Test
    public void deleteAssignment_notInList_throwsException() {
        AssignmentList list = new AssignmentList();
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);

        assertThrows(AssignmentNotFoundException.class, () -> list.deleteAssignment(assignment));
    }

    @Test
    public void getStatus_returnsCorrectStatusString() throws AssignmentNotFoundException {
        AssignmentList list = new AssignmentList();
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);
        list.addAssignment(assignment);

        AddressBook addressBook = TypicalStudents.getTypicalAddressBook();
        ObservableList<Student> students = addressBook.getStudentList();

        AssignmentList assignmentList = new AssignmentList();
        assignmentList.addAssignment(assignment);
        assignmentList.setStatus(assignment, students.get(0), true);
        assignmentList.setStatus(assignment, students.get(1), true);

        String status = assignmentList.getStatus(assignment, students);
        assertTrue(status.contains("Students who have completed: \nAlice Pauline, Benson Meier"));
        assertTrue(status.contains("Students who have not completed: \nCarl Kurz"));
    }

    @Test
    public void addAndDeleteAssignment_success() throws AssignmentNotFoundException {
        AssignmentList list = new AssignmentList();
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);

        list.addAssignment(assignment);
        assertTrue(list.hasAssignment(assignment));

        list.deleteAssignment(assignment);
        assertFalse(list.hasAssignment(assignment));
    }

    @Test
    public void setStatus_updatesAssignmentStatusCorrectly() throws AssignmentNotFoundException {
        AssignmentList assignmentList = new AssignmentList();
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);
        assignmentList.addAssignment(assignment);

        List<Student> students = TypicalStudents.getTypicalStudents();
        boolean bool = true;
        for (Student student : students) {
            if (bool) {
                assignmentList.setStatus(assignment, student, true);
            }
            bool = !bool;
        }
        assertEquals(4, assignment.getNumOfCompletedStudents());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        AssignmentList assignments = new AssignmentList();
        Assert.assertThrows(NullPointerException.class, () -> assignments.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AssignmentList assignments = new AssignmentList();
        AssignmentList newData = getTypicalAssignmentList();
        assignments.resetData(newData);
        assertEquals(newData, assignments);
    }

}
