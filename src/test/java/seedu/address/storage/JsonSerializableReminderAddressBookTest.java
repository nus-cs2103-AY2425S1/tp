package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReminderAddressBook;
import seedu.address.testutil.TypicalReminders;

public class JsonSerializableReminderAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
                                                           "data", "JsonSerializableReminderAddressBookTest");
    private static final Path TYPICAL_REMINDERS_FILE = TEST_DATA_FOLDER.resolve("typicalReminderAddressBook.json");
    private static final Path INVALID_REMINDER_FILE = TEST_DATA_FOLDER.resolve("invalidReminderAddressBook.json");
    private static final Path DUPLICATE_REMINDER_FILE = TEST_DATA_FOLDER.resolve("duplicateReminderAddressBook.json");

    @Test
    public void toModelType_typicalRemindersFile_success() throws Exception {
        JsonSerializableReminderAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_REMINDERS_FILE,
                JsonSerializableReminderAddressBook.class).get();
        ReminderAddressBook reminderAddressBookFromFile = dataFromFile.toModelType();
        ReminderAddressBook typicalRemindersAddressBook = TypicalReminders.getTypicalReminderAddressBook();
        assertEquals(reminderAddressBookFromFile, typicalRemindersAddressBook);
    }

    @Test
    public void toModelType_duplicateReminders_throwsIllegalValueException() throws Exception {
        JsonSerializableReminderAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_REMINDER_FILE,
                JsonSerializableReminderAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableReminderAddressBook.MESSAGE_DUPLICATE_REMINDER,
                dataFromFile::toModelType);
    }

}
