package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalTags.FLORIST;
import static seedu.address.testutil.TypicalTasks.TODO_TASK;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.keywordspredicate.NameContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;
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
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void hasTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasTag(null));
    }

    @Test
    public void hasTag_tagNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasTag(FLORIST));
    }

    @Test
    public void hasTag_tagInAddressBook_returnsTrue() {
        modelManager.addTag(FLORIST);
        assertTrue(modelManager.hasTag(FLORIST));
    }

    @Test
    public void getFilteredTagList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredTagList().remove(0));
    }

    @Test
    public void addTag_validTag_addsTagSuccessfully() {
        Tag newTag = new Tag(new TagName("colleague"));
        modelManager.addTag(newTag);
        assertTrue(modelManager.hasTag(newTag));
    }

    @Test
    public void setTag_nullTag_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setTag(null, FLORIST));
        assertThrows(NullPointerException.class, () -> modelManager.setTag(FLORIST, null));
    }

    @Test
    public void updateFilteredTagList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredTagList(null));
    }

    @Test
    public void setTag_validTag_replacesTagSuccessfully() {
        modelManager.addTag(FLORIST);
        Tag editedTag = new Tag(new TagName("newFlorist"));
        modelManager.setTag(FLORIST, editedTag);
        assertTrue(modelManager.hasTag(editedTag));
        assertFalse(modelManager.hasTag(FLORIST));
    }

    @Test
    public void setTask_validTask_replacesTaskSuccessfully() {
        modelManager.addTask(TODO_TASK);
        Task editedTask = new Todo("New task description");
        modelManager.setTask(TODO_TASK, editedTask);
        assertTrue(modelManager.hasTask(editedTask));
        assertFalse(modelManager.hasTask(TODO_TASK));
    }

    @Test
    public void setWedding_validWedding_replacesWeddingSuccessfully() {
        Wedding wedding = new Wedding(new WeddingName("Old Wedding"));
        Wedding editedWedding = new Wedding(new WeddingName("New Wedding"));
        modelManager.addWedding(wedding);
        modelManager.setWedding(wedding, editedWedding);
        assertTrue(modelManager.hasWedding(editedWedding));
        assertFalse(modelManager.hasWedding(wedding));
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
}
