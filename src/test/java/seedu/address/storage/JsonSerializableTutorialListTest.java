package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTutorials.TUTORIAL1;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.student.TutorialId;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

public class JsonSerializableTutorialListTest {

    private static final String VALID_NAME = "CS1010";
    private static final TutorialId VALID_TUTORIAL_ID = TutorialId.of("T1001");

    private static final List<JsonAdaptedTutorial> jsonAdaptedTutorials = new ArrayList<>();

    private static final JsonSerializableTutorialList jsonSerializableTutorialList =
            new JsonSerializableTutorialList(jsonAdaptedTutorials);


    private static final TutorialList tutorialList = new TutorialList();


    @Test
    public void constructor_fromTutorialList_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(Tutorial.of(new TutName(VALID_NAME), VALID_TUTORIAL_ID));

        JsonSerializableTutorialList jsonList = new JsonSerializableTutorialList(tutorialList);
        assertEquals(2, jsonList.getTutorials().size());
    }

    @Test
    public void constructor_nullTutorialList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new JsonSerializableTutorialList((List<JsonAdaptedTutorial>) null));
    }

    @Test
    public void constructor_fromEmptyTutorialList_success() {
        TutorialList emptyList = new TutorialList();
        JsonSerializableTutorialList jsonList = new JsonSerializableTutorialList(emptyList);
        //Empty tutorial class will always exist
        assertEquals(1, jsonList.getTutorials().size());
    }

    @Test
    void testToModelType() throws IllegalValueException {
        JsonSerializableTutorialList jsonTutorialList = new JsonSerializableTutorialList(jsonAdaptedTutorials);

        TutorialList resultTutorialList = jsonTutorialList.toModelType();

        assertEquals(1, resultTutorialList.getTutorials().size());
        assertEquals(tutorialList.getTutorials().get(0).getTutName(),
                resultTutorialList.getTutorials().get(0).getTutName());
    }

    @Test
    void testToModelType_withNoneTutorial() throws IllegalValueException {
        JsonAdaptedTutorial noneAdaptedTutorial = new JsonAdaptedTutorial(Tutorial.none());
        jsonAdaptedTutorials.add(noneAdaptedTutorial);

        JsonSerializableTutorialList jsonTutorialList = new JsonSerializableTutorialList(jsonAdaptedTutorials);

        TutorialList resultTutorialList = jsonTutorialList.toModelType();

        assertEquals(1, resultTutorialList.getTutorials().size());
    }

    @Test
    public void toModelType_withNoneTutorial_skipped() throws IllegalValueException {
        List<Tutorial> tutorials = new ArrayList<>();
        Tutorial validTutorial = TUTORIAL1;
        Tutorial noneTutorial = Tutorial.none();

        tutorials.add(validTutorial);
        tutorials.add(noneTutorial);

        List<JsonAdaptedTutorial> jsonAdaptedTutorials = new ArrayList<>();
        for (Tutorial tutorial : tutorials) {
            jsonAdaptedTutorials.add(new JsonAdaptedTutorial(tutorial));
        }

        JsonSerializableTutorialList jsonTutorialList = new JsonSerializableTutorialList(jsonAdaptedTutorials);
        TutorialList modelTutorialList = jsonTutorialList.toModelType();

        assertEquals(2, modelTutorialList.getTutorials().size());
        assertEquals(Tutorial.none(), modelTutorialList.getTutorials().get(0));
    }

    @Test
    void testGetTutorials() {
        List<JsonAdaptedTutorial> result = jsonSerializableTutorialList.getTutorials();

        assertEquals(jsonAdaptedTutorials, result);
    }
}
