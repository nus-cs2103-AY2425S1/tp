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

    public static final String MESSAGE_DISPLAY_TOTAL_PEOPLE = "Total Number Of People: %s";
    public static final String MESSAGE_DISPLAY_HIGH_PRIORITY = "Number Of HIGH Priority People: %s";
    public static final String MESSAGE_DISPLAY_MEDIUM_PRIORITY = "Number Of MEDIUM Priority People: %s";
    public static final String MESSAGE_DISPLAY_LOW_PRIORITY = "Number Of LOW Priority People: %s";
    public static final String MESSAGE_LACK_PRIORITY = "Each person should have a priority";

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
        allStats[0] = String.format(MESSAGE_DISPLAY_TOTAL_PEOPLE, nbOfPeople);
        allStats[1] = String.format(MESSAGE_DISPLAY_HIGH_PRIORITY, highPriority);
        allStats[2] = String.format(MESSAGE_DISPLAY_MEDIUM_PRIORITY, mediumPriority);
        allStats[3] = String.format(MESSAGE_DISPLAY_LOW_PRIORITY, lowPriority);
        for (int i = 0; i < nbOfPeople; i++) {
            Priority pri = lastShownList.get(i).getPriority();

            switch (pri) {

            case HIGH:
                highPriority++;
                allStats[1] = String.format("Number Of HIGH Priority People: %s", highPriority);
                break;

            case MEDIUM:
                mediumPriority++;
                allStats[2] = String.format("Number Of MEDIUM Priority People: %s", mediumPriority);
                break;

            case LOW:
                lowPriority++;
                allStats[3] = String.format("Number Of LOW Priority People: %s", lowPriority);
                break;

            default:
                throw new CommandException(MESSAGE_LACK_PRIORITY);
            }
        }
        for (int i = 0; i < allStats.length; i++) {
            s += allStats[i] + "\n";
        }
        return new CommandResult(String.format(MESSAGE_DISPLAY_STATISTICS_SUCCESS, s));
    }
}
