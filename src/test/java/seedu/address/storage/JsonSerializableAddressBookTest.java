package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.task.Task;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    private static final Path TYPICAL_TASKS_FILE = TEST_DATA_FOLDER.resolve("typicalTasksAddressBook.json");
    private static final Path INVALID_TASK_FILE = TEST_DATA_FOLDER.resolve("invalidTaskAddressBook.json");
    private static final Path DUPLICATE_TASK_FILE = TEST_DATA_FOLDER.resolve("duplicateTaskAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_duplicatePersons_ignoresDuplicatePersons() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addPerson(new PersonBuilder(ALICE).build());
        expectedAddressBook.addPerson(new PersonBuilder(BENSON).build());
        assertEquals(expectedAddressBook.getPersonList(), addressBookFromFile.getPersonList());
    }

    @Test
    public void toModelType_typicalTasksFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_TASKS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addPerson(new PersonBuilder(ALICE).build());
        expectedAddressBook.addPerson(new PersonBuilder(BENSON).build());
        expectedAddressBook.addTask(new Task(new PersonBuilder(ALICE).build(),
                "Buy medication", true));
        expectedAddressBook.addTask(new Task(new PersonBuilder(BENSON).build(),
                "Visit doctor", false));

        assertEquals(expectedAddressBook, addressBookFromFile);
    }

    @Test
    public void toModelType_invalidTaskFile_ignoresInvalidTask() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_TASK_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addTask(new Task(new PersonBuilder(ALICE).build(), "Buy medication",
                true));
        assertEquals(expectedAddressBook.getTaskList(), addressBookFromFile.getTaskList());
    }

    @Test
    public void toModelType_duplicateTasks_ignoresDuplicateTasks() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TASK_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        AddressBook expectedAddressBook = new AddressBook();
        expectedAddressBook.addTask(new Task(new PersonBuilder(ALICE).build(), "Buy medication",
                true));
        expectedAddressBook.addTask(new Task(new PersonBuilder(ALICE).build(), "Visit doctor",
                false));
        assertEquals(expectedAddressBook.getTaskList(), addressBookFromFile.getTaskList());
    }
}
