package seedu.address.logic.commands.consultation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Path tempDir;

    private ImportConsultCommand importCommand;
    private Path testCsvPath;
    private ModelStubWithConsultations modelStub;

    @BeforeEach
    public void setUp() throws IOException {
        testCsvPath = tempDir.resolve("test.csv");
        importCommand = new ImportConsultCommand(testCsvPath.toString());
        modelStub = new ModelStubWithConsultations();
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
        createCsvFile("");
        assertThrows(CommandException.class,
                ImportConsultCommand.MESSAGE_EMPTY_FILE, () -> importCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidHeader_throwsCommandException() throws IOException {
        createCsvFile("Invalid,Header,Format\n" + VALID_CONSULT);
        assertThrows(CommandException.class,
                ImportConsultCommand.MESSAGE_INVALID_HEADER, () -> importCommand.execute(modelStub));
    }

    @Test
    public void execute_validConsultation_success() throws IOException, CommandException {
        createCsvFile(VALID_HEADER + "\n" + VALID_CONSULT);
        CommandResult result = importCommand.execute(modelStub);
        assertEquals(String.format(ImportConsultCommand.MESSAGE_SUCCESS, 1, 0), result.getFeedbackToUser());
        assertEquals(1, modelStub.consultations.size());
    }

    @Test
    public void execute_invalidDate_recordsError() throws IOException, CommandException {
        createCsvFile(VALID_HEADER + "\n" + INVALID_DATE);
        CommandResult result = importCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_invalidTime_recordsError() throws IOException, CommandException {
        createCsvFile(VALID_HEADER + "\n" + INVALID_TIME);
        CommandResult result = importCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_nonexistentStudent_recordsError() throws IOException, CommandException {
        createCsvFile(VALID_HEADER + "\n" + NONEXISTENT_STUDENT);
        CommandResult result = importCommand.execute(modelStub);
        assertTrue(result.getFeedbackToUser().contains("error.csv"));
        assertEquals(0, modelStub.consultations.size());
    }

    @Test
    public void execute_duplicateConsultation_recordsError() throws IOException, CommandException {
        // Create a consultation with the same date and time as our test constants
        Consultation existingConsult = new ConsultationBuilder()
                .withDate("2024-10-20")
                .withTime("14:00")
                .withStudent(ALICE)
                .build();
        modelStub.addConsult(existingConsult);

        // Try to import the same consultation
        createCsvFile(VALID_HEADER + "\n" + VALID_CONSULT);

        CommandResult result = importCommand.execute(modelStub);
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
