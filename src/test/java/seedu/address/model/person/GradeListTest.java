package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
        firstGradeList = firstGradeList.addGrade(firstGrade);
        secondGradeList = secondGradeList.addGrade(firstGrade);
        assertTrue(firstGradeList.equals(secondGradeList));

        // different size -> returns false
        secondGradeList = secondGradeList.addGrade(secondGrade);
        assertFalse(firstGradeList.equals(secondGradeList));
    }

    @Test
    public void getGrade() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.6F, 45.0F);
        Grade thirdGrade = new Grade("Assignment", 95.4F, 15.0F);
        Grade fourthGrade = new Grade("Tutorial", 100.0F, 5.0F);

        GradeList gradeList = new GradeList();
        gradeList = gradeList.addGrade(firstGrade);
        gradeList = gradeList.addGrade(secondGrade);
        gradeList = gradeList.addGrade(thirdGrade);

        // same name -> returns true
        assertEquals(gradeList.getGrade("Midterm"), firstGrade);
        assertEquals(gradeList.getGrade("Final"), secondGrade);
        assertEquals(gradeList.getGrade("Assignment"), thirdGrade);

        // non-existing test name -> returns null
        assertNull(gradeList.getGrade("Tutorial"));
    }

    @Test
    public void equals() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.4F, 25.0F);
        Grade thirdGrade = new Grade("Midterm", 86.5F, 25.0F);
        Grade fourthGrade = new Grade("Midterm", 86.4F, 25.0F);

        GradeList firstGradeList = new GradeList();
        GradeList secondGradeList = new GradeList();

        firstGradeList = firstGradeList.addGrade(firstGrade);
        secondGradeList = secondGradeList.addGrade(fourthGrade);

        // same object -> returns true
        assertTrue(firstGradeList.equals(firstGradeList));

        // same values -> returns true
        assertTrue(firstGradeList.equals(secondGradeList));

        // null -> returns false
        assertFalse(firstGradeList.equals(null));

        // different values -> returns false
        secondGradeList = secondGradeList.addGrade(thirdGrade);
        assertFalse(firstGradeList.equals(secondGradeList));

        // different size -> returns false
        secondGradeList = secondGradeList.addGrade(secondGrade);
        assertFalse(firstGradeList.equals(secondGradeList));
    }
}
