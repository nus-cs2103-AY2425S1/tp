package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Module;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.EduContactsBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EduContacts(), new EduContacts(modelManager.getEduContacts()));
        assertEquals(null, modelManager.getPersonToDisplay());
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setEduContactsFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setEduContactsFilePath(Paths.get("new/address/book/file/path"));
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
    public void setEduContactsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setEduContactsFilePath(null));
    }

    @Test
    public void setEduContactsFilePath_validPath_setsEduContactsFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setEduContactsFilePath(path);
        assertEquals(path, modelManager.getEduContactsFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInEduContacts_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInEduContacts_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void setPersonToDisplay_validPerson_success() {
        modelManager.addPerson(ALICE);
        modelManager.setPersonToDisplay(ALICE);

        // same person -> returns true
        assertEquals(new ModelManager(modelManager.getEduContacts(), modelManager.getUserPrefs(), ALICE), modelManager);

        // different person -> returns false
        assertNotEquals(new ModelManager(modelManager.getEduContacts(), modelManager.getUserPrefs(), BENSON),
                modelManager);
    }

    @Test
    public void setPersonToDisplay_invalidPerson_nothingHappens() {
        modelManager.addPerson(ALICE);
        modelManager.setPersonToDisplay(ALICE);
        modelManager.setPersonToDisplay(BENSON);

        assertEquals(new ModelManager(modelManager.getEduContacts(),
                modelManager.getUserPrefs(),
                ALICE), modelManager);
    }

    @Test
    public void getPersonToDisplay_personToDisplayEqualToPersonSet_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager = new ModelManager(modelManager.getEduContacts(), modelManager.getUserPrefs(), ALICE);
        // same person -> returns true
        assertEquals(ALICE, modelManager.getPersonToDisplay());

        // different person -> returns false
        assertNotEquals(BENSON, modelManager.getPersonToDisplay());
    }
    @Test
    public void addModule_invalidPerson_throwsException() {
        Module module = new Module("CS2101");
        assertThrows(IllegalArgumentException.class, () -> modelManager.addModule(BENSON, module));
    }

    @Test
    public void deleteModule_invalidPerson_throwsException() {
        Module module = new Module("CS2101");
        assertThrows(IllegalArgumentException.class, () -> modelManager.deleteModule(BENSON, module));
    }
    @Test
    public void equals() {
        EduContacts eduContacts = new EduContactsBuilder().withPerson(ALICE).withPerson(BENSON).build();
        EduContacts differentEduContacts = new EduContacts();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(eduContacts, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(eduContacts, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        modelManager = new ModelManager(eduContacts, userPrefs, ALICE);
        modelManagerCopy = new ModelManager(eduContacts, userPrefs, ALICE);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different eduContacts -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentEduContacts, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(eduContacts, userPrefs)));

        // different personToDisplay -> returns false
        assertFalse(modelManager.equals(new ModelManager(eduContacts, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager = new ModelManager(eduContacts, userPrefs);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setEduContactsFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(eduContacts, differentUserPrefs)));
    }
}
