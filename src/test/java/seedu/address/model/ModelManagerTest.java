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
import seedu.address.model.link.Link;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.PawPatrolBuilder;
import seedu.address.testutil.TypicalOwners;
import seedu.address.testutil.TypicalPets;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PawPatrol(), new PawPatrol(modelManager.getPawPatrol()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPawPatrolFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPawPatrolFilePath(Paths.get("new/address/book/file/path"));
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
    public void setPawPatrolFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPawPatrolPath(null));
    }

    @Test
    public void setPawPatrolFilePath_validPath_setsPawPatrolFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPawPatrolPath(path);
        assertEquals(path, modelManager.getPawPatrolFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInPawPatrol_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasOwner_ownerNotInPawPatrol_returnsFalse() {
        assertFalse(modelManager.hasOwner(seedu.address.testutil.TypicalOwners.ALICE));
    }

    @Test
    public void hasPet_petNotInPawPatrol_returnsFalse() {
        assertFalse(modelManager.hasPet(TypicalPets.BELLA));
    }

    @Test
    public void hasLink_linkNotInPawPatrol_returnsFalse() {
        assertFalse(modelManager.hasLink(new Link(TypicalOwners.ALICE, TypicalPets.BELLA)));
    }

    @Test
    public void hasPerson_personInPawPatrol_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasOwner_ownerInPawPatrol_returnsTrue() {
        modelManager.addOwner(seedu.address.testutil.TypicalOwners.ALICE);
        assertTrue(modelManager.hasOwner(seedu.address.testutil.TypicalOwners.ALICE));
    }

    @Test
    public void hasPet_petInPawPatrol_returnsTrue() {
        modelManager.addPet(TypicalPets.BELLA);
        assertTrue(modelManager.hasPet(TypicalPets.BELLA));
    }

    @Test
    public void hasLink_linkInPawPatrol_returnsTrue() {
        Link link = new Link(TypicalOwners.ALICE, TypicalPets.BELLA);
        modelManager.addLink(link);
        assertTrue(modelManager.hasLink(link));
    }

    @Test
    public void deleteOwner_ownerInPawPatrol_returnsFalse() {
        modelManager.addOwner(seedu.address.testutil.TypicalOwners.ALICE);
        modelManager.deleteOwner(seedu.address.testutil.TypicalOwners.ALICE);
        assertFalse(modelManager.hasOwner(seedu.address.testutil.TypicalOwners.ALICE));
    }

    @Test
    public void deleteLink_linkInPawPatrol_returnsFalse() {
        Link link = new Link(TypicalOwners.ALICE, TypicalPets.BELLA);
        modelManager.addLink(link);
        modelManager.deleteLink(link);
        assertFalse(modelManager.hasLink(link));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getFilteredOwnerList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredOwnerList().remove(0));
    }

    @Test
    public void equals() {
        PawPatrol pawPatrol = new PawPatrolBuilder().withPerson(ALICE).withPerson(BENSON).build();
        PawPatrol differentPawPatrol = new PawPatrol();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(pawPatrol, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(pawPatrol, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different pawPatrol -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPawPatrol, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(pawPatrol, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPawPatrolFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(pawPatrol, differentUserPrefs)));
    }
}
