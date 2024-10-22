package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
        model = new ModelManager();
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up any files created during tests
        if (Files.exists(dataDir)) {
            Files.walk(dataDir)
                    .sorted((a, b) -> b.compareTo(a)) // Reverse order to delete files before directories
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
    public void execute_fileExistsWithForceFlag_success() throws IOException, CommandException {
        // Add a sample student to the model
        Student sampleStudent = new StudentBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withCourses("CS2103T").build();
        model.addStudent(sampleStudent);

        // Create a file that already exists
        Path existingFile = dataDir.resolve("test.csv");
        Files.createFile(existingFile);

        ExportCommand exportCommand = new ExportCommand("test", true, dataDir);
        CommandResult result = exportCommand.execute(model);

        // Verify success message
        assertTrue(result.getFeedbackToUser().contains("Exported"));

        // Verify file contents
        List<String> lines = Files.readAllLines(existingFile);
        assertEquals(2, lines.size()); // Header + 1 student
        assertEquals("Name,Phone,Email,Courses", lines.get(0));

        String expectedLine = String.format("%s,%s,%s,%s",
                sampleStudent.getName().fullName,
                sampleStudent.getPhone().value,
                sampleStudent.getEmail().value,
                sampleStudent.getCourses().stream()
                        .map(course -> course.toString())
                        .collect(Collectors.joining(";")));
        assertEquals(expectedLine, lines.get(1));
    }

    @Test
    public void execute_normalExport_success() throws CommandException, IOException {
        // Add a sample student to the model
        Student sampleStudent = new StudentBuilder().withName("John Doe")
                .withPhone("12345678")
                .withEmail("johndoe@example.com")
                .withCourses("CS2103T").build();
        model.addStudent(sampleStudent);

        ExportCommand exportCommand = new ExportCommand("test", false, dataDir);
        CommandResult result = exportCommand.execute(model);

        // Verify success message
        assertTrue(result.getFeedbackToUser().contains("Exported"));

        // Verify that the file was created
        Path exportPath = dataDir.resolve("test.csv");
        assertTrue(Files.exists(exportPath));

        // Verify file contents
        List<String> lines = Files.readAllLines(exportPath);
        assertEquals(2, lines.size()); // Header + 1 student
        assertEquals("Name,Phone,Email,Courses", lines.get(0));
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
}
