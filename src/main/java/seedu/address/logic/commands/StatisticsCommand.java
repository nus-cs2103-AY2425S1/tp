package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Priority;

/**
 * Displays the overall statistics of the people in the addressbook.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the overall statistics regarding all the people in SocialBook.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DISPLAY_STATISTICS_SUCCESS = "Here are all the statistics:\n%s";

    /**
     * Displays all the overall statistics to be shown.
     *
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        String s = "";
        int nbOfPeople = lastShownList.size();
        int highPriority = 0;
        int mediumPriority = 0;
        int lowPriority = 0;
        String[] allStats = new String[4];
        allStats[0] = String.format("Total Nb Of People: %s", nbOfPeople);
        allStats[1] = String.format("Number Of HIGH Priority People: %s", highPriority);
        allStats[2] = String.format("Number Of MEDIUM Priority People: %s", mediumPriority);
        allStats[3] = String.format("Number Of LOW Priority People: %s", lowPriority);
        for (int i = 0; i < nbOfPeople; i++) {
            Priority prior = lastShownList.get(i).getPriority();
            if (prior == Priority.HIGH) {
                highPriority++;
                allStats[1] = String.format("Number Of HIGH Priority People: %s", highPriority);
            } else if (prior == Priority.MEDIUM) {
                mediumPriority++;
                allStats[2] = String.format("Number Of MEDIUM Priority People: %s", mediumPriority);
            } else if (prior == Priority.LOW) {
                lowPriority++;
                allStats[3] = String.format("Number Of LOW Priority People: %s", lowPriority);
            }
        }
        for (int i = 0; i < allStats.length; i++) {
            s += allStats[i] + "\n";
        }
        return new CommandResult(String.format(MESSAGE_DISPLAY_STATISTICS_SUCCESS, s));
    }
}
