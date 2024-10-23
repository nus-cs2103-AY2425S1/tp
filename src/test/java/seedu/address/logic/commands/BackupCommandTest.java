//package seedu.address.logic.commands;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
//import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Optional;
//import java.util.stream.Stream;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import seedu.address.model.Model;
//import seedu.address.model.ModelManager;
//import seedu.address.model.ReadOnlyAddressBook;
//import seedu.address.model.ReadOnlyUserPrefs;
//import seedu.address.model.UserPrefs;
//import seedu.address.storage.JsonAddressBookStorage;
//import seedu.address.storage.JsonUserPrefsStorage;
//import seedu.address.storage.Storage;
//import seedu.address.storage.StorageManager;
//
///**
// * Contains integration tests (interaction with the Model) and unit tests for {@code BackupCommand}.
// */
//public class BackupCommandTest {
//
//    @TempDir
//    public Path temporaryFolder;
//    private Model model;
//    private Model expectedModel;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        JsonAddressBookStorage addressBookStorage =
//                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
//        JsonUserPrefsStorage userPrefsStorage =
//                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
//        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
//
//        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storage);
//
//        JsonAddressBookStorage addressBookExpectedStorage =
//                new JsonAddressBookStorage(temporaryFolder.resolve("addressBookExpected.json"));
//        JsonUserPrefsStorage userPrefsExpectedStorage =
//                new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefsExpected.json"));
//        StorageManager expectedStorage = new StorageManager(addressBookExpectedStorage, userPrefsExpectedStorage);
//
//        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), expectedStorage);
//
//        // Reset the static variable before each test
//        BackupCommand.lastManualBackupTime = 0;
//    }
//
//    @AfterEach
//    public void tearDown() throws IOException {
//        // Clean up any backup files created during the test
//        Path backupDir = Path.of("backups");
//        if (Files.exists(backupDir)) {
//            try (Stream<Path> backups = Files.list(backupDir)) {
//                backups.forEach(path -> {
//                    try {
//                        Files.deleteIfExists(path);
//                    } catch (IOException e) {
//                        // Log the error if deletion fails
//                        System.err.println("Failed to delete backup file: " + path);
//                    }
//                });
//            }
//        }
//    }
//
//
//    @Test
//    public void executeBackupCommand_ioException_throwsCommandException() throws IOException {
//        Storage storageStub = createStorageStubForIoException();
//        Model modelWithFailingStorage = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storageStub);
//
//        BackupCommand backupCommand = new BackupCommand();
//
//        assertCommandFailure(backupCommand, modelWithFailingStorage, String.format(
//                BackupCommand.MESSAGE_FAILURE, "Simulated IOException"));
//    }
//
//    private Storage createStorageStubForIoException() {
//        return new Storage() {
//            @Override
//            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
//                throw new IOException("Simulated IOException");
//            }
//
//            @Override
//            public void saveAddressBook(ReadOnlyAddressBook addressBook) {
//                // No-op
//            }
//
//            @Override
//            public Optional<UserPrefs> readUserPrefs() {
//                return Optional.empty();
//            }
//
//            @Override
//            public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) {
//                // No-op
//            }
//
//            @Override
//            public Path getAddressBookFilePath() {
//                return null;
//            }
//
//            @Override
//            public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) {
//                return Optional.empty();
//            }
//
//            @Override
//            public Optional<ReadOnlyAddressBook> readAddressBook() {
//                return Optional.empty();
//            }
//
//            @Override
//            public Path getUserPrefsFilePath() {
//                return null;
//            }
//
//            @Override
//            public Optional<Path> restoreBackup() {
//                return Optional.empty();
//            }
//
//            @Override
//            public void cleanOldBackups(int maxBackups) {
//                // No-op
//            }
//        };
//    }
//
//    @Test
//    public void executeBackupCommand_success() throws Exception {
//        BackupCommand backupCommand = new BackupCommand();
//
//        // Execute the command
//        String timestampPattern = "\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}-\\d{3}";
//        String expectedMessagePattern = String.format(
//                "Backup successful at backups/clinicbuddy-backup-%s.json", timestampPattern
//        );
//
//        // Use regex to match the actual message
//        CommandResult result = backupCommand.execute(model);
//        assertTrue(result.getFeedbackToUser().matches(expectedMessagePattern),
//                "Backup message should match the expected pattern.");
//
//        // Verify that the backup file was created
//        try (Stream<Path> backups = Files.list(Path.of("backups"))) {
//            boolean backupExists = backups.anyMatch(path ->
//                    path.getFileName().toString().matches("clinicbuddy-backup-" + timestampPattern + "\\.json")
//            );
//            assertTrue(backupExists, "Backup file should have been created with the correct format.");
//        }
//    }
//
//    @Test
//    public void executeBackupCommand_storageThrowsIoException_throwsCommandException() throws Exception {
//        // Create a storage stub that simulates an IOException
//        Storage storageStub = createStorageStubForIoException();
//        Model modelWithFailingStorage = new ModelManager(getTypicalAddressBook(), new UserPrefs(), storageStub);
//
//        BackupCommand backupCommand = new BackupCommand();
//
//        assertCommandFailure(backupCommand, modelWithFailingStorage,
//                String.format(BackupCommand.MESSAGE_FAILURE, "Simulated IOException"));
//    }
//
//    @Test
//    public void executeBackupCommand_validBackup_createsBackup() throws Exception {
//        BackupCommand backupCommand = new BackupCommand();
//
//        // Execute the command
//        CommandResult result = backupCommand.execute(model);
//        assertTrue(result.getFeedbackToUser().contains("Backup successful"),
//                "Backup message should contain success indication.");
//
//        // Verify that the backup file was created
//        try (Stream<Path> backups = Files.list(Path.of("backups"))) {
//            boolean backupExists = backups.anyMatch(path ->
//                    path.getFileName().toString().matches(
//                            "clinicbuddy-backup-\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}-\\d{3}\\.json")
//            );
//            assertTrue(backupExists, "Backup file should have been created with the correct format.");
//        }
//    }
//
//}
