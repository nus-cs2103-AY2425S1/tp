package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.company.Industry;
import seedu.address.model.person.student.Student;
import seedu.address.model.person.student.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Represents a command to import contacts from a CSV file into the address book.
 * Supports adding both student and company contacts.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Contacts imported successfully from: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to import contacts from: %1$s.\n"
            + "Please ensure the file path is in the correct format:\n"
            + "   - For absolute paths, start with '/' or provide the full path, e.g., '/full/path/to/file.csv'.\n"
            + "   - For relative paths, do not start with '/', e.g., 'data/file.csv' for paths relative to the current "
            + "directory.\n"
            + "Note: Only .csv files are supported.";

    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category in CSV. Category must be either 'student' "
            + "or 'company'";
    public static final String MESSAGE_INVALID_CSV_FORMAT = "Invalid CSV format";
    public static final String MESSAGE_NON_CSV_FILE = "The file extension must be .csv";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from a CSV file.\n"
            + "Parameters: FILE_PATH\n"
            + "Example:\n"
            + "   - For absolute paths: '/full/path/to/file.csv'\n"
            + "   - For relative paths: 'data/file.csv' (relative to the current directory).\n"
            + "Note: Only files with a .csv extension are supported.";



    private final Path filePath;

    /**
     * Constructs an {@code ImportCommand} with the specified file path.
     *
     * @param filePath The path of the CSV file from which to import contacts.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        // Set the file path without validation here
        this.filePath = Paths.get(filePath);
    }

    /**
     * Executes the ImportCommand and validates the file path.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            // Attempt to validate the file path. If it fails, an IllegalArgumentException will be thrown
            validateFilePath(filePath.toString());
        } catch (IllegalArgumentException e) {
            // Catch the IllegalArgumentException and wrap it in a CommandException to display in the UI
            throw new CommandException(String.format(MESSAGE_FAILURE, filePath.toString()) + "\n" + e.getMessage());
        }

        int successCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line = br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                List<String> values = parseCsvLine(line);

                if (values.size() < 6 || values.size() > 7) { // Ensure exactly 6 or 7 fields per line
                    throw new CommandException(MESSAGE_INVALID_CSV_FORMAT);
                }

                successCount += processCsvLine(values, model);
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath.toString())
                    + "\nSuccessfully imported: " + successCount + " entries");

        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, filePath.toString()));
        }
    }

    /**
     * Validates the provided file path.
     * Throws IllegalArgumentException if validation fails.
     */
    private void validateFilePath(String filePath) {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException("The file extension must be .csv");
        }

        if (System.getProperty("os.name").startsWith("Windows") && (filePath.startsWith("/")
                || filePath.startsWith("\\"))) {
            throw new IllegalArgumentException("Invalid file path format for Windows: " + filePath);
        }

        if (filePath.matches(".*[<>\"\\|?*].*")) {
            throw new IllegalArgumentException("Invalid file path provided: " + filePath);
        }
    }

    /**
     * Processes a single line from the CSV file, attempting to add the contact to the model.
     */
    private int processCsvLine(List<String> values, Model model) throws CommandException {
        String category = values.get(1).trim().toLowerCase();
        switch (category) {
        case "student":
            return addStudent(values, model);
        case "company":
            return addCompany(values, model);
        default:
            throw new CommandException(MESSAGE_INVALID_CATEGORY);
        }
    }

    /**
     * Adds a student contact to the model based on parsed CSV values.
     *
     * @param values The list of parsed CSV values.
     * @param model The model in which contacts are to be added.
     * @return 1 if the student contact is added successfully; 0 if it already exists.
     * @throws CommandException If the student ID is missing or invalid.
     */
    private int addStudent(List<String> values, Model model) throws CommandException {
        String studentId = values.get(2).trim();
        if (studentId.isEmpty()) {
            throw new CommandException("Missing Student ID for student category");
        }
        Student student = new Student(
                new Name(values.get(0).trim()),
                new StudentId(studentId),
                new Phone(values.get(3).trim()),
                new Email(values.get(4).trim()),
                new Address(values.get(5).trim()),
                parseTags(values.size() > 6 ? values.get(6).trim() : "")
        );
        if (!model.hasPerson(student)) {
            new AddStudentCommand(student).execute(model);
            return 1;
        }
        return 0;
    }

    /**
     * Adds a company contact to the model based on parsed CSV values.
     *
     * @param values The list of parsed CSV values.
     * @param model The model in which contacts are to be added.
     * @return 1 if the company contact is added successfully; 0 if it already exists.
     * @throws CommandException If the industry is missing or invalid.
     */
    private int addCompany(List<String> values, Model model) throws CommandException {
        String industry = values.get(2).trim();
        if (industry.isEmpty()) {
            throw new CommandException("Missing Industry for company category");
        }
        Company company = new Company(
                new Name(values.get(0).trim()),
                new Industry(industry),
                new Phone(values.get(3).trim()),
                new Email(values.get(4).trim()),
                new Address(values.get(5).trim()),
                parseTags(values.size() > 6 ? values.get(6).trim() : "")
        );
        if (!model.hasPerson(company)) {
            new AddCompanyCommand(company).execute(model);
            return 1;
        }
        return 0;
    }

    /**
     * Returns a success message indicating the number of contacts successfully imported.
     *
     * @param successCount The number of contacts imported.
     * @return The success message string.
     */
    private String getSuccessMessage(int successCount) {
        return String.format(MESSAGE_SUCCESS, filePath.toString()) + "\nSuccessfully imported: "
                + successCount + " entries";
    }

    /**
     * Parses a single CSV line into a list of values, accounting for quoted fields with commas.
     *
     * @param line The CSV line to parse.
     * @return A list of parsed values.
     */
    private List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;

        for (char c : line.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            } else if (c == ',' && !inQuotes) {
                result.add(currentField.toString().trim());
                currentField.setLength(0); // Reset for the next field
            } else {
                currentField.append(c);
            }
        }
        result.add(currentField.toString().trim()); // Add the last field
        return result;
    }

    /**
     * Parses a space-separated string of tags into a {@code Set<Tag>}.
     *
     * @param tagsString The string containing tags, separated by spaces.
     * @return A {@code Set<Tag>} containing the parsed tags.
     */
    private Set<Tag> parseTags(String tagsString) {
        Set<Tag> tags = new HashSet<>();
        if (!tagsString.isEmpty()) {
            String[] tagsArray = tagsString.split("\\s+"); // Split tags by spaces
            for (String tagName : tagsArray) {
                tags.add(new Tag(tagName));
            }
        }
        return tags;
    }

    /**
     * Checks if two ImportCommand objects are equal. Two ImportCommand objects are considered equal
     * if they have the same file path.
     *
     * @param other The other object to compare with.
     * @return true if both objects have the same file path; false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ImportCommand)) {
            return false;
        }

        ImportCommand otherCommand = (ImportCommand) other;
        return filePath.equals(otherCommand.filePath);
    }

    /**
     * Returns the hash code for this ImportCommand based on the file path.
     *
     * @return The hash code for this ImportCommand.
     */
    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
