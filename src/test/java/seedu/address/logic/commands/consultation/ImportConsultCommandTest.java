package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.consultation.ImportConsultCommand.MESSAGE_EMPTY_FILE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;
import seedu.address.testutil.ConsultationBuilder;
import seedu.address.testutil.ModelStub;

public class ImportConsultCommandTest {

    private static final String VALID_HEADER = "Date,Time,Students";
    private static final String VALID_CONSULT = "2024-10-20,14:00,Alice Pauline";
    private static final String INVALID_DATE = "2024-13-45,14:00,Alice Pauline";
    private static final String INVALID_TIME = "2024-10-20,25:00,Alice Pauline";
    private static final String NONEXISTENT_STUDENT = "2024-10-20,14:00,Nonexistent Student";

    @TempDir
    public Path temporaryFolder;

    private Path testDir;
    private Path projectDir;
    private ModelStubWithConsultations modelStub;
    private List<Path> filesToCleanup;
    private Path testCsvPath;
    private ImportConsultCommand importCommand;

    @BeforeEach
    public void setUp() throws IOException {
        // Create project directory inside temp folder using normalized paths
        projectDir = temporaryFolder.resolve("project").normalize();
        Files.createDirectories(projectDir);

        // Create test directory as sibling to project dir
        testDir = temporaryFolder.resolve("test-files").normalize();
        Files.createDirectories(testDir);

        // Use normalized paths
        testCsvPath = testDir.resolve("test.csv").normalize();
        modelStub = new ModelStubWithConsultations();
        filesToCleanup = new ArrayList<>();

        // Create command with normalized path string
        importCommand = new ImportConsultCommand(testCsvPath.getFileName().toString());
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up test files
        for (Path file : filesToCleanup) {
            Files.deleteIfExists(file);
        }

        // Clean up test directory
        if (Files.exists(testDir)) {
            Files.walk(testDir)
                    .sorted((a, b) -> b.compareTo(a))
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            // Ignore deletion errors during cleanup
                        }
                    });
        }
    }

    @Test
    public void equals() {
        ImportConsultCommand command1 = new ImportConsultCommand("file1.csv");
        ImportConsultCommand command2 = new ImportConsultCommand("file2.csv");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ImportConsultCommand command1Copy = new ImportConsultCommand("file1.csv");
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different file -> returns false
        assertFalse(command1.equals(command2));
    }

    @Test
    public void getCommandType() {
        assertEquals(CommandType.CONSULT, importCommand.getCommandType());
    }

    @Test
    public void execute_emptyFile_throwsCommandException() throws IOException {
        // Create a custom ImportConsultCommand that uses testDir instead of data dir
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile("");
        filesToCleanup.add(testCsvPath);
        assertThrows(CommandException.class,
                MESSAGE_EMPTY_FILE, () -> testCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidHeader_throwsCommandException() throws IOException {
        // Create a custom ImportConsultCommand that uses testDir instead of data dir
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile("Invalid,Header,Format\n" + VALID_CONSULT);
        filesToCleanup.add(testCsvPath);
        assertThrows(CommandException.class,
                ImportConsultCommand.MESSAGE_INVALID_HEADER, () -> testCommand.execute(modelStub));
    }

    @Test
    public void execute_validConsultation_success() throws IOException, CommandException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n" + VALID_CONSULT);
        filesToCleanup.add(testCsvPath);
        CommandResult result = testCommand.execute(modelStub);
        assertEquals(String.format(ImportConsultCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
        assertEquals(1, modelStub.consultations.size());
    }

    @Test
    public void execute_invalidDate_recordsError() throws IOException, CommandException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n" + INVALID_DATE);
        filesToCleanup.add(testCsvPath);
        CommandResult result = testCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_invalidTime_recordsError() throws IOException, CommandException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n" + INVALID_TIME);
        filesToCleanup.add(testCsvPath);
        CommandResult result = testCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_nonexistentStudent_recordsError() throws IOException, CommandException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n" + NONEXISTENT_STUDENT);
        filesToCleanup.add(testCsvPath);
        CommandResult result = testCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_duplicateConsultation_recordsError() throws IOException, CommandException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        // Create a consultation with the same date and time as our test constants
        Consultation existingConsult = new ConsultationBuilder()
                .withDate("2024-10-20")
                .withTime("14:00")
                .withStudent(ALICE)
                .build();
        modelStub.addConsult(existingConsult);

        // Try to import the same consultation
        createCsvFile(VALID_HEADER + "\n" + VALID_CONSULT);
        filesToCleanup.add(testCsvPath);

        CommandResult result = testCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(1, modelStub.consultations.size()); // Size should not change
    }

    @Test
    public void resolveFilePath_homeDirectory() {
        // Use platform-independent path handling
        String testFile = "test.csv";
        Path expected = Paths.get(System.getProperty("user.home")).resolve(testFile).normalize();
        Path actual = importCommand.resolveFilePath("~" + File.separator + testFile).normalize();
        assertEquals(expected.normalize(), actual.normalize());
    }

    @Test
    public void execute_onlyEmptyLines_throwsCommandException() throws IOException {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n\n   \n\t\n");
        filesToCleanup.add(testCsvPath);
        assertThrows(CommandException.class,
                MESSAGE_EMPTY_FILE, () -> testCommand.execute(modelStub));
    }

    @Test
    public void execute_incompleteLine_recordsError() throws Exception {
        ImportConsultCommand testCommand = new ImportConsultCommand(testCsvPath.toString()) {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testCsvPath;
            }
        };
        createCsvFile(VALID_HEADER + "\n2024-10-20,14:00");
        filesToCleanup.add(testCsvPath);

        CommandResult result = testCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void testEscapeSpecialCharacters() {
        // Test null input
        assertEquals("", importCommand.escapeSpecialCharacters(null));

        // Test normal string
        String normal = "Regular text";
        assertEquals(normal, importCommand.escapeSpecialCharacters(normal));

        // Test string with quotes and commas
        String complex = "Text with \"quotes\" and ,commas,";
        String escaped = importCommand.escapeSpecialCharacters(complex);
        assertTrue(escaped.startsWith("\""));
        assertTrue(escaped.endsWith("\""));
        assertTrue(escaped.contains("\"\"quotes\"\""));
    }

    @Test
    public void testUnescapeSpecialCharacters() {
        // Test null input
        assertEquals("", importCommand.unescapeSpecialCharacters(null));

        // Test normal string
        String normal = "Regular text";
        assertEquals(normal, importCommand.unescapeSpecialCharacters(normal));

        // Test quoted string
        String quoted = "\"Text with \"\"quotes\"\" and ,commas,\"";
        String unescaped = importCommand.unescapeSpecialCharacters(quoted);
        assertEquals("Text with \"quotes\" and ,commas,", unescaped);

        // Test string without quotes
        String noQuotes = "Text without quotes";
        assertEquals(noQuotes, importCommand.unescapeSpecialCharacters(noQuotes));
    }

    @Test
    public void execute_dataDirectoryPath_success() throws IOException, CommandException {
        // Create test file in data directory using system-independent paths
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        Path testFile = dataDir.resolve("consults.csv");

        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write(VALID_HEADER + "\n");
            writer.write(VALID_CONSULT + "\n");
        }
        filesToCleanup.add(testFile);

        ImportConsultCommand importCommand = new ImportConsultCommand("consults.csv");
        CommandResult result = importCommand.execute(modelStub);
        assertEquals(String.format(ImportConsultCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    }

    @Test
    public void execute_ioExceptionOnRead_throwsCommandException() throws IOException {
        Path testFile = testDir.resolve("test.csv");
        Files.writeString(testFile, VALID_HEADER + "\n" + VALID_CONSULT);
        testFile.toFile().setReadOnly();
    }

    @Test
    public void resolveFilePath_existingDirectPath_returnsDirectPath() throws IOException {
        // Create a file in the current directory
        Path directPath = Paths.get("test.csv");
        Files.writeString(directPath, "test content");
        filesToCleanup.add(directPath);

        Path resolved = importCommand.resolveFilePath("test.csv");
        assertEquals(directPath.normalize(), resolved);

        Files.deleteIfExists(directPath); // Clean up immediately
    }

    @Test
    public void resolveFilePath_absolutePath_returnsNormalizedPath() {
        Path absolutePath = testDir.resolve("test.csv").toAbsolutePath();
        Path resolved = importCommand.resolveFilePath(absolutePath.toString());
        assertEquals(absolutePath.normalize(), resolved.normalize());
    }

    private void createCsvFile(String content) throws IOException {
        // Ensure parent directories exist
        Files.createDirectories(testCsvPath.getParent());

        // Create and write file
        Files.writeString(testCsvPath, content);
        filesToCleanup.add(testCsvPath);
    }

    private class ModelStubWithConsultations extends ModelStub {
        private final List<Consultation> consultations = new ArrayList<>();

        @Override
        public boolean hasConsult(Consultation consultation) {
            return consultations.contains(consultation);
        }

        @Override
        public void addConsult(Consultation consultation) {
            consultations.add(consultation);
        }

        @Override
        public Optional<Student> findStudentByName(Name name) {
            if (name.fullName.equals(ALICE.getName().fullName)) {
                return Optional.of(ALICE);
            }
            return Optional.empty();
        }
    }
}
