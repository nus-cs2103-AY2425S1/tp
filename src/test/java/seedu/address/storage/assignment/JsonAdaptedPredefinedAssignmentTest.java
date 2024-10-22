package seedu.address.storage.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.assignment.PredefinedAssignment;

class JsonAdaptedPredefinedAssignmentTest {
    public static final String TEST_STRING = "Ex20";
    public static final Float TEST_MAX_SCORE = 0f;
    private final JsonAdaptedPredefinedAssignment testData =
            new JsonAdaptedPredefinedAssignment(TEST_STRING, TEST_MAX_SCORE);
    private final PredefinedAssignment expectedData = new PredefinedAssignment(TEST_STRING, TEST_MAX_SCORE);

    @Test
    void toModelTypeConvertsCorrectly() {
        assertEquals(testData.toModelType(), expectedData);
    }

    @Test
    void getNameTest() {
        assertEquals(testData.getName(), TEST_STRING);
    }
}
