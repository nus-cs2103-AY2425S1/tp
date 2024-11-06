package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;
import seedu.address.model.tag.Tag;

/**
 * Exports all contacts in the address book to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_SUCCESS = "Contacts exported successfully to: %1$s";
    public static final String MESSAGE_FAILURE = "Error: Failed to export contacts. Please check the file path:\n\n"
            + "- Use an absolute path (e.g., C:\\Users\\username\\Documents\\file.csv or "
            + "/home/username/file.csv) or a relative path.\n"
            + "- Ensure the path ends with '.csv' and has write permissions.\n"
            + "- Avoid restricted characters in the path.\n\n"
            + "Correct the file path and try again.";

    public static final String MESSAGE_USAGE = "Invalid file path provided: %1$s\n"
            + "Ensure the file path:\n"
            + "- Is a valid absolute or relative path.\n"
            + "- Ends with '.csv'.\n"
            + "- Does not contain restricted characters.";
    // Message for invalid file extensions
    public static final String MESSAGE_INVALID_FILE_EXT = "The file path must end with '.csv' to ensure a proper export"
            + "format.";

    private final Path filePath;

    /**
     * Creates an ExportCommand to export contacts to the specified file path.
     *
     * @param filePath The file path to which contacts will be exported.
     */
    public ExportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = validateFilePath(filePath);
    }

    /**
     * Validates the file path to ensure it conforms to expected constraints.
     * Converts the file path to an absolute path if necessary.
     *
     * @param filePath The file path to validate.
     * @return A valid Path object representing the specified file path.
     * @throws IllegalArgumentException if the file path is invalid.
     */
    private Path validateFilePath(String filePath) {
        if (!filePath.toLowerCase().endsWith(".csv")) {
            throw new IllegalArgumentException(MESSAGE_INVALID_FILE_EXT);
        }
        if (filePath.matches(".*[<>\"\\|?*].*")) {
            throw new IllegalArgumentException(String.format(MESSAGE_USAGE, filePath));
        }

        Path path;
        try {
            path = Paths.get(filePath);
            if (!path.isAbsolute()) {
                path = Paths.get(System.getProperty("user.dir")).resolve(filePath);
            }
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_USAGE, filePath));
        }
        return path;
    }

    /**
     * Executes the ExportCommand by writing all contacts to a CSV file.
     *
     * @param model The model which contains the list of contacts.
     * @return A CommandResult indicating the success or failure of the export operation.
     * @throws CommandException If an error occurs during the export process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            // Validate file path in execution to catch any issues
            validateFilePath(filePath.toString());

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("name,category,studentId/industry,phone,email,address,tags\n");

                for (Person person : model.getAddressBook().getPersonList()) {
                    writer.write(serializePerson(person));
                    writer.newLine();
                }

                return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
            }
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(MESSAGE_USAGE, filePath));
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, filePath));
        }
    }

    /**
     * Serializes a Person object into a CSV row format.
     *
     * @param person The Person to be serialized.
     * @return A String representing the serialized Person in CSV format.
     */
    private String serializePerson(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(escapeCsv(person.getName().fullName)).append(","); // Name
        if (person instanceof Student) {
            Student student = (Student) person;
            sb.append("student,").append(escapeCsv(student.getStudentId().toString())).append(","); // studentID
        } else if (person instanceof Company) {
            Company company = (Company) person;
            sb.append("company,").append(escapeCsv(company.getIndustry().toString())).append(","); // industry
        }
        sb.append(escapeCsv(person.getPhone().value)).append(","); // Phone
        sb.append(escapeCsv(person.getEmail().value)).append(","); // Email
        sb.append(escapeCsv(person.getAddress().value)).append(","); // Address
        sb.append(serializeTags(person.getTags())); // Tags
        return sb.toString();
    }

    /**
     * Escapes fields for CSV format by wrapping in double quotes if they contain commas or quotes.
     * Double quotes within fields are doubled according to CSV conventions.
     */
    public String escapeCsv(String field) {
        // Check if the field contains any commas, quotes, or newline characters
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            // Escape any double quotes by doubling them
            field = field.replace("\"", "\"\"");
            // Wrap the entire field in double quotes
            return "\"" + field + "\"";
        }
        // If no special characters are present, return the field as is
        return field;
    }



    /**
     * Serializes a set of Tag objects into a space-separated string.
     *
     * @param tags The set of Tag objects to be serialized.
     * @return A String representing the tags, separated by spaces.
     */
    private String serializeTags(Set<Tag> tags) {
        return tags.stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.joining(" "));
    }

    /**
     * Checks if this ExportCommand is equal to another object.
     * Two ExportCommand objects are considered equal if they have the same file path.
     *
     * @param other The other object to compare with.
     * @return true if both objects have the same file path, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ExportCommand)) {
            return false;
        }

        ExportCommand otherCommand = (ExportCommand) other;
        return filePath.equals(otherCommand.filePath);
    }

    /**
     * Returns the hash code for this ExportCommand.
     *
     * @return The hash code for this ExportCommand, based on the file path.
     */
    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
