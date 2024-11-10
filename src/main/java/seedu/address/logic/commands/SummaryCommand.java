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

        // Initialize a map with all statuses set to 0
        Map<String, Long> statusCounts = new HashMap<>();
        for (String status : Status.VALID_STATUSES) {
            statusCounts.put(status, 0L);
        }

        // Populate map with actual counts
        model.getAddressBook().getPersonList().stream()
                .collect(Collectors.groupingBy(person -> person.getStatus().value, Collectors.counting()))
                .forEach(statusCounts::put);

        // Total number of applicants
        long totalApplicants = model.getAddressBook().getPersonList().size();

        // Format the summary message
        StringBuilder summary = new StringBuilder();
        for (String status : Status.VALID_STATUSES) {
            summary.append(String.format("Number of applicants %s: %d\n", status, statusCounts.get(status)));
        }

        String resultMessage = String.format(MESSAGE_SUCCESS, totalApplicants, summary.toString().trim());
        return new CommandResult(resultMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SummaryCommand); // instanceof handles nulls
    }
}
