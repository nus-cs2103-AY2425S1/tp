package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
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
    public static final String MESSAGE_FAILURE = "Failed to export contacts to: %1$s";
    public static final String MESSAGE_USAGE = "Error: Invalid file path format!\n\n"
            + "To specify the file path for export, use one of the following formats:\n\n"
            + "- Absolute Path: The complete path starting from the root directory. Examples:\n"
            + "  - Windows: C:\\Users\\username\\Documents\\project\\data\\file.csv\n"
            + "  - macOS/Linux: /home/username/project/data/file.csv\n\n"
            + "- Relative Path: A path relative to the current working directory of the application. "
            + "For example, if the application is running in /home/username/project:\n"
            + "  - data/file.csv will refer to /home/username/project/data/file.csv.\n\n"
            + "Please ensure the file path format matches one of the above styles.";


    private final Path filePath;

    /**
     * Creates an ExportCommand to export contacts to the specified file path.
     *
     * @param filePath The file path to which contacts will be exported.
     */
    public ExportCommand(String filePath) {
        requireNonNull(filePath);
        // Use Paths.get() to handle both relative and absolute paths
        this.filePath = Paths.get(filePath).isAbsolute() ? Paths.get(filePath)
                : Paths.get(System.getProperty("user.dir"), filePath);
    }

    /**
     * Executes the ExportCommand by writing all contacts to a CSV file.
     *
     * @param model The model which contains the list of contacts.
     * @return A CommandResult indicating the success or failure of the export operation.
     * @throws CommandException If an I/O error occurs during the export process.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            // Write the header row for the CSV file
            writer.write("name,category,studentId/industry,phone,email,address,tags\n");

            // Write each contact to the CSV file
            for (Person person : model.getAddressBook().getPersonList()) {
                writer.write(serializePerson(person));
                writer.newLine();
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
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
    private String escapeCsv(String field) {
        if (field.contains(",") || field.contains("\"")) {
            // Escape double quotes within the field by doubling them and wrap the entire field in quotes
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
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
