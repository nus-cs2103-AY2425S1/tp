package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.student.TutorialClass;
import seedu.address.model.tut.TutName;
import seedu.address.model.tut.Tutorial;
import seedu.address.model.tut.TutorialList;

public class JsonSerializableTutorialListTest {

    private static final String VALID_NAME = "CS1010";
    private static final TutorialClass VALID_TUTORIAL_CLASS = TutorialClass.of("1001");

    @Test
    public void constructor_fromTutorialList_success() {
        TutorialList tutorialList = new TutorialList();
        tutorialList.addTutorial(Tutorial.of(new TutName(VALID_NAME), VALID_TUTORIAL_CLASS));

        JsonSerializableTutorialList jsonList = new JsonSerializableTutorialList(tutorialList);
        assertEquals(1, jsonList.getTutorials().size());
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

        assertEquals(0, jsonList.getTutorials().size());
    }
}
