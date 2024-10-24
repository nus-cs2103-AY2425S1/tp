package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
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
 * Imports contacts from a CSV file into the address book.
 * Supports adding both students and companies.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Contacts imported successfully from: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to import contacts from: %1$s";
    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category in CSV. Category must be either 'student' "
            + "or 'company'";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports file in comma-separated value(CSV) format.\n"
            + "Example: " + COMMAND_WORD + " /path/to/file.csv";
    private final String filePath;

    /**
     * Creates an ImportCommand to import contacts from the specified CSV file path.
     *
     * @param filePath The path of the CSV file to import contacts from.
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    /**
     * Executes the ImportCommand, reading contacts from the specified CSV file
     * and adding them to the address book.
     *
     * @param model The model in which contacts are to be added.
     * @return A CommandResult indicating success or failure of the import operation.
     * @throws CommandException If an error occurs during file reading or processing.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int successCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Split CSV by commas

                if (values.length < 6) {
                    throw new CommandException("Invalid CSV format");
                }

                Name name = new Name(values[0].trim());
                String category = values[1].trim();
                Phone phone = new Phone(values[3].trim());
                Email email = new Email(values[4].trim());
                Address address = new Address(values[5].trim());
                Set<Tag> tagSet = parseTags(values.length > 6 ? values[6].trim() : "");

                switch (category.toLowerCase()) {
                case "student":
                    String studentId = values[2].trim();
                    if (!studentId.isEmpty()) {
                        Student student = new Student(name, new StudentId(studentId), phone, email, address, tagSet);
                        if (!model.hasPerson(student)) {
                            new AddStudentCommand(student).execute(model);
                            successCount++;
                        }
                    } else {
                        throw new CommandException("Missing Student ID for student category");
                    }
                    break;

                case "company":
                    String industry = values[2].trim();
                    if (!industry.isEmpty()) {
                        Company company = new Company(name, new Industry(industry), phone, email, address, tagSet);
                        if (!model.hasPerson(company)) {
                            new AddCompanyCommand(company).execute(model);
                            successCount++;
                        }
                    } else {
                        throw new CommandException("Missing Industry for company category");
                    }
                    break;

                default:
                    throw new CommandException(MESSAGE_INVALID_CATEGORY);
                }
            }

            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath)
                    + "\nSuccessfully imported: " + successCount + " entries");

        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, filePath));
        }
    }

    /**
     * Parses a comma-separated string of tags into a {@code Set<Tag>}.
     *
     * @param tagsString The comma-separated string of tags.
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
     * Checks if two ImportCommand objects are equal.
     * Two ImportCommand objects are considered equal if they have the same file path.
     *
     * @param other The other object to compare with.
     * @return true if both objects have the same file path, false otherwise.
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
     * Returns the hash code for this ImportCommand.
     *
     * @return The hash code for this ImportCommand, based on the file path.
     */
    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
