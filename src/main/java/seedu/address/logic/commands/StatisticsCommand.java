package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * Lists all persons in the address book to the user.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "statistics";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // create a filtered list of all the predicate persons
        // then create iteration
        ObservableList<Person> lastShownList = model.getFilteredPersonList();
        FilteredList<Person> fullContactList = new FilteredList<>(lastShownList);
        int numOfApplicants = 0;
        HashMap<JobCode, Integer> jobDict = new HashMap<JobCode, Integer>();
        HashMap<Tag, Integer> tagDict = new HashMap<Tag, Integer>();
        for (Person person : fullContactList) {
            numOfApplicants++;
            JobCode jobCode = person.getJobCode();
            jobDict.put(jobCode, jobDict.getOrDefault(jobCode, 0) + 1);
            Tag tag = person.getTag();
            tagDict.put(tag, tagDict.getOrDefault(tag, 0) + 1);
        }

        StringBuilder statisticsMessage = new StringBuilder("STATISTICS\n"
                + "--------\n"
                + "Total number of applicants: " + numOfApplicants + "\n"
                + "Number of applicants by Job Code\n"
                + "--------\n");

        // Append JobCode statistics to the message
        for (JobCode jobCode : jobDict.keySet()) {
            statisticsMessage.append(jobCode.value)
                    .append(": ")
                    .append(jobDict.get(jobCode))
                    .append("\n");
        }
        return new CommandResult(statisticsMessage.toString());
    }
}
