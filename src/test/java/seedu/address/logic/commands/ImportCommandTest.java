package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_EMPTY_FILE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_HEADER;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.testutil.StudentBuilder;

public class ImportCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path testDir;
    private Path projectDir;
    private Model model;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        // Create project directory inside temp folder
        projectDir = temporaryFolder.resolve("project");
        Files.createDirectories(projectDir);

        // Create test directory as sibling to project dir
        testDir = temporaryFolder.resolve("test-files");
        Files.createDirectories(testDir);

        model = new ModelManager();
        filesToCleanup = new ArrayList<>();

        // Create new addressbook.json for testing within project directory
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(projectDir.resolve("addressbook.json"));
        addressBookStorage.saveAddressBook(model.getAddressBook());
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
    public void getCommandTypeMethod() {
        ImportCommand importCommand = new ImportCommand("file1.csv");
        assertEquals(CommandType.IMPORTSTUDENT, importCommand.getCommandType());
    }

    @Test
    public void equals() {
        ImportCommand command1 = new ImportCommand("file1.csv");
        ImportCommand command2 = new ImportCommand("file2.csv");
        ImportCommand command1Copy = new ImportCommand("file1.csv");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
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
        Path emptyFile = testDir.resolve("empty.csv");
        Files.createFile(emptyFile);
        filesToCleanup.add(emptyFile);

        ImportCommand importCommand = new ImportCommand("empty.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        assertThrows(CommandException.class, MESSAGE_EMPTY_FILE, () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_invalidHeader_throwsCommandException() throws IOException {
        Path invalidFile = testDir.resolve("invalid.csv");
        Files.write(invalidFile, "Wrong,Header,Format\n".getBytes());
        filesToCleanup.add(invalidFile);

        ImportCommand importCommand = new ImportCommand("invalid.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        assertThrows(CommandException.class, MESSAGE_INVALID_HEADER, () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        ImportCommand importCommand = new ImportCommand("nonexistent.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };
        assertThrows(CommandException.class, () -> importCommand.execute(model));
    }

    @Test
    public void execute_validImport_success() throws IOException, CommandException {
        // Create sample file with valid student data
        Path validFile = testDir.resolve("valid.csv");
        Student sampleStudent = new StudentBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .withCourses("CS2103T")
                .build();

        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write(String.format("%s,%s,%s,%s\n",
                    sampleStudent.getName().fullName,
                    sampleStudent.getPhone().value,
                    sampleStudent.getEmail().value,
                    "CS2103T"));
        }
        filesToCleanup.add(validFile);

        // Create command that treats testDir as parent directory
        ImportCommand importCommand = new ImportCommand("valid.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                if (!Paths.get(filepath).isAbsolute()) {
                    return testDir.resolve(filepath);
                }
                return super.resolveFilePath(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);

        // Verify success message
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0),
                result.getFeedbackToUser());

        // Verify student was added
        assertTrue(model.hasStudent(sampleStudent));
    }

    @Test
    public void execute_validFileWithMixedResults_createsErrorFile() throws IOException, CommandException {
        Path validFile = testDir.resolve("mixed.csv");
        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            // Valid entry
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
            // Invalid phone
            writer.write("Jane Doe,123,jane@example.com,CS2103T\n");
            // Invalid email
            writer.write("Bob Smith,12345678,invalid-email,CS2103T\n");
            // Duplicate student (assuming John Doe was added successfully)
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
        }
        filesToCleanup.add(validFile);

        ImportCommand importCommand = new ImportCommand("mixed.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);

        // Verify error file was created
        Path errorFile = validFile.resolveSibling("error.csv");
        assertTrue(Files.exists(errorFile));
        filesToCleanup.add(errorFile);

        // Verify error file contents
        List<String> errorLines = Files.readAllLines(errorFile);
        assertTrue(errorLines.size() > 1);
        assertEquals("Original Entry,Error Message", errorLines.get(0));
    }

    @Test
    public void execute_validFileWithQuotesAndCommas() throws IOException, CommandException {
        Path validFile = testDir.resolve("quoted.csv");
        try (FileWriter writer = new FileWriter(validFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("\"Doe, John\",12345678,john@example.com,\"CS2103T;CS2101\"\n");
            writer.write("\"Smith, Jane\",87654321,jane@example.com,CS2103T\n");
        }
        filesToCleanup.add(validFile);

        ImportCommand importCommand = new ImportCommand("quoted.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);

        // Verify students were added with correct names including commas
        assertTrue(model.getFilteredStudentList().stream()
                .anyMatch(s -> s.getName().fullName.equals("Doe, John")));
        assertTrue(model.getFilteredStudentList().stream()
                .anyMatch(s -> s.getName().fullName.equals("Smith, Jane")));
    }

    @Test
    public void execute_homeDirectoryPath() throws IOException, CommandException {
        // Create test file in a subdirectory of temp folder (simulating home directory)
        Path homeDir = temporaryFolder.resolve("home");
        Files.createDirectories(homeDir);
        Path testFile = homeDir.resolve("students.csv");

        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
        }
        filesToCleanup.add(testFile);

        ImportCommand importCommand = new ImportCommand("~/students.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                if (filepath.startsWith("~")) {
                    return homeDir.resolve(filepath.substring(2));
                }
                return testDir.resolve(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    }

    @Test
    public void execute_parentDirectoryPath() throws IOException, CommandException {
        // Create test file in test directory
        Path testFile = testDir.resolve("students.csv");
        try (FileWriter writer = new FileWriter(testFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
        }
        filesToCleanup.add(testFile);

        // Create command that simulates running from project directory
        ImportCommand importCommand = new ImportCommand("students.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                // Simulate the behavior as if we're in the project directory
                if (!Paths.get(filepath).isAbsolute()) {
                    return testDir.resolve(filepath);
                }
                return super.resolveFilePath(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    }

    @Test
    public void execute_absolutePath() throws IOException, CommandException {
        // Create file with absolute path
        Path absolutePath = testDir.toAbsolutePath().resolve("absolute.csv");
        try (FileWriter writer = new FileWriter(absolutePath.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
        }
        filesToCleanup.add(absolutePath);

        ImportCommand importCommand = new ImportCommand(absolutePath.toString());
        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    }

    @Test
    public void execute_incompleteEntry() throws Exception {
        Path invalidFile = testDir.resolve("incomplete.csv");
        try (FileWriter writer = new FileWriter(invalidFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            // Missing courses field
            writer.write("John Doe,12345678,john@example.com\n");
        }
        filesToCleanup.add(invalidFile);

        ImportCommand importCommand = new ImportCommand("incomplete.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);

        // Verify error file contents
        Path errorFile = invalidFile.resolveSibling("error.csv");
        assertTrue(Files.exists(errorFile));
        filesToCleanup.add(errorFile);
        List<String> errorLines = Files.readAllLines(errorFile);
        assertTrue(errorLines.stream()
                .anyMatch(line -> line.contains("Incomplete student entry")));
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS_WITH_ERRORS, 0, 1, errorFile),
                result.getFeedbackToUser());
    }

    @Test
    public void testEscapeSpecialCharactersWithNull() {
        ImportCommand importCommand = new ImportCommand("test.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        // Make escapeSpecialCharacters and unescapeSpecialCharacters public for testing
        String escapedNull = importCommand.escapeSpecialCharacters(null);
        assertEquals("", escapedNull);
    }

    @Test
    public void testUnescapeSpecialCharactersWithNull() {
        ImportCommand importCommand = new ImportCommand("test.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        String unescapedNull = importCommand.unescapeSpecialCharacters(null);
        assertEquals("", unescapedNull);
    }

    @Test
    public void execute_fileWithOnlyEmptyLines_throwsCommandException() throws IOException {
        Path emptyLinesFile = testDir.resolve("emptyLines.csv");
        try (FileWriter writer = new FileWriter(emptyLinesFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("   \n");
            writer.write("\n");
            writer.write("\t\n");
        }
        filesToCleanup.add(emptyLinesFile);

        ImportCommand importCommand = new ImportCommand("emptyLines.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        assertThrows(CommandException.class, MESSAGE_EMPTY_FILE, () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_fileWithEmptyLinesAndValidEntry_success() throws IOException, CommandException {
        Path mixedFile = testDir.resolve("mixed.csv");
        try (FileWriter writer = new FileWriter(mixedFile.toFile())) {
            writer.write("Name,Phone,Email,Courses\n");
            writer.write("\n");
            writer.write("   \n");
            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
            writer.write("\t\n");
            writer.write("\n");
        }
        filesToCleanup.add(mixedFile);

        ImportCommand importCommand = new ImportCommand("mixed.csv") {
            @Override
            protected Path resolveFilePath(String filepath) {
                return testDir.resolve(filepath);
            }
        };

        CommandResult result = importCommand.execute(model);
        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    }
}
