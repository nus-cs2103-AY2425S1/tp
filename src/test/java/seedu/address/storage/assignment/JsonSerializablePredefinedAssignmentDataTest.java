package seedu.address.storage.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;

class JsonSerializablePredefinedAssignmentDataTest {

    @Test
    void toModelTypeDetectsDuplicate() {
        List<JsonAdaptedPredefinedAssignment> testData = new ArrayList<>();
        testData.add(new JsonAdaptedPredefinedAssignment("Ex01", 0f));
        testData.add(new JsonAdaptedPredefinedAssignment("Ex01", 0f));
        JsonSerializablePredefinedAssignmentData objectTest = new JsonSerializablePredefinedAssignmentData(testData);
        assertThrows(IllegalValueException.class, objectTest::toModelType);
    }

    @Test
    void returnsTrueForEqualAssignmentList() {
        List<JsonAdaptedPredefinedAssignment> listOne = new ArrayList<>();
        List<JsonAdaptedPredefinedAssignment> listTwo = new ArrayList<>();
        JsonAdaptedPredefinedAssignment assignmentOne = new JsonAdaptedPredefinedAssignment("Ex01", 0f);
        JsonAdaptedPredefinedAssignment assignmentTwo = new JsonAdaptedPredefinedAssignment("Ex02", 0f);
        listOne.add(assignmentOne);
        listOne.add(assignmentTwo);
        listTwo.add(assignmentOne);
        listTwo.add(assignmentTwo);
        assertEquals(listOne, listTwo);
    }
}
