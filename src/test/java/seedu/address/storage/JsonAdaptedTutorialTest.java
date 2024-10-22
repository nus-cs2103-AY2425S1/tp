package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TutUtil.TUTORIAL_ID;
import static seedu.address.testutil.TutUtil.TUTORIAL_SAMPLE;
import static seedu.address.testutil.TutUtil.TUT_DATE;
import static seedu.address.testutil.TutUtil.TUT_NAME;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;

/**
 * Test class for JsonAdaptedTut.
 */
public class JsonAdaptedTutorialTest {

    private static final String INVALID_TUT_NAME = "";
    private static final String VALID_TUT_NAME = TUTORIAL_SAMPLE.getTutName().tutName;
    private static final String VALID_TUTORIAL_CLASS = TUTORIAL_SAMPLE.getTutorialId().toString();

    private static final JsonAdaptedStudent VALID_STUDENT = new JsonAdaptedStudent(ALICE);
    private static final JsonAdaptedTutDate VALID_TUT_DATE = new JsonAdaptedTutDate(TUT_DATE);

    @Test
    public void toModelType_validTutDetails_returnsTut() throws Exception {
        // Using TUT_SAMPLE from TutUtil
        Tutorial tutorial = Tutorial.of(new TutName(TUT_NAME), TUTORIAL_ID);
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(tutorial);
        assertEquals(tutorial, tut.toModelType());
    }

    @Test
    public void toModelType_invalidTutName_throwsIllegalValueException() {
        // Creating a JsonAdaptedTut with invalid tutorial name
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                INVALID_TUT_NAME, TUTORIAL_SAMPLE.getTutorialId().toString(),
                List.of(VALID_STUDENT),
                List.of(VALID_TUT_DATE)
        );
        String expectedMessage = Tutorial.MESSAGE_NAME_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, tut::toModelType);
    }

    @Test
    public void toModelType_nullTutName_throwsIllegalValueException() {
        // Creating a JsonAdaptedTut with null tutorial name
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                null, TUTORIAL_SAMPLE.getTutorialId().toString(),
                List.of(VALID_STUDENT),
                List.of(VALID_TUT_DATE)
        );
        String expectedMessage = String.format(JsonAdaptedTutorial.MISSING_FIELD_MESSAGE_FORMAT, "tutName");
        assertThrows(IllegalValueException.class, expectedMessage, tut::toModelType);
    }

    @Test
    public void toModelType_invalidTutorialClass_throwsIllegalValueException() {
        // Creating a JsonAdaptedTut with invalid tutorial class
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                VALID_TUT_NAME, null,
                List.of(VALID_STUDENT),
                List.of(VALID_TUT_DATE)
        );
        String expectedMessage = String.format(JsonAdaptedTutorial.MISSING_FIELD_MESSAGE_FORMAT, "tutorialId");
        assertThrows(IllegalValueException.class, expectedMessage, tut::toModelType);
    }

    @Test
    public void toModelType_missingStudentsList_noException() throws Exception {
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                VALID_TUT_NAME, VALID_TUTORIAL_CLASS,
                null,
                List.of(VALID_TUT_DATE)
        );
        assertNotNull(tut.toModelType());
    }

    @Test
    public void toModelType_missingTutDatesList_noException() throws Exception {
        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                VALID_TUT_NAME, VALID_TUTORIAL_CLASS,
                List.of(VALID_STUDENT),
                null
        );
        assertNotNull(tut.toModelType());
    }

    @Test
    public void toModelType_invalidTutDate_throwsIllegalValueException() {
        JsonAdaptedTutDate invalidTutDate = new JsonAdaptedTutDate("", List.of("studentId"));

        JsonAdaptedTutorial tut = new JsonAdaptedTutorial(
                VALID_TUT_NAME, VALID_TUTORIAL_CLASS,
                List.of(VALID_STUDENT),
                List.of(invalidTutDate)
        );

        assertThrows(IllegalValueException.class, tut::toModelType);
    }

    @Test
    public void toModelType_allValidFields_returnsTutorial() throws Exception {
        JsonAdaptedTutorial jsonTut = new JsonAdaptedTutorial(
                VALID_TUT_NAME, VALID_TUTORIAL_CLASS,
                List.of(VALID_STUDENT), List.of(VALID_TUT_DATE)
        );

        Tutorial tutorial = jsonTut.toModelType();
        assertEquals(VALID_TUT_NAME, tutorial.getTutName().tutName);
        assertEquals(VALID_TUTORIAL_CLASS, tutorial.getTutorialId().toString());
        assertEquals(1, tutorial.getStudents().size());
    }






}
