package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.Model;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.JobCodeStatistics;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class StatisticsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    private HashMap<String, Integer> tagCounts = new HashMap<String, Integer>() {
        {
            put("N", 0);
            put("BP", 0);
            put("BC", 0);
            put("TP", 0);
            put("TC", 0);
            put("A", 0);
            put("R", 0);
        }
    };

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        ObservableList<Person> lastShownList = model.getFilteredPersonList();
        FilteredList<Person> fullContactList = new FilteredList<>(lastShownList);
        HashMap<JobCode, JobCodeStatistics> jobStatisticsMap = new HashMap<>();
        calculateJobCodeStatistics(fullContactList, jobStatisticsMap);
        String statisticsMessage = buildStatisticsMessage(jobStatisticsMap);
        return new CommandResult(statisticsMessage);
    }

    private void calculateJobCodeStatistics(FilteredList<Person> fullContactList,
                                            HashMap<JobCode, JobCodeStatistics> jobStatisticsMap) {
        for (Person person : fullContactList) {
            JobCode jobCode = person.getJobCode(); // get person job code
            String tagCode = person.getTag().tagCode; // get person's tag
            JobCodeStatistics jobStats = jobStatisticsMap.getOrDefault(jobCode, new JobCodeStatistics());

            // Increment the tag count for the specific tag code
            jobStats.incrementTag(tagCode);
            // Update the map with the modified JobCodeStatistics object
            jobStatisticsMap.put(jobCode, jobStats);
            // Update the tagCounts for all applicants
            tagCounts.put(tagCode, tagCounts.get(tagCode) + 1);
        }
    }

    private String buildStatisticsMessage(HashMap<JobCode, JobCodeStatistics> jobStatisticsMap) {
        StringBuilder statisticsMessage = new StringBuilder();

        int totalApplicantsInSystem = 0;
        // Convert the jobStatisticsMap entries into a list
        // The sorting algorithm below was written with the aid of ChatGPT.
        List<Map.Entry<JobCode, JobCodeStatistics>> sortedEntries = new ArrayList<>(jobStatisticsMap.entrySet());
        sortedEntries.sort((entry1, entry2) -> {
            int result = entry1.getKey().value.toLowerCase().compareTo(entry2.getKey().value.toLowerCase());
            if (result == 0) {
                result = entry1.getKey().value.compareTo(entry2.getKey().value);
            }
            return result;
        });

        // Iterate over the job statistics and build the message
        for (Map.Entry<JobCode, JobCodeStatistics> entry : sortedEntries) {
            JobCode jobCode = entry.getKey();
            JobCodeStatistics jobStats = entry.getValue();

            appendJobCodeStatistics(statisticsMessage, jobCode.value, jobStats.getTotalApplicants());

            // Appends breakdown of interview stages of job applicants
            appendStagesBreakdown(statisticsMessage, jobStats);

            // update the total count of applicants in the system
            totalApplicantsInSystem += jobStats.getTotalApplicants();
        }
        statisticsMessage.insert(0, "Total number of applicant(s) by job code and interview Stages:\n");
        // insert the total number of tags across all job codes
        insertTagPercentages(statisticsMessage, tagCounts, totalApplicantsInSystem);
        insertHeading(statisticsMessage, "Total number of applicant(s) in system: " + totalApplicantsInSystem);
        insertHeading(statisticsMessage, "STATISTICS");
        return statisticsMessage.toString();
    }

    private void appendJobCodeStatistics(StringBuilder statisticsMessage, String jobCode, int totalApplicants) {
        statisticsMessage.append(jobCode)
                .append(": ")
                .append(totalApplicants)
                .append(" applicant(s)\n");
    }

    // Helper method to append formatted headings
    private void insertHeading(StringBuilder builder, String heading) {
        builder.insert(0, heading + "\n--------" + "\n");
    }


    // Appends breakdown of interview stages for a given JobCodeStatistics instance
    private void appendStagesBreakdown(StringBuilder builder, JobCodeStatistics jobStats) {
        builder.append("      N: ").append(jobStats.getN())
                .append("      TP: ").append(jobStats.getTP())
                .append("      TC: ").append(jobStats.getTC())
                .append("      BP: ").append(jobStats.getBP())
                .append("      BC: ").append(jobStats.getBC())
                .append("      A: ").append(jobStats.getA())
                .append("      R: ").append(jobStats.getR()).append("\n");
    }

    private void insertTagPercentages(StringBuilder builder, HashMap<String, Integer> tagCounts,
                                      int totalApplicantsInSystem) {
        StringBuilder stringToInsert = new StringBuilder();
        stringToInsert.append("Percentages of applicant(s) in each interview stage:\n");

        // Correct order of tags
        // The code from this line onwards was written with the aid of ChatGPT
        String[] tagOrder = {"N", "TP", "TC", "BP", "BC", "A", "R"};

        // Iterate over the tag order and append the % of each tag
        for (String tag : tagOrder) {
            int count = tagCounts.getOrDefault(tag, 0); // Get the count for the tag (default to 0 if not present
            double percentage = totalApplicantsInSystem == 0 ? 0.0 : (
                    (double) count / totalApplicantsInSystem) * 100;

            // Append the tag and its count/percentage to the string in the correct order
            stringToInsert.append("  ")
                    .append(tag)
                    .append(": ")
                    .append(String.format("%.2f", percentage))
                    .append("%      ");
        }

        // Insert the tag percentages at the beginning of the builder
        builder.insert(0, stringToInsert.toString().trim() + "\n--------\n");
    }
}
