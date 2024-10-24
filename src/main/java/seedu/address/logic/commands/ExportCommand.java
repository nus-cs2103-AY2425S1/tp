package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts to a CSV file.\n"
            + "Example: " + COMMAND_WORD + " /path/to/export.csv";

    private final String filePath;

    /**
     * Creates an ExportCommand to export contacts to the specified file path.
     *
     * @param filePath The file path to which contacts will be exported.
     */
    public ExportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
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
        sb.append(person.getName().fullName).append(","); // Name
        if (person instanceof Student) {
            Student student = (Student) person;
            sb.append("student,").append(student.getStudentID().toString()).append(","); // Category and studentID
        } else if (person instanceof Company) {
            Company company = (Company) person;
            sb.append("company,").append(company.getIndustry()).append(","); // Category and industry
        }
        sb.append(person.getPhone().value).append(","); // Phone
        sb.append(person.getEmail().value).append(","); // Email
        sb.append(person.getAddress().value).append(","); // Address
        sb.append(serializeTags(person.getTags())); // Tags
        return sb.toString();
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
