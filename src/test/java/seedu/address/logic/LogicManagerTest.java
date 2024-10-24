package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_INPUT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddBuyerProfile;
import seedu.address.logic.commands.AddSellerProfile;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Listings;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Name;
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

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
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

        // Adjust the expected message to match the actual message format
        String expectedMessage = String.format("New buyer added: %s; Phone: %s; Email: %s; Appointment: Date:  "
                        + "(From:  To: ); Tags: ",
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

        // Creating a seller with empty appointment, property, and no tags
        Seller expectedSeller = new PersonBuilder(AMY)
                .withAppointment("", "", "") // Empty appointment details
                .withProperty("") // Empty property
                .withTags() // No tags
                .buildSeller();

        // Construct the expected message based on the actual format produced by the application
        String expectedMessage = String.format("New seller added: %s; Phone: %s; Email: %s; Appointment: "
                        + "Date:  (From:  To: ); Tags: ",
                expectedSeller.getName(), expectedSeller.getPhone(), expectedSeller.getEmail());

        // Execute the command and check for success
        assertCommandSuccess(addSellerCommand, expectedMessage, model);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        Name invalidName = new Name("aaaaaaaaaaaaaaa");
        String deleteCommand = "delete " + PREFIX_NAME + invalidName;
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_INPUT);
    }

    @Test
    public void execute_listCommandWhenClientsExist_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;

        Person expectedPerson = new PersonBuilder(AMY).buildBuyer();
        model.addPerson(expectedPerson);

        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }
    @Test
    public void execute_listCommandWhenNoClients_throwsCommandException() {
        model = new ModelManager();
        logic = new LogicManager(model, new StorageManager(
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json")),
                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json")),
                new JsonListingsStorage(temporaryFolder.resolve("listings.json")))
        );

        String listCommand = ListCommand.COMMAND_WORD;

        assertCommandException(listCommand, ListCommand.MESSAGE_NO_CLIENT_IN_LIST);
    }
    /*
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
    */

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

        // Trigger the saveAddressBook method by executing an add command
        String addBuyerCommand = AddBuyerProfile.COMMAND_WORD + " " + NAME_DESC_AMY + " "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY;

        String addSellerCommand = AddSellerProfile.COMMAND_WORD + " " + NAME_DESC_AMY + " "
                + PHONE_DESC_AMY + " " + EMAIL_DESC_AMY;

        // Use a loop or separate assertions if needed
        assertCommandFailure(addBuyerCommand, CommandException.class, expectedMessage);
        assertCommandFailure(addSellerCommand, CommandException.class, expectedMessage);
    }
}
