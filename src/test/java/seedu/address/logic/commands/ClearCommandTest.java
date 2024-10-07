package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ClearCommand}.
 */
public class ClearCommandTest {

    @TempDir
    public Path temporaryFolder;

    private StorageManager storage;

    /**
     * Sets up the test environment with the required model and storage.
     */
    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Model createModelWithAddressBook(AddressBook addressBook) {
        return new ModelManager(addressBook, new UserPrefs(), storage);
    }

    /**
     * Tests the successful execution of clearing an empty address book.
     */
    @Test
    public void execute_emptyAddressBook_success() {
        Model model = createModelWithAddressBook(new AddressBook());
        Model expectedModel = createModelWithAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    /**
     * Tests the successful execution of clearing a non-empty address book.
     */
    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = createModelWithAddressBook(getTypicalAddressBook());
        Model expectedModel = createModelWithAddressBook(getTypicalAddressBook());
        expectedModel.setAddressBook(new AddressBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
