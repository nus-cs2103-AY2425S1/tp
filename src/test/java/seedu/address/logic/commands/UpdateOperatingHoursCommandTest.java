package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UpdateOperatingHoursCommand.MESSAGE_FAILED;
import static seedu.address.logic.commands.UpdateOperatingHoursCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.OperatingHours;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;


/**
 * Contains integration tests (interaction with the Model) for {@code UpdateOperatingHoursCommand}.
 */
public class UpdateOperatingHoursCommandTest {

    @TempDir
    public Path temporaryFolder;
    private Model model;
    private Model expectedModel;

    private OperatingHours defaultHours = new OperatingHours(null, null);
    private OperatingHours openingHours = new OperatingHours(LocalTime.of(8, 30), null);
    private OperatingHours closingHours = new OperatingHours(null, LocalTime.of(21, 30));
    private OperatingHours unreasonableClosingHours = new OperatingHours(null, LocalTime.of(12, 30));

    private String expectedMessageDefault = MESSAGE_SUCCESS + defaultHours;
    private String expectedMessageOpening = MESSAGE_SUCCESS + openingHours;
    private String expectedMessageClosing = MESSAGE_SUCCESS + closingHours;

    @Test
    public void constructor_nullOperatingHours_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateOperatingHoursCommand(null));
    }

    /**
     * Sets up the test environment with the required model and storage.
     */
    @BeforeEach
    public void setUp() throws IOException {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage =
                new StorageManager(addressBookStorage, userPrefsStorage);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);

        JsonAddressBookStorage addressBookExpectedStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsExpectedStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager expectedStorage = new StorageManager(addressBookExpectedStorage, userPrefsExpectedStorage);

        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), expectedStorage);
    }

    @Test
    public void equals() {

        UpdateOperatingHoursCommand defaultCommand = new UpdateOperatingHoursCommand(defaultHours);
        UpdateOperatingHoursCommand openingCommand = new UpdateOperatingHoursCommand(openingHours);
        UpdateOperatingHoursCommand closingCommand = new UpdateOperatingHoursCommand(closingHours);

        assertTrue(defaultCommand.equals(defaultCommand));
        assertTrue(defaultCommand.equals(new UpdateOperatingHoursCommand(
                                                new OperatingHours(null, null))));

        assertFalse(defaultCommand.equals(null));
        assertFalse(defaultCommand.equals(1));
        assertFalse(defaultCommand.equals(openingCommand));
        assertFalse(defaultCommand.equals(closingCommand));
    }

    @Test
    public void execute_noAppointments_success() {

        UpdateOperatingHoursCommand commandDefault = new UpdateOperatingHoursCommand(defaultHours);
        UpdateOperatingHoursCommand commandOpening = new UpdateOperatingHoursCommand(openingHours);
        UpdateOperatingHoursCommand commandClosing = new UpdateOperatingHoursCommand(closingHours);

        assertCommandSuccess(commandDefault, model, expectedMessageDefault, model);
        assertCommandSuccess(commandClosing, model, expectedMessageClosing, model);
        assertCommandSuccess(commandOpening, model, expectedMessageOpening, model);
    }

    @Test
    public void execute_haveAppointment_success() {
        UpdateOperatingHoursCommand commandDefault = new UpdateOperatingHoursCommand(defaultHours);
        UpdateOperatingHoursCommand commandOpening = new UpdateOperatingHoursCommand(openingHours);
        UpdateOperatingHoursCommand commandClosing = new UpdateOperatingHoursCommand(unreasonableClosingHours);

        assertCommandSuccess(commandDefault, expectedModel, expectedMessageDefault, expectedModel);
        assertCommandSuccess(commandOpening, expectedModel, expectedMessageOpening, expectedModel);
        assertCommandFailure(commandClosing, model, MESSAGE_FAILED);
    }

    @Test
    public void toStringMethod() {
        UpdateOperatingHoursCommand commandDefault = new UpdateOperatingHoursCommand(defaultHours);
        String expected = UpdateOperatingHoursCommand.class.getCanonicalName() + "{toUpdate=" + defaultHours + "}";
        assertEquals(expected, commandDefault.toString());
    }

}
