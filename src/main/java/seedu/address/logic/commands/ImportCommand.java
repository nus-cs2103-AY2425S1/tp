package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
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
            + "Parameters: FILE_PATH"
            + "[" + PREFIX_PATH + "FILE_PATH]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PATH + "data/persons.csv";

    private final String csvFilePath;

    public ImportCommand(String csvFilePath) {
        this.csvFilePath = csvFilePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> newPersons = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] fields;

            // Skip header line
            csvReader.readNext();

            while ((fields = csvReader.readNext()) != null) {
                if (fields.length < 7) {
                    throw new CommandException("Invalid CSV format.");
                }
                Person person = parsePerson(fields, model);
                newPersons.add(person);
            }
        } catch (IOException | CsvValidationException e) {
            throw new CommandException("Error reading from the CSV file: " + e.getMessage());
        }
        model.replaceAllPersons(newPersons);

        return new CommandResult(String.format("Successfully imported %d persons.", newPersons.size()));
    }


    /**
     * Parses a person from CSV fields.
     */
    private Person parsePerson(String[] fields, Model model) throws CommandException {
        try {
            Name name = ParserUtil.parseName(fields[0].trim());
            Phone phone = ParserUtil.parsePhone(fields[1].trim());
            Email email = ParserUtil.parseEmail(fields[2].trim());
            Telegram telegram = ParserUtil.parseTelegram(fields[3].trim());
            Github github = ParserUtil.parseGithub(fields[5].trim());

            // Process tags
            Set<Tag> tags = parseTags(fields[4].trim());

            Map<String, Assignment> assignment = parseAssignment(fields[6].trim(), model);

            Set<Integer> weeksPresent = new HashSet<>();
            if (!fields[7].trim().isEmpty()) {
                String[] weeksArray = fields[7].split(",");
                for (String week : weeksArray) {
                    int weekPresent = ParserUtil.parseWeek(week.trim());
                    weeksPresent.add(Integer.parseInt(week.trim()));
                }
            }

            return new Person(name, phone, email, telegram, github, assignment, weeksPresent, tags);

        } catch (IllegalArgumentException | ParseException e ) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Parses a set of tags from a string with tags in the format "[tag1],[tag2]".
     */
    private Set<Tag> parseTags(String tagField) {
        Set<Tag> tags = new HashSet<>();

        // Remove surrounding brackets for each tag
        tagField = tagField.trim();
        if (!tagField.isEmpty()) {
            // Split on commas first, then remove the square brackets from each tag
            String[] tagArray = tagField.split(",");
            for (String tag : tagArray) {
                tag = tag.trim(); // Remove any extra spaces
                if (tag.startsWith("[") && tag.endsWith("]")) {
                    tag = tag.substring(1, tag.length() - 1); // Remove the brackets
                }
                if (!tag.isEmpty()) {
                    tags.add(new Tag(tag));
                }
            }
        }
        return tags;
    }

    /**
     * Parses a set of tags from a string with tags in the format "assignmentName | score".
     */
    private Map<String, Assignment> parseAssignment(String assignmentField, Model model) throws NumberFormatException, CommandException {
        Map<String, Assignment> assignments = new HashMap<>();
        assignmentField = assignmentField.trim();
        if (!assignmentField.isEmpty()) {
            String[] assignmentArray = assignmentField.split(",");
            for (String assignment : assignmentArray) {
                assignment = assignment.trim();
                List<String> individual = Stream.of(assignment.split("\\|"))
                        .map(String::trim).toList(); // | used as delimiter between name and score
                String asgnName = individual.get(0);
                Float asgnScore = Float.parseFloat(individual.get(1));
                if (!model.hasAssignment(asgnName)) {
                    throw new CommandException("Invalid assignment name: " + asgnName);
                }

                if (asgnScore > model.maxScore(asgnName) || asgnScore < 0) {
                    throw new CommandException("Score must be between 0.0 and " + model.maxScore(asgnName));
                }
                assignments.put(asgnName, new Assignment(asgnName, asgnScore));
            }
        }
        return assignments;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImportCommand other)) {
            return false;
        }
        return this.csvFilePath.equals(other.csvFilePath);
    }
}



