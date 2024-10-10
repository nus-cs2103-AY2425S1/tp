package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GradeTest {

    @Test
    public void isValidGrade() {
        // grade between 0 and 100 -> return true
        assertTrue(Grade.isValidGrade(100.00F));

        // grade greater than 100 -> return false
        assertFalse(Grade.isValidGrade(100.01F));

        // grade lower than 0 -> return false
        assertFalse(Grade.isValidGrade(-0.01F));
    }

    @Test
    public void isValidWeightage() {
        // grade between 0 and 100 -> return true
        assertTrue(Grade.isValidWeightage(100.00F));

        // grade greater than 100 -> return false
        assertFalse(Grade.isValidWeightage(100.01F));

        // grade lower than 0 -> return false
        assertFalse(Grade.isValidWeightage(-0.01F));
    }

    @Test
    public void equals() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("FInal", 86.4F, 25.0F);
        Grade thirdGrade = new Grade("Midterm", 86.5F, 25.0F);
        Grade fourthGrade = new Grade("Midterm", 86.4F, 25.1F);
        Grade fifthGrade = new Grade("Midterm", 86.4F, 25.0F);

        // same values -> returns true
        assertTrue(firstGrade.equals(fifthGrade));

        // same object -> returns true
        assertTrue(firstGrade.equals(firstGrade));

        // null -> returns false
        assertFalse(firstGrade.equals(null));

        //different types -> returns false
        assertFalse(firstGrade.equals(5));

        // different test name -> returns false
        assertFalse(firstGrade.equals(secondGrade));

        // different score -> returns false
        assertFalse(firstGrade.equals(thirdGrade));

        // different weightage -> returns false
        assertFalse(firstGrade.equals(fourthGrade));
    }

    @Test
    public void toStringMethod() {
        String expected = "Midterm: 86.4%";
        assertEquals(expected, new Grade("Midterm", 86.4F, 25.0F).toString());
    }
}
