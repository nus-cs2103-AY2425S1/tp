package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;

import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;

public class ClientUtilTest {

    @TempDir
    public Path temporaryFolder;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Logic logic;
    private List<Person> personList;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
        personList = logic.getFilteredPersonList();
    }
    @Test
    public void findViewPerson_personExists() {
        // Test finding "Alice Pauline"
        String viewCommandAlice = "view Alice Pauline";
        Person foundAlicePerson = ClientUtil.findViewPerson(viewCommandAlice, logic);
        assertEquals(personList.get(0), foundAlicePerson);

        // Test finding "Daniel Meier"
        String viewCommandDaniel = "view Daniel Meier";
        Person foundDanielPerson = ClientUtil.findViewPerson(viewCommandDaniel, logic);
        assertEquals(personList.get(3), foundDanielPerson);

        // Test finding "Fiona Kunz"
        String viewCommandFiona = "view Fiona Kunz";
        Person foundFionaPerson = ClientUtil.findViewPerson(viewCommandFiona, logic);
        assertEquals(personList.get(5), foundFionaPerson);

        // Test finding invalid person
        String viewCommandInvalid = "view John Doe";
        Person foundNull = ClientUtil.findViewPerson(viewCommandInvalid, logic);
        Assertions.assertNull(foundNull);

    }


    private void assertNull(Person foundPerson) {
    }
}
