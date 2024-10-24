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
import seedu.address.model.person.student.StudentID;
import seedu.address.model.tag.Tag;

public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Contacts imported successfully from: %1$s";
    public static final String MESSAGE_FAILURE = "Failed to import contacts from: %1$s";
    public static final String MESSAGE_INVALID_CATEGORY = "Invalid category in CSV. Category must be either 'student' "
            + "or 'company'";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports file in comma-separated value(CSV) format"
            + "Example: " + COMMAND_WORD + " /path/to/file.csv";

    private final String filePath;

    public ImportCommand(String filePath) {
        requireNonNull(filePath);
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        int successCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",", -1); // Split CSV by commas and include empty strings for empty fields

                if (values.length < 6) {
                    throw new CommandException("Invalid CSV format");
                }

                Name name = new Name(values[0].trim());
                String category = values[1].trim();
                Phone phone = new Phone(values[3].trim());
                Email email = new Email(values[4].trim());
                Address address = new Address(values[5].trim());
                Set<Tag> tagSet = parseTags(values, 6); // Start parsing tags from the 6th field onwards

                switch (category.toLowerCase()) {
                case "student":
                    // For students
                    String studentID = values[2].trim();
                    if (!studentID.isEmpty()) {
                        Student student = new Student(name, new StudentID(studentID), phone, email, address, tagSet);
                        if (!model.hasPerson(student)) {
                            new AddStudentCommand(student).execute(model); // Add student using AddStudentCommand
                            successCount++;
                        }
                    } else {
                        throw new CommandException("Missing Student ID for student category");
                    }
                    break;

                case "company":
                    // For companies
                    String industry = values[2].trim();
                    if (!industry.isEmpty()) {
                        Company company = new Company(name, new Industry(industry), phone, email, address, tagSet);
                        if (!model.hasPerson(company)) {
                            new AddCompanyCommand(company).execute(model); // Add company using AddCompanyCommand
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

            return new CommandResult(String.format(MESSAGE_SUCCESS, filePath) + "\nSuccessfully imported: " + successCount + " entries");

        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_FAILURE, filePath));
        }
    }

    /**
     * Parses tags starting from the specified index in the CSV values array.
     */
    private Set<Tag> parseTags(String[] values, int startIndex) {
        Set<Tag> tags = new HashSet<>();
        for (int i = startIndex; i < values.length; i++) {
            String tagName = values[i].trim();
            if (!tagName.isEmpty()) {
                tags.add(new Tag(tagName));
            }
        }
        return tags;
    }
    
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

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }
}
