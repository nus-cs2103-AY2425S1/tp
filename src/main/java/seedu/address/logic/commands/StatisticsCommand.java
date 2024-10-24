package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

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

    public static final String COMMAND_WORD = "statistics";

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
            String tagCode = person.getTag().toString(); // get person's tag
            JobCodeStatistics jobStats = jobStatisticsMap.getOrDefault(jobCode, new JobCodeStatistics());
            jobStats.incrementTag(tagCode);
            jobStatisticsMap.put(jobCode, jobStats);
        }
    }

    private String buildStatisticsMessage(HashMap<JobCode, JobCodeStatistics> jobStatisticsMap) {
        StringBuilder statisticsMessage = new StringBuilder();
        appendHeading(statisticsMessage, "STATISTICS");
        appendHeading(statisticsMessage, "Total number of applicants by Job Code and Interview Stages");

        int totalApplicantsInSystem = 0;

        // Iterate over the job statistics and build the message
        for (JobCode jobCode : jobStatisticsMap.keySet()) {
            JobCodeStatistics jobStats = jobStatisticsMap.get(jobCode);

            appendJobCodeStatistics(statisticsMessage, jobCode.value, jobStats.getTotalApplicants());

            // Appends breakdown of interview stages of job applicants
            appendStagesBreakdown(statisticsMessage, jobStats);

            // Update the total count of applicants in the system
            totalApplicantsInSystem += jobStats.getTotalApplicants();
        }

        // Total number of applicants
        statisticsMessage.append("\nTotal number of applicants in the system: ")
                .append(totalApplicantsInSystem)
                .append("\n");

        return statisticsMessage.toString();
    }

    private void appendJobCodeStatistics(StringBuilder statisticsMessage, String jobCode, int totalApplicants) {
        statisticsMessage.append(jobCode)
                .append(": ")
                .append(totalApplicants)
                .append(" applicants\n");
    }

    // Helper method to append formatted headings
    private void appendHeading(StringBuilder builder, String heading) {
        builder.append(heading).append("\n")
                .append("--------\n");
    }

    // New method to append stages breakdown
    private void appendStagesBreakdown(StringBuilder builder, JobCodeStatistics jobStats) {
        builder.append("      N: ").append(jobStats.getN())
                .append("      TP: ").append(jobStats.getTP())
                .append("      TC: ").append(jobStats.getTC())
                .append("      BP: ").append(jobStats.getBP())
                .append("      BC: ").append(jobStats.getBC())
                .append("      A: ").append(jobStats.getA())
                .append("      R: ").append(jobStats.getR()).append("\n");
    }
}
