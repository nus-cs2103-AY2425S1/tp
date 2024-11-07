package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Subject;
import seedu.address.model.person.Tutee;
import seedu.address.model.person.Tutor;

public class JsonAdaptedLessonTest {
    private static final Tutor VALID_TUTOR = BENSON;
    private static final Tutee VALID_TUTEE = DANIEL;
    private static final Subject VALID_SUBJECT = new Subject("Math");
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_TUTOR.getId(), VALID_TUTEE.getId(),
                new JsonAdaptedSubject(VALID_SUBJECT));
        assertEquals(new Lesson(VALID_TUTOR, VALID_TUTEE, VALID_SUBJECT), lesson.toModelType(addressBookFromFile));
    }

    @Test
    public void toModelType_invalidTutorId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(5, VALID_TUTEE.getId(), new JsonAdaptedSubject(VALID_SUBJECT));
        String expectedMessage = JsonAdaptedLesson.INVALID_TUTOR_ID;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(addressBookFromFile));
    }

    @Test
    public void toModelType_invalidTuteeId_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_TUTOR.getId(), 0, new JsonAdaptedSubject(VALID_SUBJECT));
        String expectedMessage = JsonAdaptedLesson.INVALID_TUTEE_ID;
        assertThrows(IllegalValueException.class, expectedMessage, () -> lesson.toModelType(addressBookFromFile));
    }
}
