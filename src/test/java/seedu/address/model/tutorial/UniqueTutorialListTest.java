package seedu.address.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.MATH;
import static seedu.address.testutil.TypicalPersons.SCIENCE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.tutorial.exceptions.DuplicateTutorialException;
import seedu.address.model.tutorial.exceptions.TutorialNotFoundException;
import seedu.address.testutil.TutorialBuilder;

public class UniqueTutorialListTest {
    private final UniqueTutorialList uniqueTutorialList = new UniqueTutorialList();

    @Test
    public void contains_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.contains(null));
    }

    @Test
    public void contains_tutorialNotInList_returnsFalse() {
        assertFalse(uniqueTutorialList.contains(MATH));
    }

    @Test
    public void contains_tutorialInList_returnsTrue() {
        uniqueTutorialList.add(MATH);
        assertTrue(uniqueTutorialList.contains(MATH));
    }

    @Test
    public void contains_tutorialWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTutorialList.add(MATH);
        Tutorial editedMath = new TutorialBuilder(MATH).build();
        assertTrue(uniqueTutorialList.contains(editedMath));
    }

    @Test
    public void add_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.add(null));
    }

    @Test
    public void add_duplicateTutorial_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(MATH);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.add(MATH));
    }

    @Test
    public void setTutorial_nullTargetTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(null, MATH));
    }

    @Test
    public void setTutorial_nullEditedTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorial(MATH, null));
    }

    @Test
    public void setTutorial_targetTutorialNotInList_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.setTutorial(MATH, MATH));
    }


    @Test
    public void setTutorial_editedTutorialIsSameTutorial_success() {
        uniqueTutorialList.add(MATH);
        uniqueTutorialList.setTutorial(MATH, MATH);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(MATH);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasSameIdentity_success() {
        uniqueTutorialList.add(MATH);
        Tutorial editedMath = new TutorialBuilder(MATH).build();
        uniqueTutorialList.setTutorial(MATH, editedMath);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(editedMath);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasDifferentIdentity_success() {
        uniqueTutorialList.add(MATH);
        uniqueTutorialList.setTutorial(MATH, SCIENCE);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(SCIENCE);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorial_editedTutorialHasNonUniqueIdentity_throwsDuplicateTutorialException() {
        uniqueTutorialList.add(MATH);
        uniqueTutorialList.add(SCIENCE);
        assertThrows(DuplicateTutorialException.class, () -> uniqueTutorialList.setTutorial(MATH, SCIENCE));
    }

    @Test
    public void remove_nullTutorial_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.remove(null));
    }

    @Test
    public void remove_tutorialDoesNotExist_throwsTutorialNotFoundException() {
        assertThrows(TutorialNotFoundException.class, () -> uniqueTutorialList.remove(MATH));
    }

    @Test
    public void remove_existingTutorial_removesTutorial() {
        uniqueTutorialList.add(MATH);
        uniqueTutorialList.remove(MATH);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullUniqueTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((UniqueTutorialList) null));
    }

    @Test
    public void setTutorials_uniqueTutorialList_replacesOwnListWithProvidedUniqueTutorialList() {
        uniqueTutorialList.add(MATH);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(SCIENCE);
        uniqueTutorialList.setTutorials(expectedUniqueTutorialList);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTutorialList.setTutorials((List<Tutorial>) null));
    }

    @Test
    public void setTutorials_list_replacesOwnListWithProvidedList() {
        uniqueTutorialList.add(MATH);
        List<Tutorial> tutorialList = Collections.singletonList(SCIENCE);
        uniqueTutorialList.setTutorials(tutorialList);
        UniqueTutorialList expectedUniqueTutorialList = new UniqueTutorialList();
        expectedUniqueTutorialList.add(SCIENCE);
        assertEquals(expectedUniqueTutorialList, uniqueTutorialList);
    }

    @Test
    public void setTutorials_listWithDuplicateTutorials_throwsDuplicateTutorialException() {
        List<Tutorial> listWithDuplicateTutorials = Arrays.asList(MATH, MATH);
        assertThrows(DuplicateTutorialException.class, ()
                -> uniqueTutorialList.setTutorials(listWithDuplicateTutorials));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueTutorialList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueTutorialList.asUnmodifiableObservableList().toString(), uniqueTutorialList.toString());
    }

    @Test
    public void equals() {
        UniqueTutorialList uniqueTutorialList2 = new UniqueTutorialList();

        // same values -> returns true
        assertTrue(uniqueTutorialList.equals(uniqueTutorialList2));

        // same object -> returns true
        assertTrue(uniqueTutorialList.equals(uniqueTutorialList));

        // null -> returns false
        assertFalse(uniqueTutorialList.equals(null));

        // different type -> returns false
        assertFalse(uniqueTutorialList.equals(5.0f));

        //different values -> returns false
        uniqueTutorialList2.add(MATH);
        assertFalse(uniqueTutorialList.equals(uniqueTutorialList2));
    }
}
