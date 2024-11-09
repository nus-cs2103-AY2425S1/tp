package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Status;

/**
 * Displays a summary of the application statuses of all candidates.
 */
public class SummaryCommand extends Command {
    public static final String COMMAND_WORD = "summary";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a summary of all candidates' application statuses.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Total number of applicants: %d\n%s";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        assert model != null : "Model should not be null";

        // Initialize a map with all statuses set to 0
        Map<String, Long> statusCounts = new HashMap<>();
        for (String status : Status.VALID_STATUSES) {
            statusCounts.put(status, 0L);
        }

        // Populate map with actual counts
        model.getAddressBook().getPersonList().stream()
                .collect(Collectors.groupingBy(person -> {
                    assert person != null : "Person should not be null.";
                    assert person.getStatus() != null : "Person's status should not be null.";
                    return person.getStatus().value;
                }, Collectors.counting()))
                .forEach(statusCounts::put);

        // Total number of applicants
        long totalApplicants = model.getAddressBook().getPersonList().size();
        assert totalApplicants > 0 : "Total applicants should be greater than zero if list is non-empty.";

        // Format the summary message
        StringBuilder summary = new StringBuilder();
        for (String status : Status.VALID_STATUSES) {
            summary.append(String.format("Number of applicants %s: %d\n", status, statusCounts.get(status)));
        }

        assert summary.length() > 0 : "Summary string should not be empty.";

        String resultMessage = String.format(MESSAGE_SUCCESS, totalApplicants, summary.toString().trim());
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryCommand); // instanceof handles nulls
    }
}
