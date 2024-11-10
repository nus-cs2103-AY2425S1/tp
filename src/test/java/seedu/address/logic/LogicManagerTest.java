package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.clientcommands.AddBuyerProfile;
import seedu.address.logic.commands.clientcommands.AddSellerProfile;
import seedu.address.logic.commands.clientcommands.ShowClientsCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Person;
import seedu.address.model.person.Seller;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonListingsStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model;
    private Logic logic;

    @BeforeEach
    public void setUp() {
        model = new ModelManager();
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonListingsStorage listingsStorage = new JsonListingsStorage(temporaryFolder.resolve("listings.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, listingsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_addBuyerProfile_success() throws Exception {
        // Reset the model to avoid conflicts from previous tests
        model = new ModelManager();

        String addBuyerCommand = AddBuyerProfile.COMMAND_WORD + " " + NAME_DESC_AMY + " "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY;

        Buyer expectedBuyer = new PersonBuilder(AMY).buildBuyer();

        String expectedMessage = String.format("New buyer added: %1s.\nPhone number: %2s and Email: %3s",
                expectedBuyer.getName(), expectedBuyer.getPhone(), expectedBuyer.getEmail());

        model.addPerson(expectedBuyer);
        assertCommandSuccess(addBuyerCommand, expectedMessage, model);
    }

    @Test
    public void execute_addSellerProfile_success() throws Exception {
        // Reset the model to avoid conflicts from previous tests
        model = new ModelManager();

        String addSellerCommand = AddSellerProfile.COMMAND_WORD + " " + NAME_DESC_AMY + " "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY;

        // Creating a seller with empty appointment, and no tags
        Seller expectedSeller = new PersonBuilder(AMY)
                .withTags() // No tags
                .buildSeller();

        String expectedMessage = String.format("New seller added: %1s.\nPhone number: %2s and Email: %3s",
                expectedSeller.getName(), expectedSeller.getPhone(), expectedSeller.getEmail());

        // Execute the command and check for success
        assertCommandSuccess(addSellerCommand, expectedMessage, model);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String invalidIndex = Integer.toString(model.getFilteredPersonList().size() + 1);
        String deleteCommand = "deleteclient " + invalidIndex;
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_listCommandWhenClientsExist_success() throws Exception {
        String listCommand = ShowClientsCommand.COMMAND_WORD;

        Person expectedPerson = new PersonBuilder(AMY).buildBuyer();
        model.addPerson(expectedPerson);

        assertCommandSuccess(listCommand, ShowClientsCommand.MESSAGE_SUCCESS, model);
    }
    @Test
    public void execute_listCommandWhenNoClients_throwsCommandException() {
        model = new ModelManager();
        logic = new LogicManager(model, new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")),
                new JsonListingsStorage(temporaryFolder.resolve("listings.json")))
        );

        String listCommand = ShowClientsCommand.COMMAND_WORD;

        assertCommandException(listCommand, ShowClientsCommand.MESSAGE_NO_CLIENT_IN_LIST);
    }
    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }
    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new Listings());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
                throw e; // Simulate an IOException
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        JsonListingsStorage listingsStorage =
                new JsonListingsStorage(temporaryFolder.resolve("ExceptionListings.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, listingsStorage);

        logic = new LogicManager(model, storage);

        // Trigger the saveAddressBook method by executing an add buyer command
        String addBuyerCommand = AddBuyerProfile.COMMAND_WORD + " " + NAME_DESC_AMY + " "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY;
        Buyer expectedBuyer = new PersonBuilder(AMY).withTags().buildBuyer();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedBuyer);
        assertCommandFailure(addBuyerCommand, CommandException.class, expectedMessage, expectedModel);
    }
    @Test
    public void getAddressBook_returnsCorrectAddressBook() {
        Model testModel = new ModelManager();
        Logic testLogic = new LogicManager(testModel, new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")),
                new JsonListingsStorage(temporaryFolder.resolve("listings.json"))));

        assertEquals(testModel.getAddressBook(), testLogic.getAddressBook());
    }
    @Test
    public void getListingsFilePath_returnsCorrectListingsFilePath() {
        Model testModel = new ModelManager();
        Logic testLogic = new LogicManager(testModel, new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")),
                new JsonListingsStorage(temporaryFolder.resolve("listings.json"))));

        assertEquals(testModel.getListingsFilePath(), testLogic.getListingsFilePath());
    }
}
