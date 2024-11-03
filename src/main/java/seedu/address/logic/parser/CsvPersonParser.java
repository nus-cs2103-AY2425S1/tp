package seedu.address.logic.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.exceptions.CommandException;
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
 * Utility class to parse a CSV row into a Person object.
 */
public class CsvPersonParser {
    /**
     * Parses a CSV row into a Person object using model data.
     *
     * @param fields CSV data fields representing a person
     * @param model the application model
     * @return a Person object created from the CSV data
     * @throws CommandException if the CSV data cannot be parsed into a Person object
     */
    public static Person parsePerson(String[] fields, Model model) throws CommandException {
        try {

            // Use parse fields using ParserUtil.
            Name name = ParserUtil.parseName(fields[0].trim());
            Phone phone = ParserUtil.parsePhone(fields[1].trim());
            Email email = ParserUtil.parseEmail(fields[2].trim());
            Telegram telegram = ParserUtil.parseTelegram(fields[3].trim());
            Github github = ParserUtil.parseGithub(fields[5].trim());

            // Parse tags, assignments and week present.
            Set<Tag> tags = parseTags(fields[4].trim());
            Map<String, Assignment> assignment = parseAssignment(fields[6].trim(), model);
            Set<Integer> weeksPresent = parseWeeksPresent(fields[7].trim());

            // Return person with given information from a row of data.
            return new Person(name, phone, email, telegram, github, assignment, weeksPresent, tags);

        } catch (IllegalArgumentException | ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Parses the weeksPresent field into a set of integers.
     *
     * @param weeksField string representing weeks present, separated by commas
     * @return a set of integers representing weeks present
     * @throws ParseException if any week value is invalid
     */
    private static Set<Integer> parseWeeksPresent(String weeksField) throws ParseException {
        Set<Integer> weeksPresent = new HashSet<>();
        if (weeksField.isEmpty()) {
            return weeksPresent;
        }
        // Split data by coma and parse each week value as an integer.
        String[] weeksArray = weeksField.split(",");
        for (String week : weeksArray) {
            int weekPresent = ParserUtil.parseWeek(week.trim());
            weeksPresent.add(weekPresent);
        }

        return weeksPresent;
    }

    /**
     * Parses the tags field from a CSV field.
     *
     * @param tagField string representing tags, separated by commas
     * @return a set of Tag objects
     */
    private static Set<Tag> parseTags(String tagField) {
        Set<Tag> tags = new HashSet<>();

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
     * Parses the assignments field, where each assignment is represented as "assignmentName | score".
     *
     * @param assignmentField string representing assignments and their scores
     * @param model the application model
     * @return a map of assignment names to Assignment objects
     * @throws CommandException if assignment name or score is invalid
     */
    private static Map<String, Assignment> parseAssignment(String assignmentField, Model model) throws NumberFormatException,
            CommandException {
        Map<String, Assignment> assignments = new HashMap<>();
        assignmentField = assignmentField.trim();

        if (!assignmentField.isEmpty()) {
            String[] assignmentArray = assignmentField.split(",");
            for (String assignment : assignmentArray) {
                processAssignment(assignment.trim(), assignments, model);
            }
        }
        return assignments;
    }

    /**
     * Processes each assignment entry by validating the name and score, and adds it to the assignments map.
     *
     * @param assignment string representing a single assignment
     * @param assignments map to store validated assignments
     * @param model the application model
     * @throws CommandException if assignment name or score is invalid
     */
    private static void processAssignment(String assignment, Map<String, Assignment> assignments, Model model)
            throws CommandException {
        List<String> individual = Stream.of(assignment.split("\\|"))
            .map(String::trim).toList(); // | used as delimiter between name and score

        // Get assignment score and name from each individual assignment
        String asgnName = individual.get(0);
        float asgnScore = Float.parseFloat(individual.get(1));

        // Throws Command exception if assignment name is invalid
        if (!model.hasAssignment(asgnName)) {
            throw new CommandException("Invalid assignment name: " + asgnName);
        }

        // Throws Command exception if score is invalid (not parser exception because this happens during execution)
        if (asgnScore > model.maxScore(asgnName) || asgnScore < 0) {
            throw new CommandException("Score must be between 0.0 and " + model.maxScore(asgnName));
        }

        assignments.put(asgnName, new Assignment(asgnName, asgnScore));
    }
}
