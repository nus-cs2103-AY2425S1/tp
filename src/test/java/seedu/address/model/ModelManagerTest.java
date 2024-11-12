package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EMPLOYEES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAssignments.ALICE_ALPHA;
import static seedu.address.testutil.TypicalAssignments.BENSON_BETA;
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
    public void setCommandTextHistoryFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCommandTextHistory(null));
    }

    @Test
    public void setCommandTextHistoryFilePath_validPath_setsCommandTextHistoryFilePath() {
        Path path = Paths.get("command/text/history/file/path");
        modelManager.setCommandTextHistoryFilePath(path);
        assertEquals(path, modelManager.getCommandTextHistoryFilePath());
    }

    @Test
    public void hasEmployee_nullEmployee_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEmployee(null));
    }

    @Test
    public void hasEmployee_employeeNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployee_employeeInAddressBook_returnsTrue() {
        modelManager.addEmployee(ALICE);
        assertTrue(modelManager.hasEmployee(ALICE));
    }

    @Test
    public void hasEmployeeId_employeeIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEmployeeId(ALICE.getEmployeeId()));
    }

    @Test
    public void hasEmployeeId_employeeIdInAddressBook_returnsFalse() {
        modelManager.addEmployee(ALICE);
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

    // EP: valid project id, addressbook has 1 assignment
    @Test
    public void deleteAllAssignment_assignmentWithProjectIdInAddressBookSingleAssignment_deleteAllSuccess() {
        modelManager.addAssignment(ALICE_ALPHA);
        assertTrue(modelManager.deleteAllAssignments(ALPHA.getId()));
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA));
    }

    // EP: valid project id, addressbook has more than 1 assignment
    @Test
    public void deleteAllAssignment_assignmentWithProjectIdInAddressBookMultipleAssignments_deleteAllSuccess() {
        modelManager.addAssignment(ALICE_ALPHA);
        modelManager.addAssignment(BENSON_BETA);
        assertTrue(modelManager.deleteAllAssignments(ALPHA.getId()));
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA));
    }

    // EP: invalid project id, addressbook is empty
    @Test
    public void deleteAllAssignment_assignmentWithProjectIdNotInEmptyAddressBook_noError() {
        assertFalse(modelManager.deleteAllAssignments(ALPHA.getId()));
    }

    // EP: invalid project id, addressbook is not empty
    @Test
    public void deleteAllAssignment_assignmentWithProjectIdNotInAddressBook_noError() {
        modelManager.addAssignment(BENSON_BETA);
        assertFalse(modelManager.deleteAllAssignments(ALPHA.getId()));
    }

    // EP: valid employee id, addressbook has 1 assignment
    @Test
    public void deleteAllAssignment_assignmentWithEmployeeIdInAddressBookSingleAssignment_deleteAllSuccess() {
        modelManager.addAssignment(ALICE_ALPHA);
        assertTrue(modelManager.deleteAllAssignments(ALICE.getEmployeeId()));
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA));
    }

    // EP: valid employee id, addressbook has more than 1 assignment
    @Test
    public void deleteAllAssignment_assignmentWithEmployeeIdInAddressBookMultipleAssignments_deleteAllSuccess() {
        modelManager.addAssignment(ALICE_ALPHA);
        modelManager.addAssignment(BENSON_BETA);
        assertTrue(modelManager.deleteAllAssignments(ALICE.getEmployeeId()));
        assertFalse(modelManager.hasAssignment(ALICE_ALPHA));
    }

    // EP: invalid employee id, addressbook is empty
    @Test
    public void deleteAllAssignment_assignmentWithEmployeeIdNotInEmptyAddressBook_noError() {
        assertFalse(modelManager.deleteAllAssignments(ALICE.getEmployeeId()));
    }

    // EP: invalid employee id, addressbook is not empty
    @Test
    public void deleteAllAssignment_assignmentWithEmployeeIdNotInAddressBook_noError() {
        modelManager.addAssignment(BENSON_BETA);
        assertFalse(modelManager.deleteAllAssignments(ALICE.getEmployeeId()));
    }

    @Test
    public void getFilteredEmployeeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEmployeeList().remove(0));
    }

    @Test
    public void getFilteredProjectList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredProjectList().remove(0));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withEmployee(ALICE).withEmployee(BENSON)
                .withProject(ALPHA).withProject(BETA)
                .build();
        AddressBook differentAddressBook = new AddressBook();
        CommandTextHistory commandTextHistory = new CommandTextHistory();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, commandTextHistory, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, commandTextHistory, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, commandTextHistory, userPrefs)));

        // different filteredEmployeeList -> returns false
        String[] keywords = ALICE.getName().value.split("\\s+");
        modelManager.updateFilteredEmployeeList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, commandTextHistory, userPrefs)));

        // different filteredProjectList -> returns false
        keywords = ALPHA.getName().value.split("\\s+");
        modelManager.updateFilteredProjectList(
                new ProjectNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, commandTextHistory, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEmployeeList(PREDICATE_SHOW_ALL_EMPLOYEES);
        modelManager.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, commandTextHistory, differentUserPrefs)));
    }
}
