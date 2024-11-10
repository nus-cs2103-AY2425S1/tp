package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author gho7sie

public class TaskNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TaskName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new TaskName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> TaskName.isValidName(null));

        // invalid name
        assertFalse(TaskName.isValidName("")); // empty string
        assertFalse(TaskName.isValidName(" ")); // spaces only
        assertFalse(TaskName.isValidName("                 ")); // many spaces

        // valid name
        assertTrue(TaskName.isValidName("test task")); // alphabets only
        assertTrue(TaskName.isValidName("12345")); // numbers only
        assertTrue(TaskName.isValidName("task 2nd")); // alphanumeric characters
        assertTrue(TaskName.isValidName("TASK 2ND")); // with capital letters
        assertTrue(TaskName.isValidName("Betray David Roger Jackson Ray Jr 2nd")); // long task name
        assertTrue(TaskName.isValidName("ËÛ<xuó×üi±ñ&mÊÎ8K5_«$^õjÇ1U_¡ä")); // with extended ASCII
    }
}
