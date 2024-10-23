package tahub.contacts.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import tahub.contacts.commons.exceptions.IllegalValueException;
import tahub.contacts.model.grade.GradingSystem;

/**
 * Unit tests for {@link JsonSerializableGradingSystem}.
 * These tests ensure that the JsonSerializableGradingSystem class correctly serializes and deserializes
 * GradingSystem objects.
 */
public class JsonSerializableGradingSystemTest {

    private static final JsonAdaptedGrade VALID_GRADE = new JsonAdaptedGrade("Midterm",
                                                                             85.0, 0.4);
    private static final JsonAdaptedGrade VALID_GRADE_2 = new JsonAdaptedGrade("Final",
                                                                               90.0, 0.6);

    /**
     * Tests if a valid GradingSystem can be correctly converted to a model type and back.
     * @throws Exception if there's an unexpected error during the conversion process.
     */
    @Test
    public void toModelType_validGradingSystem_returnsGradingSystem() throws Exception {
        List<JsonAdaptedGrade> grades = new ArrayList<>();
        grades.add(VALID_GRADE);
        grades.add(VALID_GRADE_2);
        JsonSerializableGradingSystem gradingSystem = new JsonSerializableGradingSystem(grades);

        GradingSystem modelGradingSystem = gradingSystem.toModelType();
        assertEquals(85.0, modelGradingSystem.getGrade("Midterm"));
        assertEquals(90.0, modelGradingSystem.getGrade("Final"));
        assertEquals(0.4, modelGradingSystem.getAllWeights().get("Midterm"));
        assertEquals(0.6, modelGradingSystem.getAllWeights().get("Final"));
    }

    /**
     * Tests if an invalid grade (with an empty assessment name) throws an IllegalValueException.
     */
    @Test
    public void toModelType_invalidGrade_throwsIllegalValueException() {
        List<JsonAdaptedGrade> grades = new ArrayList<>();
        grades.add(new JsonAdaptedGrade("", 85.0, 0.4)); // Invalid assessment name
        JsonSerializableGradingSystem gradingSystem = new JsonSerializableGradingSystem(grades);
        assertThrows(IllegalValueException.class, gradingSystem::toModelType);
    }

    /**
     * Tests if duplicate grades are handled correctly when converting to model type.
     * @throws Exception if there's an unexpected error during the conversion process.
     */
    @Test
    public void toModelType_duplicateGrades_replacesOldGrade() throws Exception {
        List<JsonAdaptedGrade> grades = new ArrayList<>();
        grades.add(VALID_GRADE);
        grades.add(new JsonAdaptedGrade("Midterm", 90.0, 0.5));
        JsonSerializableGradingSystem gradingSystem = new JsonSerializableGradingSystem(grades);

        GradingSystem modelGradingSystem = gradingSystem.toModelType();
        assertEquals(90.0, modelGradingSystem.getGrade("Midterm"));
        assertEquals(0.5, modelGradingSystem.getAllWeights().get("Midterm"));
    }

    /**
     * Tests if the conversion from GradingSystem to JsonSerializableGradingSystem works correctly.
     */
    @Test
    public void constructor_validGradingSystem_createsCorrectJsonSerializableGradingSystem() {
        GradingSystem gradingSystem = new GradingSystem();
        gradingSystem.addGrade("Midterm", 85.0);
        gradingSystem.setAssessmentWeight("Midterm", 0.4);
        gradingSystem.addGrade("Final", 90.0);
        gradingSystem.setAssessmentWeight("Final", 0.6);

        JsonSerializableGradingSystem jsonGradingSystem = new JsonSerializableGradingSystem(gradingSystem);

        try {
            GradingSystem convertedGradingSystem = jsonGradingSystem.toModelType();
            assertEquals(gradingSystem.getAllGrades(), convertedGradingSystem.getAllGrades());
            assertEquals(gradingSystem.getAllWeights(), convertedGradingSystem.getAllWeights());
        } catch (IllegalValueException e) {
            fail("Converting a valid GradingSystem should not throw an exception");
        }
    }
}
