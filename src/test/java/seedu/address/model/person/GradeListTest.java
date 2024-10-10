package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GradeListTest {

    @Test
    public void addGrade() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.4F, 25.0F);

        GradeList firstGradeList = new GradeList();
        GradeList secondGradeList = new GradeList();

        // same size and values -> returns true
        firstGradeList.addGrade(firstGrade);
        secondGradeList.addGrade(firstGrade);
        secondGradeList.addGrade(firstGrade);
        assertTrue(firstGradeList.equals(secondGradeList));

        // different size -> returns false
        secondGradeList.addGrade(secondGrade);
        assertFalse(firstGradeList.equals(secondGradeList));
    }

    @Test
    public void getGrade() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.6F, 45.0F);
        Grade thirdGrade = new Grade("Assignment", 95.4F, 15.0F);
        Grade fourthGrade = new Grade("Tutorial", 100.0F, 5.0F);

        GradeList gradeList = new GradeList();
        gradeList.addGrade(firstGrade);
        gradeList.addGrade(secondGrade);
        gradeList.addGrade(thirdGrade);

        // same name -> returns true
        assertEquals(gradeList.getGrade("midterm"), firstGrade);
        assertEquals(gradeList.getGrade("Final"), secondGrade);
        assertEquals(gradeList.getGrade("AsSiGnMeNt"), thirdGrade);

        // different name -> returns false
        assertEquals(gradeList.getGrade("Tutorial"), null);
        assertEquals(gradeList.getGrade("assignment "), null);
    }

    @Test
    public void equals() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("FInal", 86.4F, 25.0F);
        Grade thirdGrade = new Grade("Midterm", 86.5F, 25.0F);
        Grade fourthGrade = new Grade("Midterm", 86.4F, 25.0F);

        GradeList firstGradeList = new GradeList();
        GradeList secondGradeList = new GradeList();

        firstGradeList.addGrade(firstGrade);
        secondGradeList.addGrade(fourthGrade);

        // same object -> returns true
        assertTrue(firstGradeList.equals(firstGradeList));

        // same values -> returns true
        assertTrue(firstGradeList.equals(secondGradeList));

        // null -> returns false
        assertFalse(firstGradeList.equals(null));

        // different values -> returns false
        secondGradeList.addGrade(thirdGrade);
        assertFalse(firstGradeList.equals(secondGradeList));

        // different size -> returns false
        secondGradeList.addGrade(secondGrade);
        assertFalse(firstGradeList.equals(secondGradeList));
    }
}
