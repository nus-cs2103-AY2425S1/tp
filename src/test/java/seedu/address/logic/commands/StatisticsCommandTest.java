package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.JobCode;
import seedu.address.model.person.JobCodeStatistics;
import seedu.address.model.person.Person;

public class StatisticsCommandTest {
    private Model emptyModel = new ModelManager(new AddressBook(), new UserPrefs());
    private Model typicalModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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
    public void testBuildStatisticsMessageEmptyListSuccess() {
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
    public void testCalculateJobCodeStatistics() {
        FilteredList<Person> typicalPersons = (FilteredList<Person>) typicalModel.getFilteredPersonList();
        HashMap<JobCode, JobCodeStatistics> testHashMap = new HashMap<>();
        StatisticsCommand stats = new StatisticsCommand();
        stats.calculateJobCodeStatistics(typicalPersons, testHashMap);

        // Typical persons Jobcodes (Each has 1)
        // SWE2024 (N), HRD2025 (TP), INTERN087 (TC), HRD (BP), 135800PLS (BC), CCN32 (A), 345 (R)
        // make sure that each tag is incremented correctly for each job code
        assertEquals(1, testHashMap.get(new JobCode("SWE2024")).getN());
        assertEquals(1, testHashMap.get(new JobCode("HRD2025")).getTP());
        assertEquals(1, testHashMap.get(new JobCode("INTERN087")).getTC());
        assertEquals(1, testHashMap.get(new JobCode("HRD")).getBP());
        assertEquals(1, testHashMap.get(new JobCode("135800PLS")).getBC());
        assertEquals(1, testHashMap.get(new JobCode("CCN32")).getA());
        assertEquals(1, testHashMap.get(new JobCode("345")).getR());
    }

    @Test
    public void testBuildStatisticsMessageFilledListSuccess() {
        StatisticsCommand stats = new StatisticsCommand();
        FilteredList<Person> typicalPersons = (FilteredList<Person>) typicalModel.getFilteredPersonList();
        HashMap<JobCode, JobCodeStatistics> testHashMap = new HashMap<>();
        stats.calculateJobCodeStatistics(typicalPersons, testHashMap);
        String expectedString = "STATISTICS\n"
                + "--------\n" + "Total number of applicant(s) in contact book: 7\n"
                + "--------\n" + "Percentages of applicant(s) in each interview stage:\n"
                + "  N: 14.29%        TP: 14.29%        TC: 14.29%        BP: 14.29%        BC: 14.29%        "
                + "A: 14.29%        R: 14.29%\n"
                + "--------\n" + "Total number of applicant(s) by job code and interview stages:\n"
                + "135800PLS: 1 applicant(s)\n" + "      N: 0      TP: 0      TC: 0      BP: 0      BC: 1      "
                + "A: 0      R: 0\n"
                + "345: 1 applicant(s)\n" + "      N: 0      TP: 0      TC: 0      BP: 0      BC: 0      "
                + "A: 0      R: 1\n"
                + "CCN32: 1 applicant(s)\n" + "      N: 0      TP: 0      TC: 0      BP: 0      BC: 0      "
                + "A: 1      R: 0\n"
                + "HRD: 1 applicant(s)\n" + "      N: 0      TP: 0      TC: 0      BP: 1      BC: 0      "
                + "A: 0      R: 0\n"
                + "HRD2025: 1 applicant(s)\n" + "      N: 0      TP: 1      TC: 0      BP: 0      BC: 0      "
                + "A: 0      R: 0\n"
                + "INTERN087: 1 applicant(s)\n" + "      N: 0      TP: 0      TC: 1      BP: 0      BC: 0      "
                + "A: 0      R: 0\n"
                + "SWE2024: 1 applicant(s)\n" + "      N: 1      TP: 0      TC: 0      BP: 0      BC: 0      "
                + "A: 0      R: 0\n";
        assertEquals(expectedString.trim(), stats.buildStatisticsMessage(testHashMap).trim());
    }


}
