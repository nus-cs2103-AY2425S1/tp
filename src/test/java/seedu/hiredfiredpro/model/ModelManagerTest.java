package seedu.hiredfiredpro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hiredfiredpro.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.hiredfiredpro.testutil.Assert.assertThrows;
import static seedu.hiredfiredpro.testutil.TypicalPersons.ALICE;
import static seedu.hiredfiredpro.testutil.TypicalPersons.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.hiredfiredpro.commons.core.GuiSettings;
import seedu.hiredfiredpro.model.person.Job;
import seedu.hiredfiredpro.model.person.Name;
import seedu.hiredfiredpro.model.person.NameContainsKeywordsPredicate;
import seedu.hiredfiredpro.model.person.Person;
import seedu.hiredfiredpro.testutil.HiredFiredProBuilder;


public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new HiredFiredPro(), new HiredFiredPro(modelManager.getHiredFiredPro()));
    }

    @Test
    public void setPersons_validList_setsPersons() {
        List<Person> persons = List.of(ALICE, BENSON);
        modelManager.setPersons(persons);
        assertEquals(persons, modelManager.getHiredFiredPro().getPersonList());
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> modelManager.setPersons(null));
    }

    @Test
    public void getSortedPersonList_returnsSortedPersons() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        ObservableList<Person> sortedPersons = modelManager.getSortedPersonList();
        assertEquals(2, sortedPersons.size());
        assertEquals(ALICE, sortedPersons.get(0));
        assertEquals(BENSON, sortedPersons.get(1));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setHiredFiredProFilePath(Paths.get("hired/fired/pro/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setHiredFiredProFilePath(Paths.get("new/hired/fired/pro/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void findPersonByNameAndJob_success() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        assertEquals(modelManager.findPersonByNameAndJob(new Name("Alice Pauline"),
                new Job("Software Engineer L1")), ALICE);
        assertEquals(modelManager.findPersonByNameAndJob(new Name("Benson Meier"),
                new Job("Software Engineer L2")), BENSON);
    }

    @Test
    public void findPersonByNameAndJob_failure() {
        modelManager.addPerson(ALICE);
        modelManager.addPerson(BENSON);
        // non-matching name, matching job
        assertNull(modelManager.findPersonByNameAndJob(new Name("Carl Kurz"),
                new Job("Software Engineer L1")));

        // matching name, non-matching job
        assertNull(modelManager.findPersonByNameAndJob(new Name("Alice Pauline"),
                new Job("Software Engineer L3")));

        // non-matching name, non-matching job
        assertNull(modelManager.findPersonByNameAndJob(new Name("Daniel Meier"),
                new Job("Software Engineer L4")));
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
    public void setHiredFiredProFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setHiredFiredProFilePath(null));
    }

    @Test
    public void setHiredFiredProFilePath_validPath_setsHiredFiredProFilePath() {
        Path path = Paths.get("hiredfired/pro/file/path");
        modelManager.setHiredFiredProFilePath(path);
        assertEquals(path, modelManager.getHiredFiredProFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void constructor_withHiredFiredProAndUserPrefs_initializesCorrectly() {
        HiredFiredPro hiredFiredPro = new HiredFiredProBuilder().withPerson(ALICE).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(hiredFiredPro, userPrefs);

        assertEquals(hiredFiredPro, modelManager.getHiredFiredPro());
        assertEquals(userPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void hasPerson_personNotInHiredFiredPro_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInHiredFiredPro_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        HiredFiredPro hiredFiredPro = new HiredFiredProBuilder().withPerson(ALICE).withPerson(BENSON).build();
        HiredFiredPro differentHiredFiredPro = new HiredFiredPro();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(hiredFiredPro, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(hiredFiredPro, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different hiredFiredPro -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentHiredFiredPro, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(hiredFiredPro, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setHiredFiredProFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(hiredFiredPro, differentUserPrefs)));
    }
}
