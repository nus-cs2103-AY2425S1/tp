package seedu.address.logic.parser;

import java.util.Map;
import java.util.Set;

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
     * @param fields CSV data fields representing a person.
     * @param model  the application model.
     * @return a Person object created from the CSV data.
     * @throws CommandException if the CSV data cannot be parsed into a Person object.
     */
    public static Person parsePerson(String[] fields, Model model) throws CommandException {
        try {

            // Use parse fields using ParserUtil.
            Name name = ParserUtil.parseName(fields[0]);
            Phone phone = ParserUtil.parsePhone(fields[1]);
            Email email = ParserUtil.parseEmail(fields[2]);
            Telegram telegram = ParserUtil.parseTelegram(fields[3]);
            Github github = ParserUtil.parseGithub(fields[5]);

            // Parse tags, assignments and week present.
            Set<Tag> tags = CsvTagParser.parseTags(fields[4]);
            Map<String, Assignment> assignment = CsvAssignmentParser.parseAssignment(fields[6], model);
            Set<Integer> weeksPresent = CsvAttendanceParser.parseWeeksPresent(fields[7]);

            // Return person with given information from a row of data.
            return new Person(name, phone, email, telegram, github, assignment, weeksPresent, tags);

        } catch (IllegalArgumentException | ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }


}
