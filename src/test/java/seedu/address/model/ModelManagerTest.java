package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.BENSON;
import static seedu.address.testutil.TypicalProjects.ALPHA;
import static seedu.address.testutil.TypicalProjects.BETA;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentId;
import seedu.address.model.assignment.exceptions.AssignmentNotFoundException;
import seedu.address.model.employee.NameContainsKeywordsPredicate;
import seedu.address.model.project.ProjectNameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

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
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasEmployeeId_employeeIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEmployeeId(ALICE.getEmployeeId()));
    }

    @Test
    public void hasEmployeeId_employeeIdInAddressBook_returnsFalse() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasEmployeeId(ALICE.getEmployeeId()));
    }

    @Test
    public void hasProject_nullProject_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProject(null));
    }

    @Test
    public void hasProject_projectNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasProject(ALPHA));
    }

    @Test
    public void hasProject_projectInAddressBook_returnsTrue() {
        modelManager.addProject(ALPHA);
        assertTrue(modelManager.hasProject(ALPHA));
    }

    @Test
    public void hasProjectId_projectIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasProjectId(ALPHA.getId()));
    }

    @Test
    public void hasProjectId_projectIdInAddressBook_returnsFalse() {
        modelManager.addProject(ALPHA);
        assertTrue(modelManager.hasProjectId(ALPHA.getId()));
    }

    @Test
    public void hasAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAssignment((Assignment) null));
    }

    @Test
    public void hasAssignment_nullAssignmentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAssignment((AssignmentId) null));
    }

    @Test
    public void hasAssignment_nullProjectIdAndEmployeeId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAssignment(null, null));
    }

    @Test
    public void hasAssignment_assignmentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA));
    }

    @Test
    public void hasAssignment_assignmentInAddressBook_returnsTrue() {
        modelManager.addAssignment(ALICE_ALPHA);
        assertTrue(modelManager.hasAssignment(ALICE_ALPHA));
    }

    @Test
    public void hasAssignment_assignmentIdInAddressBook_returnsTrue() {
        modelManager.addAssignment(ALICE_ALPHA);
        assertTrue(modelManager.hasAssignment(ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void hasAssignment_assignmentIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void hasAssignment_projectIdAndEmployeeIdInAddressBook_returnsTrue() {
        modelManager.addAssignment(ALICE_ALPHA);
        assertTrue(modelManager.hasAssignment(ALPHA.getId(), ALICE.getEmployeeId()));
    }

    @Test
    public void hasAssignment_projectIdAndEmployeeIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAssignment(ALPHA.getId(), ALICE.getEmployeeId()));
    }

    @Test
    public void deleteAssignment_nullAssignment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteAssignment((Assignment) null));
    }

    @Test
    public void deleteAssignment_nullAssignmentId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteAssignment((AssignmentId) null));
    }

    @Test
    public void deleteAssignment_nullProjectIdAndEmployeeId_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.deleteAssignment(null, null));
    }

    @Test
    public void deleteAssignment_assignmentNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () -> modelManager.deleteAssignment(ALICE_ALPHA));
    }

    @Test
    public void deleteAssignment_assignmentIdNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () ->
                modelManager.deleteAssignment(ALICE_ALPHA.getAssignmentId()));
    }

    @Test
    public void deleteAssignment_projectIdAndEmployeeIdNotInAddressBook_throwsAssignmentNotFoundException() {
        assertThrows(AssignmentNotFoundException.class, () ->
                modelManager.deleteAssignment(ALPHA.getId(), ALICE.getEmployeeId()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }
    @Test
    public void getFilteredProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredProjectList().remove(0));
    }


    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withPerson(ALICE).withPerson(BENSON)
                .withProject(ALPHA).withProject(BETA)
                .build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredProjectList -> returns false
        keywords = ALPHA.getName().fullName.split("\\s+");
        modelManager.updateFilteredProjectList(
                new ProjectNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        modelManager.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
