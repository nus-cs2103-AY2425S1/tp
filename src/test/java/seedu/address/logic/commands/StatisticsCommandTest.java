package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.JobCodeStatistics;

public class StatisticsCommandTest {
    private Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_emptyList_success() {
        StatisticsCommand stats = new StatisticsCommand();
        CommandResult commandResult = stats.execute(emptyModel);
        String expectedMessage = "STATISTICS\n"
                + "--------\n" + "Total number of applicant(s) in contact book: 0\n"
                + "--------\n" + "Percentages of applicant(s) in each interview stage:\n"
                + "  N: 0.00%        TP: 0.00%        TC: 0.00%        BP: 0.00%        BC: 0.00%        "
                + "A: 0.00%        R: 0.00%\n"
                + "--------\n" + "Total number of applicant(s) by job code and interview stages:";
        assertEquals(expectedMessage.trim(), commandResult.getFeedbackToUser().trim());
    }

    @Test
    public void test_buildStatisticsMessage() {
        StatisticsCommand stats = new StatisticsCommand();
        HashMap<JobCode, JobCodeStatistics> jobStatisticsMap = new HashMap<>();
        String actualMessage = stats.buildStatisticsMessage(jobStatisticsMap);
        String expectedMessage = "STATISTICS\n"
                + "--------\n" + "Total number of applicant(s) in contact book: 0\n"
                + "--------\n" + "Percentages of applicant(s) in each interview stage:\n"
                + "  N: 0.00%        TP: 0.00%        TC: 0.00%        BP: 0.00%        BC: 0.00%        "
                + "A: 0.00%        R: 0.00%\n"
                + "--------\n" + "Total number of applicant(s) by job code and interview stages:";
        assertEquals(expectedMessage.trim(), actualMessage.trim());
    }

    @Test
    public void test_CalculateJobCodeStatistics() {

    }
}
