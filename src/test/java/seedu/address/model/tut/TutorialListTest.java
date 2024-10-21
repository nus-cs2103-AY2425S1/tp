package seedu.address.model.tut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TutUtil.TUTORIAL_CLASS;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL2;
import static seedu.address.testutil.TypicalTutorials.getTypicalTutorialList;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.exceptions.DuplicateTutorialException;
import seedu.address.model.tut.exceptions.TutNoFoundException;


public class TutorialListTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TutorialList(null));
    }

    @Test
    public void constructor_constructTutorialListSuccess() {
        TutorialClass tutorialClass1 = new TutorialClass("1000");
        TutorialClass tutorialClass2 = new TutorialClass("2000");
        Tutorial tutorial1 = Tutorial.of(new TutName("Tut"), tutorialClass1);
        Tutorial tutorial2 = Tutorial.of(new TutName("Tut1"), tutorialClass2);
        ArrayList<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(tutorial1);
        tutorials.add(tutorial2);
        TutorialList tutorialList = new TutorialList(tutorials);
        assertTrue(tutorialList.getTutorials().contains(tutorial1));
        assertTrue(tutorialList.getTutorials().contains(tutorial2));
    }


    @Test
    public void getTutorialsTest() {
        ArrayList<Tutorial> tutorials = new ArrayList<>();
        tutorials.add(TUTORIAL1);
        TutorialList tutorialList = new TutorialList(tutorials);
        assertEquals(tutorialList.getTutorials(), tutorials);
    }

    @Test
    public void addTutorialTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL1);
        assertTrue(tutorialList.getTutorials().contains(TUTORIAL1));
    }

    @Test
    public void addTutorialDuplicateTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL1);
        assertThrows(DuplicateTutorialException.class, () -> tutorialList.addTutorial(TUTORIAL1));
    }

    @Test
    public void deleteTutorialTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL1);
        tutorialList.addTutorial(TUTORIAL2);
        tutorialList.deleteTutorial(TUTORIAL1);
        assertTrue(tutorialList.getTutorials().contains(TUTORIAL2));
        assertFalse(tutorialList.getTutorials().contains(TUTORIAL1));
    }

    @Test
    public void deleteTutorialTest_notExist_success() {
        TutorialList tutorialList = new TutorialList();
        assertThrows(TutNoFoundException.class, () -> tutorialList.deleteTutorial(TUTORIAL1));
    }
    @Test
    public void deleteStudentTest() {
        TutorialList tutorialList = new TutorialList();
        TUTORIAL1.add(ALICE);
        tutorialList.addTutorial(TUTORIAL1);
        assertTrue(tutorialList.getTutorials().get(0).studentInList(ALICE));
        tutorialList.deleteStudent(ALICE);
        assertFalse(tutorialList.getTutorials().get(0).studentInList(ALICE));
    }

    @Test
    public void hasTutorialTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL1);
        assertTrue(tutorialList.hasTutorial(TUTORIAL1));
        assertFalse(tutorialList.hasTutorial(TUTORIAL2));
    }

    @Test
    public void hasTutorialClassTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL2);
        assertTrue(tutorialList.hasTutorial(TUTORIAL_CLASS));
    }


    @Test
    public void assignTutorialTest_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(TUTORIAL2);
        tutorialList.assignStudent(ALICE, TUTORIAL_CLASS);
        assertTrue(tutorialList.getTutorials().get(0).getStudents().contains(ALICE));
    }

    @Test
    public void equals() {
        TutorialList tutorialList = new TutorialList();
        TutorialList tutorialList1 = getTypicalTutorialList();

        assertTrue(tutorialList.equals(tutorialList));

        TutorialList tutorialListCopy = new TutorialList();
        assertTrue(tutorialList.equals(tutorialListCopy));

        assertFalse(tutorialList.equals(1));

        assertFalse(tutorialList.equals(null));

        assertFalse(tutorialList.equals(tutorialList1));
    }
}
