package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.StudentBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    private Student student1;
    private Student student2;
    private Student student3;

    @BeforeEach
    public void setUp() {
        modelManager = new ModelManager();
        student1 = new StudentBuilder().withName("John Doe").withStudentNumber("A1234567L")
                .withTutorialGroup("T01").build();
        student2 = new StudentBuilder().withName("Jane Doe").withStudentNumber("A1234568M")
                .withTutorialGroup("T01").build();
        student3 = new StudentBuilder().withName("John Smith").withStudentNumber("A1234569N")
                .withTutorialGroup("T02").build();
        modelManager.addStudent(student1);
        modelManager.addStudent(student2);
        modelManager.addStudent(student3);
    }

    @Test
    public void constructor() {
        ModelManager modelManager = new ModelManager();
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void deleteStudent_validStudent_deletesStudent() {
        Student student = new StudentBuilder().withName("John Doe").build();
        modelManager.addStudent(student);
        modelManager.deleteStudent(student);
        assertFalse(modelManager.hasStudent(student));
    }

    @Test
    public void deleteStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteStudent(null));
    }

    @Test
    public void setStudent_replacesStudent_success() {
        ModelManager modelManager = new ModelManager();
        Student originalStudent = new StudentBuilder().withName("John Doe").withStudentNumber("A0191222D").build();
        Student editedStudent = new StudentBuilder().withName("John Doe").withStudentNumber("A0191222D")
                .withAttendanceRecord(LocalDate.of(2023, 10, 9), "p").build();

        modelManager.addStudent(originalStudent);
        modelManager.setStudent(originalStudent, editedStudent);

        assertEquals(editedStudent, modelManager.getStudentByName(new Name("John Doe")));
    }

    @Test
    public void addStudentAtIndex_validIndexAndStudent_addsStudent() {
        Student student = new StudentBuilder().withName("John Doe").build();
        modelManager.addStudent(0, student);
        assertEquals(student, modelManager.getFilteredStudentList().get(0));
    }

    @Test
    public void addStudentAtIndex_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.addStudent(0, null));
    }

    @Test
    public void addStudentAtIndex_invalidIndex_throwsIndexOutOfBoundsException() {
        Student student = new StudentBuilder().withName("John Doe").build();
        assertThrows(IndexOutOfBoundsException.class, () -> modelManager.addStudent(-1, student));
    }

    @Test
    public void replaceStudentList_validList_replacesStudentList() {
        ModelManager modelManager = new ModelManager();
        ObservableList<Student> newStudentList = FXCollections.observableArrayList();
        newStudentList.add(new StudentBuilder().withName("John Doe").withStudentNumber("A1234567M").build());
        newStudentList.add(new StudentBuilder().withName("Jane Doe").withStudentNumber("A1234568M").build());

        modelManager.replaceStudentList(newStudentList);

        assertEquals(newStudentList, modelManager.getFilteredStudentList());
    }

    @Test
    public void replaceStudentList_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.replaceStudentList(null));
    }

    @Test
    public void getStudentByName_existingStudent_returnsStudent() {
        assertEquals(student1, modelManager.getStudentByName(new Name("John Doe")));
        assertEquals(student2, modelManager.getStudentByName(new Name("Jane Doe")));
    }

    @Test
    public void getStudentByName_nonExistingStudent_returnsNull() {
        assertNull(modelManager.getStudentByName(new Name("Non Existing")));
    }

    @Test
    public void equalsMethod() {
        ModelManager modelManagerCopy = new ModelManager();
        modelManagerCopy.addStudent(student1);
        modelManagerCopy.addStudent(student2);
        modelManagerCopy.addStudent(student3);

        // Same object
        assertEquals(modelManager, modelManager);

        // Different objects, same values
        assertEquals(modelManager, modelManagerCopy);

        // Different types
        assertNotEquals(modelManager, new Object());

        // Null
        assertNotEquals(modelManager, null);
    }

    @Test
    public void getStudentsByTutorialGroup_existingGroup_returnsStudents() {
        List<Student> expectedStudents = List.of(student1, student2);
        assertEquals(expectedStudents, modelManager.getStudentsByTutorialGroup(new TutorialGroup("T01")));
    }

    @Test
    public void getStudentsByTutorialGroup_nonExistingGroup_returnsEmptyList() {
        List<Student> expectedStudents = List.of();
        assertEquals(expectedStudents, modelManager.getStudentsByTutorialGroup(new TutorialGroup("L21")));
    }

    @Test
    public void getStudentsByTutorialGroup_nonExistingGroup_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                modelManager.getStudentsByTutorialGroup(new TutorialGroup("Invalid")));
    }

    @Test
    public void getAllStudentsByName_existingName_returnsStudents() {
        ObservableList<Student> expectedStudents = FXCollections.observableArrayList(student1);
        assertEquals(expectedStudents, modelManager.getAllStudentsByName(new Name("John Doe")));
    }

    @Test
    public void getAllStudentsByName_nonExistingName_returnsEmptyList() {
        ObservableList<Student> expectedStudents = FXCollections.observableArrayList();
        assertEquals(expectedStudents, modelManager.getAllStudentsByName(new Name("Non Existing")));
    }


}
