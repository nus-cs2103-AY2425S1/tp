package seedu.address.logic.commands;

import java.util.List;

import org.apache.commons.text.similarity.LevenshteinDistance;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Finds the closest match for a given target name from a list of persons using the Levenshtein distance algorithm.
     *
     * @param targetName The name to match against the list of persons. This value is case-insensitive.
     * @param personList The list of Person objects to search for closest match.
     * @return The name of the closest match if found within a reasonable distance; otherwise, returns null.
     *         The threshold for a "reasonable distance" is currently set to 3.
     */
    public String findClosestMatch(String targetName, List<Person> personList) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        String closestMatch = null;
        int closestDistance = Integer.MAX_VALUE;
        String targetNameLowerCase = targetName.toLowerCase();

        for (Person person : personList) {
            String personNameLowerCase = person.getName().toString().toLowerCase();
            int distance = levenshteinDistance.apply(targetNameLowerCase, personNameLowerCase);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestMatch = person.getName().toString();
            }
        }

        return closestDistance <= 3 ? closestMatch : null;
    }
}
