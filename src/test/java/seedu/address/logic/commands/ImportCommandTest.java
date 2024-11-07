package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ImportCommand.CORRECT_HEADER_USAGE;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_READING_ERROR;
import static seedu.address.model.person.Name.MESSAGE_CONSTRAINTS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Email;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleAssignmentsUtil;

/**
 * Contains tests for {@code ImportCommand}.
 */
public class ImportCommandTest {
    private final Model model = new ModelManager(
        getTypicalAddressBook(),
        new UserPrefs(),
        SampleAssignmentsUtil.getSamplePredefinedAssignments());

    /**
     * Tests the execution of a valid import command, expecting success.
     * Verifies that the command result feedback matches the expected success message.
     *
     * @throws CommandException if command execution fails unexpectedly.
     */
    @Test
    public void validImportCommandResult_success() throws CommandException, IOException {
        Path tempFile = Files.createTempFile("validImport", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\""
            + "\n\"Jeck low\",\"jecky@gmail.com\",\"@jeckandjill\",\"\",\"gecky\",\"\",\"\""
            + "\n\"Jick lim\",\"joicky@gmail.com\",\"@jickand\",\"\",\"jikcing\",\"\",\"\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            CommandResult commandResult = command.execute(model);
            assertEquals("Successfully imported 2 person.", commandResult.getFeedbackToUser());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with an invalid CSV header, expecting failure.
     * Verifies that the command fails with the expected error message indicating incorrect header format.
     */
    @Test
    public void invalidCsvHeaderExecution_fail() throws IOException {
        Path tempFile = Files.createTempFile("invalidCsvHeader", ".csv");
        String data = "\"Name\",\"Email\",\"Wrong\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + "Header is defined incorrectly!\n"
                + CORRECT_HEADER_USAGE;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with extra columns in the CSV header, expecting failure.
     * Verifies that the command fails with the expected error message indicating extra columns in the header.
     */
    @Test
    public void extraCsvHeaderCol_fail() throws IOException {
        Path tempFile = Files.createTempFile("extraCsvHeaderCol", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\",\"Extra\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + "There are extra columns!\n"
                + "Please ensure there is only 7 corresponding header/data columns\n" + CORRECT_HEADER_USAGE;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with fewer columns in the CSV header than expected, expecting failure.
     * Verifies that the command fails with the expected error message indicating missing columns in the header.
     */
    @Test
    public void missingCsvHeadersEntry_fail() throws IOException {
        Path tempFile = Files.createTempFile("missingCsvHeadersEntry", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR
                + "There are lesser columns in header than expected!\n" + CORRECT_HEADER_USAGE;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with an empty CSV header, expecting failure.
     * Verifies that the command fails with the expected error message indicating that all headers must be valid.
     */
    @Test
    public void missingCsvHeader_fail() throws IOException {
        Path tempFile = Files.createTempFile("missingCsvHeader", ".csv");
        String data = "\n"
            + "\"Jeck low\",\"jecky@gmail.com\",\"@jeckandjill\",\"\",\"gecky\",\"\",\"\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR
                + "CSV header is empty/contains empty values, please ensure"
                + " all headers are valid.\n"
                + CORRECT_HEADER_USAGE;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with missing data rows, expecting partial success.
     * Verifies that the command result feedback indicates the correct number of persons imported.
     *
     * @throws CommandException if command execution fails unexpectedly.
     */
    @Test
    public void missingDataRow_success() throws CommandException, IOException {
        // Create a temporary CSV file with duplicate person name
        Path tempFile = Files.createTempFile("missingDataRow", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\n"
            + "\"Jeck low\",\"jecky@gmail.com\",\"@jeckandjill\",\"\",\"gecky\",\"\",\"\"";
        Files.writeString(tempFile, data);
        ImportCommand command = new ImportCommand(tempFile.toString());
        try {
            CommandResult commandResult = command.execute(model);
            assertEquals("Successfully imported 1 person.", commandResult.getFeedbackToUser());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with data rows containing only commas, expecting partial success.
     * Verifies that the command result feedback indicates the correct number of persons imported.
     *
     * @throws CommandException if command execution fails unexpectedly.
     */
    @Test
    public void dataRowSomeBlank_success() throws CommandException, IOException {
        // Create a temporary CSV file with duplicate person name
        Path tempFile = Files.createTempFile("dataRowSomeBlank", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + ",,,,,,,,,,,\n"
            + "\"Jeck low\",\"jecky@gmail.com\",\"@jeckandjill\",\"\",\"gecky\",\"\",\"\"";
        Files.writeString(tempFile, data);
        ImportCommand command = new ImportCommand(tempFile.toString());
        try {
            CommandResult commandResult = command.execute(model);
            assertEquals("Successfully imported 1 person.", commandResult.getFeedbackToUser());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with no data rows, expecting failure.
     * Verifies that the command fails with the expected error message indicating no person data is present.
     *
     * @throws CommandException if command execution fails unexpectedly.
     */
    @Test
    public void noDataRow_fail() throws CommandException, IOException {
        Path tempFile = Files.createTempFile("noDataRow", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR
                + "There is no person data present.";
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests the execution of an import command with a CSV file containing duplicate persons,
     * expecting failure. Verifies that the command fails with the expected error message
     * indicating that duplicate persons are present.
     */
    @Test
    public void duplicatePersonExecution_fail() throws IOException {
        // Create a temporary CSV file with duplicate person name
        Path tempFile = Files.createTempFile("duplicatePerson", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"jh\",\"mickey@gmail.com\",\"@hjlim\",\"\",\"g23\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"\n"
            + "\"jh\",\"hwinnee@gmial.com\",\"@jhwoo\",\"\",\"g11\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data);
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + "Operation would result in duplicate persons"
                + "\nPlease ensure that there are no duplicate person in the CSV file";
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid person name.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidPersonNameExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonName", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"inv@l1d\",\"hellokitty@gmail.com\",\"@hj2909\",\"\",\"lfgcode\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + MESSAGE_CONSTRAINTS;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid email entry.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidEmailExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonEmail", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"valid\",\"hellokitty\",\"@hj2909\",\"\",\"lfgcode\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + Email.MESSAGE_CONSTRAINTS;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid telegram entry.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidTelegramExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonEmail", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"valid\",\"hellokitty@gmail.com\",\"hj2909\",\"\",\"lfgcode\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + Telegram.MESSAGE_CONSTRAINTS;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid tag entry.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidTagExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonEmail", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"valid\",\"kitty@gmail.com\",\"@hj2909\",\"[friend\",\"lfgcode\",\"Ex02 | 8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + Tag.MESSAGE_CONSTRAINTS;
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid assignment name entry.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidAssignmentNameExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonEmail", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"valid\",\"hellokitty@gmail.com\",\"@hj2\",\"[friend]\",\"lfgcode\",\"Ex1000|8.0,Ex01|1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + "Invalid assignment name: Ex1000";
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }


    /**
     * Tests that the ImportCommand fails when given a CSV file with an invalid assignment score entry.
     *
     * @throws IOException if an error occurs while handling the temporary file.
     */
    @Test
    public void invalidAssignmentScoreExecution_fail() throws IOException {
        // Create a temporary CSV file with invalid name data
        Path tempFile = Files.createTempFile("invalidPersonEmail", ".csv");
        String data = "\"Name\",\"Email\",\"Telegram\",\"Tags\",\"Github\",\"Assignments\",\"WeeksPresent\"\n"
            + "\"valid\",\"kitty@gmail.com\",\"@hj2909\",\"[friend]\",\"lfgcode\",\"Ex01 | -8.0,Ex01 | 1.0\",\"1,2\"";
        Files.writeString(tempFile, data); // Write content into file
        try {
            ImportCommand command = new ImportCommand(tempFile.toString());
            String expectedMsg = MESSAGE_READING_ERROR + "Score must be between 0.0 and 10.0";
            assertCommandFailure(command, model, expectedMsg);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
