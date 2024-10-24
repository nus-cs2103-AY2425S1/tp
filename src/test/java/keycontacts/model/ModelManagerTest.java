package keycontacts.model;

import static keycontacts.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static keycontacts.testutil.Assert.assertThrows;
import static keycontacts.testutil.TypicalStudents.ALICE;
import static keycontacts.testutil.TypicalStudents.BENSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import keycontacts.commons.core.GuiSettings;
import keycontacts.logic.commands.FindCommand.FindStudentDescriptor;
import keycontacts.model.student.StudentDescriptorMatchesPredicate;
import keycontacts.testutil.FindStudentDescriptorBuilder;
import keycontacts.testutil.StudentDirectoryBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new StudentDirectory(), new StudentDirectory(modelManager.getStudentDirectory()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setStudentDirectoryFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setStudentDirectoryFilePath(Paths.get("new/address/book/file/path"));
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
    public void setStudentDirectoryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setStudentDirectoryFilePath(null));
    }

    @Test
    public void setStudentDirectoryFilePath_validPath_setsStudentDirectoryFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setStudentDirectoryFilePath(path);
        assertEquals(path, modelManager.getStudentDirectoryFilePath());
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInStudentDirectory_returnsFalse() {
        assertFalse(modelManager.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInStudentDirectory_returnsTrue() {
        modelManager.addStudent(ALICE);
        assertTrue(modelManager.hasStudent(ALICE));
    }

    @Test
    public void getFilteredStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredStudentList().remove(0));
    }

    @Test
    public void equals() {
        StudentDirectory studentDirectory = new StudentDirectoryBuilder().withStudent(ALICE).withStudent(BENSON)
                .build();
        StudentDirectory differentStudentDirectory = new StudentDirectory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(studentDirectory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(studentDirectory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different studentDirectory -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentStudentDirectory, userPrefs)));

        // different filteredList -> returns false
        FindStudentDescriptor findStudentDescriptor = new FindStudentDescriptorBuilder()
                .withName(ALICE.getName().fullName).build();
        modelManager.updateFilteredStudentList(new StudentDescriptorMatchesPredicate(findStudentDescriptor));
        assertFalse(modelManager.equals(new ModelManager(studentDirectory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setStudentDirectoryFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(studentDirectory, differentUserPrefs)));
    }
}
