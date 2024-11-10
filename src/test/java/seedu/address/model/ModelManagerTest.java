package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
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


}
