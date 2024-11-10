package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

public class SummaryCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = getTypicalAddressBook();
        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void execute_summaryDisplaysCorrectCounts() {
        SummaryCommand summaryCommand = new SummaryCommand();

        // Generate expected summary message
        String expectedMessage = generateExpectedSummaryMessage(model);

        assertCommandSuccess(summaryCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Generates the expected summary message based on the typical address book.
     */
    private String generateExpectedSummaryMessage(Model model) {
        // Initialize a map with all statuses set to 0
        Map<String, Long> statusCounts = new HashMap<>();
        for (String status : Status.VALID_STATUSES) {
            statusCounts.put(status, 0L);
        }

        // Populate map with actual counts from the address book
        for (Person person : model.getAddressBook().getPersonList()) {
            String statusValue = person.getStatus().value;
            statusCounts.put(statusValue, statusCounts.get(statusValue) + 1);
        }

        // Format the expected summary message
        long totalApplicants = model.getAddressBook().getPersonList().size();
        StringBuilder summary = new StringBuilder();
        for (String status : Status.VALID_STATUSES) {
            summary.append(String.format("Number of applicants %s: %d\n", status, statusCounts.get(status)));
        }

        return String.format(SummaryCommand.MESSAGE_SUCCESS, totalApplicants, summary.toString().trim());
    }
}
