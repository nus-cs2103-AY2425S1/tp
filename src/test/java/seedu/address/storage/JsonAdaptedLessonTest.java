package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.StudentId;
import seedu.address.model.person.Person;
import seedu.address.testutil.LessonBuilder;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedLessonTest {
    private static final String INVALID_STUDENT = "R@chel";
    private static final String INVALID_LOCATION_INDEX = "0";
    private static final String INVALID_START_DATE_TIME = "00-00-00 25:60";
    private static final String INVALID_END_DATE_TIME = "00-00-00 25:60";
    private final ReadOnlyAddressBook addressBook = getTypicalAddressBook();

    private final Lesson validLesson = new LessonBuilder().withName(addressBook.getPersonList().get(0)).build();
    private final String validStudent = validLesson.getStudent().toString();
    private final String validLocationIndex = validLesson.getLocationIndex().toString();
    private final String validStartDateTime = validLesson.getStartDateTime().toString();
    private final String validEndDateTime = validLesson.getEndDateTime().toString();

    public JsonAdaptedLessonTest() throws ParseException {
    }

    @Test
    public void toModelType_validLessonsDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(validLesson);
        assertEquals(validLesson, lesson.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidStudent_throwsIllegalValueException() throws ParseException {
        String nameNotExist = "JohnDoesNotExist";
        Person person = new PersonBuilder().withName(nameNotExist).build();
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(new LessonBuilder().withName(person).build());
        String expectedMessage = StudentId.INVALID_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(addressBook));
    }
}
