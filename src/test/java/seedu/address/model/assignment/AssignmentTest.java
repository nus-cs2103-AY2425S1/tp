package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class AssignmentTest {

    @Test
    public void constructor_noStatus_initializesCorrectly() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);

        assertEquals("Assignment 1", assignment.getTitle());
        assertEquals(date, assignment.getDueDate());
        assertEquals(0, assignment.getStatuses().size());
    }

    @Test
    public void constructor_withStatus_initializesCorrectly() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        HashMap<String, Boolean> statuses = new HashMap<>();
        statuses.put("1", true);
        statuses.put("2", false);
        Assignment assignment = new Assignment("Assignment 1", date, statuses);

        assertEquals(true, assignment.getStatuses().get("1"));
        assertEquals(false, assignment.getStatuses().get("2"));
        assertFalse(assignment.getStatus("100"));
        assertTrue(assignment.getStatus("1"));
        assertEquals(2, assignment.getStatuses().size());
    }

    @Test
    public void markStatus_updatesStatusCorrectly() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);

        // Mark student 1 as complete
        assignment.markStatus("1", true);
        assertTrue(assignment.getStatus("1"));

        // Mark student 2 as incomplete
        assignment.markStatus("2", false);
        assertFalse(assignment.getStatus("2"));

        // Change status of student 1 to incomplete
        assignment.markStatus("1", false);
        assertFalse(assignment.getStatus("1"));
    }

    @Test
    public void equals_sameTitle_returnTrue() {
        LocalDateTime date1 = LocalDateTime.of(2024, 10, 14, 23, 59);
        LocalDateTime date2 = LocalDateTime.of(2024, 10, 15, 23, 59);
        Assignment assignment1 = new Assignment("Assignment 1", date1);
        Assignment assignment2 = new Assignment("Assignment 1", date2);

        assertEquals(assignment1, assignment2);
    }

    @Test
    public void equals_differentTitle_returnFalse() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment1 = new Assignment("Assignment 1", date);
        Assignment assignment2 = new Assignment("Assignment 2", date);

        assertNotEquals(assignment1, assignment2);
    }

    @Test
    public void markStatus_updatesNumCorrectly() {
        LocalDateTime date = LocalDateTime.of(2024, 10, 14, 23, 59);
        Assignment assignment = new Assignment("Assignment 1", date);
        assignment.markStatus("1", true);
        assertEquals(1, assignment.getNumOfCompletedStudents());
        assignment.markStatus("2", false);
        assertEquals(1, assignment.getNumOfCompletedStudents());
        assignment.markStatus("1", false);
        assertEquals(0, assignment.getNumOfCompletedStudents());
        assignment.markStatus("2", true);
        assertEquals(1, assignment.getNumOfCompletedStudents());
    }
}
