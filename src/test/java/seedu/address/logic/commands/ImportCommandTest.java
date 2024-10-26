package seedu.address.logic.commands;

//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_EMPTY_FILE;
//import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_FILE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_HEADER;
import static seedu.address.testutil.Assert.assertThrows;

//import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
//import seedu.address.model.student.Student;
//import seedu.address.testutil.StudentBuilder;

public class ImportCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path testDir;
    private Model model;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        // Setup test directory one level up from temporary folder to simulate project structure
        testDir = temporaryFolder.getParent().resolve("testData");
        Files.createDirectories(testDir);
        model = new ModelManager();
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
        ImportCommand command1 = new ImportCommand("file1.csv");
        ImportCommand command2 = new ImportCommand("file2.csv");

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ImportCommand command1Copy = new ImportCommand("file1.csv");
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

        ImportCommand importCommand = new ImportCommand(emptyFile.toString());
        assertThrows(CommandException.class, MESSAGE_EMPTY_FILE, () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_invalidHeader_throwsCommandException() throws IOException {
        Path invalidFile = testDir.resolve("invalid.csv");
        Files.write(invalidFile, "Wrong,Header,Format\n".getBytes());
        filesToCleanup.add(invalidFile);

        ImportCommand importCommand = new ImportCommand(invalidFile.toString());
        assertThrows(CommandException.class, MESSAGE_INVALID_HEADER, () ->
                importCommand.execute(model));
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        ImportCommand importCommand = new ImportCommand("nonexistent.csv");
        assertThrows(CommandException.class, () -> importCommand.execute(model));
    }

    //    @Test
    //    public void execute_validImport_success() throws IOException, CommandException {
    //        // Create sample file with valid student data
    //        Path validFile = testDir.resolve("valid.csv");
    //        Student sampleStudent = new StudentBuilder()
    //                .withName("John Doe")
    //                .withPhone("12345678")
    //                .withEmail("john@example.com")
    //                .withCourses("CS2103T")
    //                .build();
    //
    //        try (FileWriter writer = new FileWriter(validFile.toFile())) {
    //            writer.write("Name,Phone,Email,Courses\n");
    //            writer.write(String.format("%s,%s,%s,%s\n",
    //                    sampleStudent.getName().fullName,
    //                    sampleStudent.getPhone().value,
    //                    sampleStudent.getEmail().value,
    //                    "CS2103T"));
    //        }
    //        filesToCleanup.add(validFile);
    //
    //        ImportCommand importCommand = new ImportCommand(validFile.getFileName().toString());
    //        CommandResult result = importCommand.execute(model);
    //
    //        // Verify success message
    //        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0),
    //                result.getFeedbackToUser());
    //
    //        // Verify student was added
    //        assertTrue(model.hasStudent(sampleStudent));
    //    }

    //    @Test
    //    public void execute_validFileWithMixedResults_createsErrorFile() throws IOException, CommandException {
    //        Path validFile = testDir.resolve("mixed.csv");
    //        try (FileWriter writer = new FileWriter(validFile.toFile())) {
    //            writer.write("Name,Phone,Email,Courses\n");
    //            // Valid entry
    //            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
    //            // Invalid phone
    //            writer.write("Jane Doe,123,jane@example.com,CS2103T\n");
    //            // Invalid email
    //            writer.write("Bob Smith,12345678,invalid-email,CS2103T\n");
    //            // Duplicate student (assuming John Doe was added successfully)
    //            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
    //        }
    //        filesToCleanup.add(validFile);
    //
    //        ImportCommand importCommand = new ImportCommand(validFile.getFileName().toString());
    //        CommandResult result = importCommand.execute(model);
    //
    //        // Verify error file was created
    //        Path errorFile = validFile.resolveSibling("error.csv");
    //        assertTrue(Files.exists(errorFile));
    //        filesToCleanup.add(errorFile);
    //
    //        // Verify error file contents
    //        List<String> errorLines = Files.readAllLines(errorFile);
    //        assertTrue(errorLines.size() > 1); // Header + at least one error entry
    //        assertEquals("Original Entry,Error Message", errorLines.get(0));
    //        assertTrue(errorLines.stream().anyMatch(line -> line.contains("invalid phone number")));
    //        assertTrue(errorLines.stream().anyMatch(line -> line.contains("invalid email")));
    //        assertTrue(errorLines.stream().anyMatch(line -> line.contains("Duplicate student")));
    //
    //        // Verify success message shows correct counts
    //        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS_WITH_ERRORS, 1, 3, errorFile),
    //                result.getFeedbackToUser());
    //    }

    //    @Test
    //    public void execute_validFileWithQuotesAndCommas() throws IOException, CommandException {
    //        Path validFile = testDir.resolve("quoted.csv");
    //        try (FileWriter writer = new FileWriter(validFile.toFile())) {
    //            writer.write("Name,Phone,Email,Courses\n");
    //            writer.write("\"Doe, John\",12345678,john@example.com,\"CS2103T;CS2101\"\n");
    //            writer.write("\"Smith, Jane\",87654321,jane@example.com,CS2103T\n");
    //        }
    //        filesToCleanup.add(validFile);
    //
    //        ImportCommand importCommand = new ImportCommand(validFile.getFileName().toString());
    //        CommandResult result = importCommand.execute(model);
    //
    //        // Verify students were added with correct names including commas
    //        assertTrue(model.getFilteredStudentList().stream()
    //                .anyMatch(s -> s.getName().fullName.equals("Doe, John")));
    //        assertTrue(model.getFilteredStudentList().stream()
    //                .anyMatch(s -> s.getName().fullName.equals("Smith, Jane")));
    //    }

    //    @Test
    //    public void execute_homeDirectoryPath() throws IOException, CommandException {
    //        // Create test file in a subdirectory of temp folder (simulating home directory)
    //        Path homeDir = temporaryFolder.resolve("home");
    //        Files.createDirectories(homeDir);
    //        Path testFile = homeDir.resolve("students.csv");
    //
    //        try (FileWriter writer = new FileWriter(testFile.toFile())) {
    //            writer.write("Name,Phone,Email,Courses\n");
    //            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
    //        }
    //        filesToCleanup.add(testFile);
    //
    //        // Create command with modified home path resolution for testing
    //        ImportCommand importCommand = new ImportCommand("~/students.csv") {
    //            @Override
    //            protected Path resolveFilePath(String filepath) {
    //                if (filepath.startsWith("~")) {
    //                    return homeDir.resolve(filepath.substring(2));
    //                }
    //                return super.resolveFilePath(filepath);
    //            }
    //        };
    //
    //        CommandResult result = importCommand.execute(model);
    //        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    //    }

    //    @Test
    //    public void execute_parentDirectoryPath() throws IOException, CommandException {
    //        // Create test file in parent directory
    //        Path parentDir = testDir.getParent();
    //        Path testFile = parentDir.resolve("students.csv");
    //
    //        try (FileWriter writer = new FileWriter(testFile.toFile())) {
    //            writer.write("Name,Phone,Email,Courses\n");
    //            writer.write("John Doe,12345678,john@example.com,CS2103T\n");
    //        }
    //        filesToCleanup.add(testFile);
    //
    //        ImportCommand importCommand = new ImportCommand("students.csv");
    //        CommandResult result = importCommand.execute(model);
    //        assertEquals(String.format(ImportCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
    //    }
}
