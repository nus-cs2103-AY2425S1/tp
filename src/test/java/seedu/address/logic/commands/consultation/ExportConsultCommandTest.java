package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalConsultations.getTypicalConsultations;

import java.io.IOException;
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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.consultation.Consultation;
import seedu.address.testutil.ModelStub;

public class ExportConsultCommandTest {

    @TempDir
    public Path temporaryFolder;

    private Path dataDir;
    private ModelStubWithConsultations model;
    private Path homeDir;
    private List<Path> filesToCleanup;

    @BeforeEach
    public void setUp() throws IOException {
        dataDir = temporaryFolder.resolve("data");
        Files.createDirectories(dataDir);
        model = new ModelStubWithConsultations(getTypicalConsultations());
        homeDir = Paths.get(System.getProperty("user.home"));
        filesToCleanup = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() throws IOException {
        for (Path file : filesToCleanup) {
            Files.deleteIfExists(file);
        }

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
        ExportConsultCommand command1 = new ExportConsultCommand("file1", false, dataDir);
        ExportConsultCommand command2 = new ExportConsultCommand("file2", false, dataDir);
        ExportConsultCommand command3 = new ExportConsultCommand("file1", true, dataDir);

        // same object -> returns true
        assertTrue(command1.equals(command1));

        // same values -> returns true
        ExportConsultCommand command1Copy = new ExportConsultCommand("file1", false, dataDir);
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

    @Test
    public void getCommandType() {
        ExportConsultCommand exportCommand = new ExportConsultCommand("file1", false, dataDir);
        assertEquals(CommandType.EXPORTCONSULT, exportCommand.getCommandType());
    }

    @Test
    public void execute_normalExport_success() throws CommandException, IOException {
        String filename = "test";
        Path dataFile = dataDir.resolve(filename + ".csv");
        Path testHomeDir = temporaryFolder.resolve("home");
        Files.createDirectories(testHomeDir);
        Path homeFile = testHomeDir.resolve(filename + ".csv");

        filesToCleanup.add(dataFile);
        filesToCleanup.add(homeFile);

        ExportConsultCommand exportCommand = new ExportConsultCommand(filename, false, dataDir) {
            @Override
            protected Path getHomeFilePath(String filename) {
                return testHomeDir.resolve(filename + ".csv");
            }
        };

        CommandResult result = exportCommand.execute(model);

        String expectedMessage = String.format(ExportConsultCommand.MESSAGE_SUCCESS_WITH_COPY,
                getTypicalConsultations().size(), dataFile, homeFile);
        assertEquals(expectedMessage, result.getFeedbackToUser());

        verifyExportedFileContent(dataFile);
        verifyExportedFileContent(homeFile);
    }

    private void verifyExportedFileContent(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        assertEquals("Date,Time,Students", lines.get(0));

        List<String> expectedLines = getTypicalConsultations().stream()
                .map(this::consultationToCsvLine)
                .collect(Collectors.toList());

        // Skip header line when comparing
        List<String> actualLines = lines.subList(1, lines.size());
        assertEquals(expectedLines, actualLines);
    }

    private String consultationToCsvLine(Consultation consultation) {
        String studentsString = consultation.getStudents().stream()
                .map(student -> student.getName().fullName)
                .collect(Collectors.joining(";"));
        return String.format("%s,%s,%s",
                consultation.getDate().getValue(),
                consultation.getTime().getValue(),
                studentsString);
    }

    private class ModelStubWithConsultations extends ModelStub {
        private final List<Consultation> consultations;

        ModelStubWithConsultations(List<Consultation> consultations) {
            this.consultations = new ArrayList<>(consultations);
        }

        @Override
        public ObservableList<Consultation> getFilteredConsultationList() {
            return FXCollections.observableList(consultations);
        }
    }
}
