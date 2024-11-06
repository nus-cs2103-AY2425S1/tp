package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

public class GradeListTest {

    @Test
    public void addGrade() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.4F, 25.0F);
        Grade thirdGrade = new Grade("Assignment 1", 100.0F, 50.0F);
        Grade fourthGrade = new Grade("Assignment 2", 100.0F, 51.0F);

        GradeList firstGradeList = new GradeList();
        GradeList secondGradeList = new GradeList();

        // same size and values -> returns true
        firstGradeList = firstGradeList.addGrade(firstGrade);
        secondGradeList = secondGradeList.addGrade(firstGrade);
        assertTrue(firstGradeList.equals(secondGradeList));

        // different size -> returns false
        secondGradeList = secondGradeList.addGrade(secondGrade);
        assertFalse(firstGradeList.equals(secondGradeList));

        // Adding grades that don't exceed 100% weightage
        firstGradeList = firstGradeList.addGrade(secondGrade);
        assertTrue(firstGradeList.getTotalWeightage() <= 100);

        // Adding grade that brings weightage to exactly 100%
        firstGradeList = firstGradeList.addGrade(thirdGrade);
        assertEquals(100, firstGradeList.getTotalWeightage());

        // weightage over 100% -> throws runtime exception
        GradeList finalFirstGradeList = firstGradeList;
        assertThrows(RuntimeException.class, () -> finalFirstGradeList.addGrade(fourthGrade));
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
        assertEquals(gradeList.getGrade("midterm"), firstGrade);
        assertEquals(gradeList.getGrade("final"), secondGrade);
        assertEquals(gradeList.getGrade("assignment"), thirdGrade);

        // non-existing test name -> returns null
        assertNull(gradeList.getGrade("tutorial"));
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

    @Test
    public void constructor() {
        Grade firstGrade = new Grade("Midterm", 86.4F, 25.0F);
        Grade secondGrade = new Grade("Final", 86.4F, 25.0F);
        Grade thirdGrade = new Grade("midterm", 86.5F, 25.0F);
        Grade fourthGrade = new Grade("final", 86.4F, 25.0F);

        AtomicReference<Constructor<GradeList>> constructor = new AtomicReference<>();

        assertDoesNotThrow(() -> constructor.set(GradeList.class.getDeclaredConstructor(List.class)));
        constructor.get().setAccessible(true);

        Map<String, Grade> correctMap = Map.of("Midterm", firstGrade, "Final", secondGrade);
        Map<String, Grade> incorrectMap = Map.of("Midterm", firstGrade, "Final", secondGrade,
                                                 "midterm", thirdGrade,
                                                 "final", fourthGrade);

        List<Grade> correctList = Arrays.asList(firstGrade, secondGrade);
        List<Grade> incorrectList = Arrays.asList(firstGrade, secondGrade, thirdGrade, fourthGrade);

        assertDoesNotThrow(() -> new GradeList(correctMap), "Valid call to GradeList constructor should not throw.");
        assertThrows(IllegalStateException.class, () -> new GradeList(incorrectMap), "Invalid call to GradeList "
                + "constructor should throw");

        assertDoesNotThrow(() -> constructor.get().newInstance(correctList), "Valid call to Gradelist constructor "
                + "should not throw");
        assertThrows(IllegalStateException.class, () -> {
            try {
                constructor.get().newInstance(incorrectList);
            } catch (InvocationTargetException e) {
                throw e.getCause();
            }
        }, "Invalid call to GradeList constructor should throw");
    }
}
