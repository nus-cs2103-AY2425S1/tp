package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TaskBuilder;

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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasTask_taskNotInAddressBook_returnsFalse() {
        Task task = new TaskBuilder().withDescription("Buy medication").build();
        assertFalse(modelManager.hasTask(task));
    }

    @Test
    public void hasTask_taskInAddressBook_returnsTrue() {
        Task task = new TaskBuilder().withDescription("Buy medication").build();
        modelManager.addTask(task);
        assertTrue(modelManager.hasTask(task));
    }

    @Test
    public void getFilteredTaskList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTaskList().remove(0));
    }

    @Test
    public void deleteAssociatedTasks_personWithTasks_tasksDeleted() {
        Task task1 = new TaskBuilder().withDescription("Task 1").withPatient(ALICE).build();
        Task task2 = new TaskBuilder().withDescription("Task 2").withPatient(ALICE).build();

        // Add the tasks to the model
        modelManager.addTask(task1);
        modelManager.addTask(task2);

        assertTrue(modelManager.hasTask(task1));
        assertTrue(modelManager.hasTask(task2));

        modelManager.deleteAssociatedTasks(ALICE);

        assertFalse(modelManager.hasTask(task1));
        assertFalse(modelManager.hasTask(task2));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
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

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

    @Test
    public void resetPersonPriority_validPerson_priorityLevelReset() {
        modelManager.addPerson(ALICE);

        // Ensure initial priority level is set correctly
        assertEquals(3, ALICE.getPriorityLevel().getValue());

        // Reset the priority level
        modelManager.resetPersonPriority(ALICE);

        // Check that the priority level is reset to default (3)
        assertEquals(3, ALICE.getPriorityLevel().getValue());
    }

    @Test
    public void resetPersonPriority_afterReset_priorityLevelIsThree() {
        modelManager.addPerson(ALICE);

        // Reset priority level
        modelManager.resetPersonPriority(ALICE);

        // Verify the priority level has been reset to 3
        assertEquals(3, ALICE.getPriorityLevel().getValue());
    }

    @Test
    public void resetPersonPriority_multipleResets_priorityLevelRemainsThree() {
        modelManager.addPerson(ALICE);

        // Reset priority level multiple times
        modelManager.resetPersonPriority(ALICE);
        modelManager.resetPersonPriority(ALICE);

        // Verify the priority level remains 3 after multiple resets
        assertEquals(3, ALICE.getPriorityLevel().getValue());
    }

    @Test
    public void updateTasksForPerson_personWithTasks_updatesPriority() {
        // Create a person and add to address book
        modelManager.addPerson(ALICE);

        // Create tasks associated with the person and add to address book
        Task task1 = new TaskBuilder().withDescription("Task 1").withPatient(ALICE).build();
        Task task2 = new TaskBuilder().withDescription("Task 2").withPatient(ALICE).build();
        modelManager.addTask(task1);
        modelManager.addTask(task2);

        // Create an edited person with a different priority level
        Person editedAlice = new PersonBuilder(ALICE).withPriorityLevel(1).build();

        // Update tasks for the person
        modelManager.updateTasksForPerson(ALICE, editedAlice);

        // Verify that tasks have been updated with the edited person
        for (Task task : modelManager.getFilteredTaskList()) {
            if (task.getPatient().equals(editedAlice)) {
                assertEquals(1, task.getPatient().getPriorityLevel().getValue());
            }
        }
    }



}
