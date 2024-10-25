package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.testutil.TypicalAssignments;
import seedu.address.testutil.TypicalEmployees;
import seedu.address.testutil.TypicalProjects;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_EMPLOYEES_FILE = TEST_DATA_FOLDER.resolve("typicalEmployeesAddressBook.json");
    private static final Path INVALID_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("invalidEmployeeAddressBook.json");
    private static final Path DUPLICATE_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("duplicateEmployeeAddressBook.json");

    private static final Path TYPICAL_PROJECTS_FILE = TEST_DATA_FOLDER.resolve("typicalProjectsAddressBook.json");
    private static final Path INVALID_PROJECT_FILE = TEST_DATA_FOLDER.resolve("invalidProjectAddressBook.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectAddressBook.json");

    private static final Path TYPICAL_ASSIGNMENTS_FILE = TEST_DATA_FOLDER.resolve("typicalAssignmentsAddressBook.json");
    private static final Path INVALID_ASSIGNMENT_FILE = TEST_DATA_FOLDER.resolve("invalidAssignmentAddressBook.json");
    private static final Path DUPLICATE_ASSIGNMENT_FILE = TEST_DATA_FOLDER
            .resolve("duplicateAssignmentAddressBook.json");

    @Test
    public void toModelType_typicalEmployeesFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EMPLOYEES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalEmployeesAddressBook = TypicalEmployees.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalEmployeesAddressBook);
    }

    @Test
    public void toModelType_invalidEmployeeFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_EMPLOYEE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateEmployees_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EMPLOYEE_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_EMPLOYEE,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalProjectsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PROJECTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalProjectsAddressBook = TypicalProjects.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalProjectsAddressBook);
    }

    @Test
    public void toModelType_invalidProjectFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PROJECT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateProjects_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PROJECT,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_typicalAssignmentsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_ASSIGNMENTS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalAssignmentsAddressBook = TypicalAssignments.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalAssignmentsAddressBook);
    }

    @Test
    public void toModelType_invalidAssignmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_ASSIGNMENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateAssignments_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_ASSIGNMENT_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_ASSIGNMENT,
                dataFromFile::toModelType);
    }
}
