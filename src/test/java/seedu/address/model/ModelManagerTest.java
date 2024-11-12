package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.person.Birthday.BIRTHDAY_REMINDER_EMPTY;
import static seedu.address.model.person.Birthday.BIRTHDAY_REMINDER_HEADER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

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
    void testGetBirthdayPredicate() {
        Person editedAlice = new PersonBuilder(ALICE).withBirthday(LocalDate.now()
                .plusDays(1).minusYears(20).toString()).build();
        Person editedBob = new PersonBuilder(BOB).withBirthday(LocalDate.now()
                .minusDays(1).minusYears(20).toString()).build();
        AddressBook addressBook = new AddressBookBuilder().withPerson(editedAlice).withPerson(editedBob).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, userPrefs);
        Predicate<Person> birthdayPredicate = modelManager.getBirthdayPredicate();
        assertTrue(birthdayPredicate.test(editedAlice));
        assertFalse(birthdayPredicate.test(editedBob));
    }

    @Test
    void testGetPersonsWithUpcomingBirthdays() {
        Person editedAlice = new PersonBuilder(ALICE).withBirthday(LocalDate.now()
                .plusDays(7).minusYears(20).toString()).build();
        Person editedBob = new PersonBuilder(BOB).withBirthday(LocalDate.now()
                .minusDays(1).minusYears(20).toString()).build();
        AddressBook addressBook = new AddressBookBuilder().withPerson(editedAlice).withPerson(editedBob).build();
        UserPrefs userPrefs = new UserPrefs();

        modelManager = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager.getPersonsWithUpcomingBirthdays(), BIRTHDAY_REMINDER_EMPTY);

        LocalDate sixDaysAfterTodaySomeYearsBack = LocalDate.now().plusDays(6).minusYears(20);
        LocalDate todaySomeYearsBack = LocalDate.now().minusDays(0).minusYears(20);
        Person editedBenson = new PersonBuilder(BENSON).withBirthday(sixDaysAfterTodaySomeYearsBack.toString()).build();
        Person editedCarl = new PersonBuilder(CARL).withBirthday(todaySomeYearsBack.toString()).build();
        addressBook = new AddressBookBuilder().withPerson(editedBenson).withPerson(editedCarl).build();

        modelManager = new ModelManager(addressBook, userPrefs);
        assertEquals(modelManager.getPersonsWithUpcomingBirthdays(), BIRTHDAY_REMINDER_HEADER
                + "Benson Meier's birthday is on " + sixDaysAfterTodaySomeYearsBack.plusYears(20) + "\n"
                + "Carl Kurz's birthday is on " + todaySomeYearsBack.plusYears(20));
    }
}
