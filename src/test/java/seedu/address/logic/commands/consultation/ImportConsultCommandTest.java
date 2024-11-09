package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;

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
        // Create project directory inside temp folder
        projectDir = temporaryFolder.resolve("project");
        Files.createDirectories(projectDir);

        // Create test directory as sibling to project dir
        testDir = temporaryFolder.resolve("test-files");
        Files.createDirectories(testDir);

        testCsvPath = testDir.resolve("test.csv");
        importCommand = new ImportConsultCommand(testCsvPath.toString());
        modelStub = new ModelStubWithConsultations();
        filesToCleanup = new ArrayList<>();
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
                ImportConsultCommand.MESSAGE_EMPTY_FILE, () -> testCommand.execute(modelStub));
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
        String homeFilePath = "~/test.csv";
        Path expected = Paths.get(System.getProperty("user.home"), "test.csv");
        Path actual = importCommand.resolveFilePath(homeFilePath);
        assertEquals(expected, actual);
    }

    private void createCsvFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(testCsvPath.toFile())) {
            writer.write(content);
        }
    }

    @Test
    public void execute_dataDirectoryPath_success() throws IOException, CommandException {
        // Create test file in data directory
        Path dataDir = Paths.get("data");
        Files.createDirectories(dataDir);
        Path testFile = dataDir.resolve("consults.csv");

        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write(VALID_HEADER + "\n");
            writer.write(VALID_CONSULT + "\n");
        }
        ImportConsultCommand importCommand = new ImportConsultCommand("consults.csv");
        CommandResult result = importCommand.execute(modelStub);
        assertEquals(String.format(ImportConsultCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
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
