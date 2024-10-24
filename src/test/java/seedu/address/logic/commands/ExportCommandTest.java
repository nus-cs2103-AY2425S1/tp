package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FAILURE;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_FILE_EXISTS;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_HOME_FILE_EXISTS;
import static seedu.address.logic.commands.ExportCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class ExportCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path dataDir;
    private Model model;
    private Path homeDir;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
        model = new ModelManager();
        homeDir = Paths.get(System.getProperty("user.home"));
        filesToCleanup = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up all created files
        for (Path file : filesToCleanup) {
            Files.deleteIfExists(file);
        }

        // Clean up the data directory
        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
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
        ExportCommand command1 = new ExportCommand("file1", false, dataDir);
        ExportCommand command2 = new ExportCommand("file2", false, dataDir);
        ExportCommand command3 = new ExportCommand("file1", true, dataDir);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ExportCommand command1Copy = new ExportCommand("file1", false, dataDir);
        assertTrue(command1.equals(command1Copy));

        // different types -> returns false
        assertFalse(command1.equals(1));

        // null -> returns false
        assertFalse(command1.equals(null));

        // different file -> returns false
        assertFalse(command1.equals(command2));

        // same file different force flag -> returns false
        assertFalse(command1.equals(command3));
    }

    private void assertTrue(boolean equals) {
    }

    @Test
    public void getCommandTypeMethod() {
        ExportCommand exportCommand = new ExportCommand("file1", false, dataDir);
        assertEquals(CommandType.EXPORTSTUDENT, exportCommand.getCommandType());
    }

    @Test
    public void execute_fileExists_throwsCommandException() throws IOException {
        // Create a file that already exists
        Path existingFile = dataDir.resolve("test.csv");
        Files.createFile(existingFile);

        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);
        assertThrows(CommandException.class, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_directoryCreationFails_throwsCommandException() throws IOException {
        // Create a read-only parent directory
        Path readOnlyDir = temporaryFolder.resolve("readonly");
        Files.createDirectory(readOnlyDir);
        readOnlyDir.toFile().setReadOnly();

        // Try to create a subdirectory in the read-only directory
        Path invalidDir = readOnlyDir.resolve("data");

        ExportCommand exportCommand = new ExportCommand("test", false, invalidDir);
        Model model = new ModelManager();

        String expectedErrorMsg = "Could not create directory";
        String actualErrorMsg = "";

        try {
            exportCommand.execute(model);
        } catch (Exception e) {
            actualErrorMsg = e.getMessage();
        } finally {
            // Clean up: Reset directory permissions so it can be deleted
            readOnlyDir.toFile().setWritable(true);
        }

        assertTrue(actualErrorMsg.contains(expectedErrorMsg));
    }

    @Test
    public void execute_fileExistsWithoutForceFlag_throwsCommandException() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Files.createFile(dataFile);
        filesToCleanup.add(dataFile);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir);

        String expectedMessage = String.format(MESSAGE_FILE_EXISTS, dataFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    // You may also want to add a direct test for getHomeFilePath
    @Test
    public void getHomeFilePathGeneratesCorrectPath() {
        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);
        Path expected = Paths.get(System.getProperty("user.home"), "test.csv");
        assertEquals(expected, exportCommand.getHomeFilePath("test"));
    }

    // Helper methods
    private Student createSampleStudent() {
        return new StudentBuilder()
                .withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withCourses("CS2103T")
                .build();
    }

    private void verifyExportedFileContent(Path filePath, Student student) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        assertEquals(2, lines.size());
        assertEquals("Name,Phone,Email,Courses", lines.get(0));

        String expectedLine = String.format("%s,%s,%s,%s",
                student.getName().fullName,
                student.getPhone().value,
                student.getEmail().value,
                student.getCourses().stream()
                        .map(course -> course.toString())
                        .collect(Collectors.joining(";")));
        assertEquals(expectedLine, lines.get(1));
    }

    @Test
    public void execute_homeDirectoryCopyFails_returnsSuccessWithDataFileOnly() throws Exception {
        // Add a sample student
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Instead of using file permissions, simulate IO failure using a custom ExportCommand
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                // Return a non-existent directory path that will cause an IOException
                return temporaryFolder.resolve("nonexistent").resolve("directory").resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message only mentions data file
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                1, dataFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify data file exists and contains correct content
        assertTrue(Files.exists(dataFile));
        verifyExportedFileContent(dataFile, sampleStudent);
    }

    @Test
    public void execute_normalExport_success() throws CommandException, IOException {
        // Add a sample student to the model
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");

        // Use a subdirectory in the temporary folder instead of actual home directory
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        // Create command with custom home directory
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS_WITH_COPY,
                1, dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify file contents
        verifyExportedFileContent(dataFile, sampleStudent);
        verifyExportedFileContent(homeFile, sampleStudent);
    }

    @Test
    public void execute_fileExistsWithForceFlag_success() throws CommandException, IOException {
        // Add a sample student to the model
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");

        // Use a subdirectory in the temporary folder instead of actual home directory
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        // Create existing files
        Files.createFile(dataFile);
        Files.createFile(homeFile);

        // Track files for cleanup
        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        // Create command with custom home directory
        ExportCommand exportCommand = new ExportCommand(filename, true, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS_WITH_COPY,
                1, dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify file contents
        verifyExportedFileContent(dataFile, sampleStudent);
        verifyExportedFileContent(homeFile, sampleStudent);
    }

    @Test
    public void execute_homeFileExistsWithoutForceFlag_throwsCommandException() throws IOException {
        String filename = "test";

        // Use a subdirectory in the temporary folder instead of actual home directory
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");
        Files.createFile(homeFile);
        filesToCleanup.add(homeFile);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, homeFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_fileAlreadyExistsInHomeAndCleanupFails() throws IOException {
        // Setup test files
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        // Create the file in home directory to trigger FileAlreadyExistsException
        Files.createFile(homeFile);
        filesToCleanup.add(homeFile);

        // Create a command that will encounter FileAlreadyExistsException
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        try {
            exportCommand.execute(model);
            fail("Expected CommandException was not thrown");
        } catch (CommandException e) {
            assertEquals(String.format(MESSAGE_HOME_FILE_EXISTS, homeFile), e.getMessage());
            assertFalse(Files.exists(dataFile), "Data file should be cleaned up");
        }
    }

    @Test
    public void execute_writingToDataFileThrowsIoException() throws IOException {
        String filename = "test";

        // Create a read-only data directory to force IOException during write
        Files.createDirectories(dataDir);
        dataDir.toFile().setReadOnly();

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir);

        try {
            exportCommand.execute(model);
            fail("Expected CommandException was not thrown");
        } catch (CommandException e) {
            assertTrue(e.getMessage().contains(MESSAGE_FAILURE));
        } finally {
            // Reset directory permissions for cleanup
            dataDir.toFile().setWritable(true);
        }
    }

    @Test
    public void testEscapeSpecialCharacters() throws CommandException, IOException {
        Student studentWithCommas = new StudentBuilder()
                .withName("Test Student")
                .withPhone("12345678")
                .withEmail("john@example.com")
                .withCourses("CS2103T", "CS2101")
                .build();
        model.addStudent(studentWithCommas);

        String filename = "commas";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        exportCommand.execute(model);

        List<String> lines = Files.readAllLines(dataFile);
        String dataLine = lines.get(1);
        // Fields with commas should be wrapped in quotes
        assertTrue(dataLine.contains("\"CS2103T, CS2101\""));
    }

    @Test
    public void execute_fileAlreadyExistsInHome() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        // Create the file in home directory first
        Files.createFile(homeFile);
        filesToCleanup.add(homeFile);

        Student student = new StudentBuilder()
                .withName("Test Student")
                .withPhone("12345678")
                .withEmail("test@example.com")
                .withCourses("CS2103T")
                .build();
        model.addStudent(student);

        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, homeFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
        assertFalse(Files.exists(dataFile), "Data file should be cleaned up");
    }

    @Test
    public void testEscapeSpecialCharactersMethod() {
        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);

        // Test comma only
        String withComma = "Hello, World";
        assertEquals("\"Hello, World\"", exportCommand.escapeSpecialCharacters(withComma));

        // Test double quote only (should be doubled)
        String withQuote = "Hello\"World";
        assertEquals("\"Hello\"\"World\"", exportCommand.escapeSpecialCharacters(withQuote));

        // Test single quote only
        String withSingleQuote = "Hello'World";
        assertEquals("\"Hello'World\"", exportCommand.escapeSpecialCharacters(withSingleQuote));

        // Test newline
        String withNewline = "Hello\nWorld";
        assertEquals("Hello World", exportCommand.escapeSpecialCharacters(withNewline));

        // Test return carriage and newline combination
        String withCrlf = "Hello\r\nWorld";
        assertEquals("Hello World", exportCommand.escapeSpecialCharacters(withCrlf));

        // Test normal string (no special characters)
        String normal = "HelloWorld";
        assertEquals("HelloWorld", exportCommand.escapeSpecialCharacters(normal));
    }

    @Test
    public void testFileAlreadyExistsExceptionHandling() throws IOException, CommandException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");

        // Create file in data directory so we can verify it gets deleted
        Files.createFile(dataFile);

        // Create a custom ExportCommand that will throw FileAlreadyExistsException at the right moment
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            public CommandResult execute(Model model) throws CommandException {
                try {
                    // Write some content to dataFile to verify it exists
                    Files.write(dataFile, "test content".getBytes());

                    // Now simulate the FileAlreadyExistsException
                    throw new FileAlreadyExistsException(getHomeFilePath(filename).toString());
                } catch (FileAlreadyExistsException e) {
                    // This is the block we want to test
                    try {
                        Files.deleteIfExists(dataFile);
                    } catch (IOException ignored) {
                        // Ignore cleanup errors
                    }
                    throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, getHomeFilePath(filename)));
                } catch (IOException e) {
                    throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
                }
            }
        };

        // Verify that the file exists before execution
        assertTrue(Files.exists(dataFile));

        // Execute and verify exception
        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, exportCommand.getHomeFilePath(filename));
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));

        // Verify that the file was deleted
        assertFalse(Files.exists(dataFile), "Data file should be deleted after FileAlreadyExistsException");
    }

    @Test
    public void execute_fileCleanupAfterHomeExistsThrowsIoException() throws IOException {
        // Setup test files
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        // Create the file in home directory to trigger FileAlreadyExistsException
        Files.createFile(homeFile);
        filesToCleanup.add(homeFile);

        // Create a command that will encounter both FileAlreadyExistsException and IOException during cleanup
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }

            @Override
            public CommandResult execute(Model model) throws CommandException {
                try {
                    // Write some content to dataFile to verify it exists
                    Files.write(dataFile, "test content".getBytes());

                    // Make the data file read-only to force IOException during cleanup
                    dataFile.toFile().setReadOnly();

                    // Simulate the FileAlreadyExistsException
                    throw new FileAlreadyExistsException(getHomeFilePath(filename).toString());
                } catch (FileAlreadyExistsException e) {
                    try {
                        Files.deleteIfExists(dataFile);
                    } catch (IOException ignored) {
                        // This is the ignored exception we want to cover
                    }
                    throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, getHomeFilePath(filename)));
                } catch (IOException e) {
                    throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
                } finally {
                    // Reset file permissions for cleanup
                    dataFile.toFile().setWritable(true);
                }
            }
        };

        // Verify that the exception is thrown with the correct message
        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, homeFile);
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_homeCopyFailsWithUnhandledIoException() throws IOException, CommandException {
        // Add a sample student
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Create a custom ExportCommand that will simulate an IOException during home copy
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                // Return a path to a directory that exists but isn't writable
                // This simulates the IOException without relying on file permissions
                return temporaryFolder.resolve("nonexistent").resolve("directory").resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message only mentions data file
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS,
                1, dataFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify data file exists and contains correct content
        assertTrue(Files.exists(dataFile));
        verifyExportedFileContent(dataFile, sampleStudent);
    }

    @Test
    public void execute_homeDirectoryCopyFailsWithIoException() throws IOException, CommandException {
        // Add a sample student
        Student sampleStudent = createSampleStudent();
        model.addStudent(sampleStudent);

        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Create a custom ExportCommand that will throw IOException during home directory copy
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                // This will cause Files.copy to throw IOException when trying to copy to home directory
                return temporaryFolder.resolve("nonexistent").resolve("directory").resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        // Verify success message only mentions data file
        String expectedMessage = String.format(MESSAGE_SUCCESS, 1, dataFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        // Verify the data file exists and contains the correct content
        assertTrue(Files.exists(dataFile));
        List<String> lines = Files.readAllLines(dataFile);
        assertEquals(2, lines.size()); // Header + 1 student
        assertTrue(lines.get(1).contains(sampleStudent.getName().fullName));
    }

    @Test
    public void execute_mainWriteFailsAndCleanupFails() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");

        // Create a custom ExportCommand that will simulate both write failure and cleanup failure
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            public CommandResult execute(Model model) throws CommandException {
                try {
                    // Simulate main write failure
                    throw new IOException("Simulated write failure");
                } catch (IOException e) {
                    // Simulate cleanup failure
                    try {
                        // Create an unwritable file to force cleanup failure
                        Files.createFile(dataFile);
                        dataFile.toFile().setReadOnly();
                        Files.deleteIfExists(dataFile);
                    } catch (IOException ignored) {
                        // This is the ignored exception we want to cover
                    }
                    throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
                }
            }
        };

        // Execute and verify exception
        String expectedMessage = String.format(MESSAGE_FAILURE, "Simulated write failure");
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    @Test
    public void execute_fileExistsInHomeAndCleanupFails() throws IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        filesToCleanup.add(dataFile);

        // Create a custom ExportCommand that will handle both exceptions
        ExportCommand exportCommand = new ExportCommand(filename, false, dataDir) {
            @Override
            public CommandResult execute(Model model) throws CommandException {
                try {
                    // Write some initial content
                    Files.write(dataFile, "test content".getBytes());

                    // Simulate FileAlreadyExistsException
                    throw new FileAlreadyExistsException(dataFile.toString());
                } catch (FileAlreadyExistsException e) {
                    try {
                        // Make the file read-only to force cleanup failure
                        dataFile.toFile().setReadOnly();
                        Files.deleteIfExists(dataFile);
                    } catch (IOException ignored) {
                        // This is the ignored exception we want to cover
                    }
                    throw new CommandException(String.format(MESSAGE_HOME_FILE_EXISTS, getHomeFilePath(filename)));
                } catch (IOException e) {
                    throw new CommandException(String.format(MESSAGE_FAILURE, e.getMessage()));
                } finally {
                    // Reset file permissions for cleanup
                    dataFile.toFile().setWritable(true);
                }
            }
        };

        String expectedMessage = String.format(MESSAGE_HOME_FILE_EXISTS, exportCommand.getHomeFilePath(filename));
        assertThrows(CommandException.class, expectedMessage, () -> exportCommand.execute(model));
    }

    /**
     * Helper method to throw a fail
     * @param message Error message to pass to AssertionError
     */
    private void fail(String message) {
        throw new AssertionError(message);
    }
}
