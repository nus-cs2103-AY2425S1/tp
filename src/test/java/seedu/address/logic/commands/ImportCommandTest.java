package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

public class ImportCommandTest {

    private static final String VALID_CSV_HEADERS = "Name,Phone Number,Email Address,Address,Tags";
    private static final String VALID_PERSON_ENTRY = "\"John Doe\",\"98765432\",\"johnd@example.com\",\"311, Clementi Ave 2, #02-25\",\"friends\"";
    private static final String INVALID_PERSON_ENTRY = "\"John Doe\",\"98765432\""; // Incomplete person entry
    private static final String TEST_DIRECTORY = "./data";

    private Model model;
    private Model expectedModel;
    private Path validFilePath;
    private Path invalidFilePath;
    private Path nonExistentFilePath;

    @BeforeEach
    public void setUp() throws IOException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Create paths for valid and invalid CSV files
        validFilePath = Paths.get(TEST_DIRECTORY, "ValidImportContacts.csv");
        invalidFilePath = Paths.get(TEST_DIRECTORY, "InvalidImportContacts.csv");
        nonExistentFilePath = Paths.get(TEST_DIRECTORY, "NonExistentFile.csv");

        // Ensure the directory exists
        Files.createDirectories(validFilePath.getParent());

        // Write valid CSV content
        try (BufferedWriter writer = Files.newBufferedWriter(validFilePath)) {
            writer.write(VALID_CSV_HEADERS);
            writer.newLine();
            writer.write(VALID_PERSON_ENTRY);
        }

        // Write invalid CSV content
        try (BufferedWriter writer = Files.newBufferedWriter(invalidFilePath)) {
            writer.write(VALID_CSV_HEADERS);
            writer.newLine();
            writer.write(INVALID_PERSON_ENTRY);
        }
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up the files after each test
        if (Files.exists(validFilePath)) {
            Files.delete(validFilePath);
        }
        if (Files.exists(invalidFilePath)) {
            Files.delete(invalidFilePath);
        }
    }

    @Test
    public void execute_fileDoesNotExist_throwsCommandException() {
        ImportCommand importCommand = new ImportCommand("NonExistentFile.csv");
        try {
            importCommand.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().contains(ImportCommand.MESSAGE_FILE_DOES_NOT_EXIST));
        }
    }

    @Test
    public void execute_invalidFileFormat_throwsCommandException() {
        ImportCommand importCommand = new ImportCommand("InvalidImportContacts.csv");
        try {
            importCommand.execute(model);
        } catch (CommandException e) {
            assertTrue(e.getMessage().contains(ImportCommand.MESSAGE_INCORRECT_FILE_FORMAT));
        }
    }

    @Test
    public void execute_validFile_importSuccess() throws CommandException {
        ImportCommand importCommand = new ImportCommand("ValidImportContacts.csv");
        Name name = new Name("John Doe");
        Phone phone = new Phone("98765432");
        Email email = new Email("johnd@example.com");
        Address address = new Address("311, Clementi Ave 2, #02-25");
        Tag tag = new Tag("friends");
        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag);
        Person person = new Person(name, phone, email, address, tagSet);
        expectedModel.addPerson(person);
        // Ensure success of import
        assertCommandSuccess(importCommand, model, String.format(ImportCommand.MESSAGE_SUCCESS, "ValidImportContacts.csv"), expectedModel);
    }
}
