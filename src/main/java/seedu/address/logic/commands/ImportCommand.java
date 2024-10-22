package seedu.address.logic.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Import command to load data from specified CSV file location.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports person data from a CSV file.\n"
        + "Parameters: FILE_PATH\n"
        + "Example: " + COMMAND_WORD + " data/persons.csv";

    private final String csvFilePath;

    public ImportCommand(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            List<Person> newPersons = new ArrayList<>();

            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 9) {
                    throw new CommandException("Invalid CSV format.");
                }

                try {
                    // Create person fields
                    Name name = new Name(fields[0].trim().replaceAll("\"", ""));
                    Phone phone = new Phone(fields[1].trim().replaceAll("\"", ""));
                    Email email = new Email(fields[2].trim().replaceAll("\"", ""));
                    Address address = new Address(fields[3].trim().replaceAll("\"", ""));
                    Telegram telegram = new Telegram(fields[4].trim().replaceAll("\"", ""));
                    Github github = new Github(fields[6].trim().replaceAll("\"", ""));

                    // Process tags
                    Set<Tag> tags = new HashSet<>();
                    if (!fields[5].trim().isEmpty() && !fields[5].equals("\"\"")) {
                        String[] tagArray = fields[5].trim().split(",");
                        for (String tag : tagArray) {
                            tag = tag.trim();
                            if (!tag.isEmpty()) {
                                tags.add(new Tag(tag));
                            }
                        }
                    }

                    // Process assignment name
                    String assignmentNameStr = fields[7].trim().replaceAll("\"", "");
                    Assignment assignment;
                    if (assignmentNameStr.equals("N/A") || assignmentNameStr.isEmpty()) {
                        Person person = new Person(name, phone, email, address, telegram, tags, github);
                        newPersons.add(person);
                        continue;
                    }

                    // Process assignment score
                    String assignmentScoreStr = fields[8].trim().replaceAll("\"", "");

                    float assignmentScore = Float.parseFloat(assignmentScoreStr);

                    assignment = new Assignment(assignmentNameStr, assignmentScore);

                    // Create new person
                    Person person = new Person(name, phone, email, address, telegram, tags, github,
                        assignment);
                    newPersons.add(person);

                } catch (IllegalArgumentException e) {
                    // Skip invalid entries but continue processing
                    throw new CommandException(e.getMessage());
                }
            }

            // Replace all persons in the model with the new ones
            model.replaceAllPersons(newPersons);

            return new CommandResult(String.format("Successfully imported %d persons.", newPersons.size()));

        } catch (IOException e) {
            throw new CommandException("Error reading from the CSV file: " + e.getMessage());
        }
    }
}



