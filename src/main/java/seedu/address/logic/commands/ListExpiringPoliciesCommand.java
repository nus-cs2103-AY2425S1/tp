package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyExpiryDatePredicate;

/**
 * Lists all policies in the address book that are nearing expiry within the next 30 days.
 */
public class ListExpiringPoliciesCommand extends Command {

    /**
     * Command word to trigger this command in the application's UI.
     */
    public static final String COMMAND_WORD = "listExpiringPolicies";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all policies nearing expiry within the next 30 days.\n"
            + "Example: " + COMMAND_WORD;

    private static final String MESSAGE_SUCCESS = "The following policies are near expiry:";
    private static final String MESSAGE_NO_EXPIRING_POLICY = "No policies expiring within the next 30 days!";
    private static final String MESSAGE_FAILURE = "Failed to retrieve expiring policies. Please try again.";
    private static final String MESSAGE_POLICY_LISTED_DETAILS = "Insuree phone: %1$s, Policy Type: %2$s, "
            + "Premium Amount: %3$s, Coverage Amount: %4$s, Expiry Date: %5$s";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            LocalDate currentDate = LocalDate.now();

            PolicyExpiryDatePredicate predicate = new PolicyExpiryDatePredicate(currentDate);
            model.updateFilteredPolicyList(predicate);

            List<Policy> expiringPolicies = model.getFilteredPolicyList();

            if (expiringPolicies.isEmpty()) {
                return new CommandResult(MESSAGE_NO_EXPIRING_POLICY);
            }

            // Construct the success message with policy details
            StringBuilder resultMessage = new StringBuilder(MESSAGE_SUCCESS);
            for (Policy policy : expiringPolicies) {
                Person insuree = policy.getInsuree();
                resultMessage.append(String.format(
                        MESSAGE_POLICY_LISTED_DETAILS,
                        insuree.getPhone().toString(),
                        policy.getType().toString(),
                        policy.getPremiumAmount(),
                        policy.getCoverageAmount(),
                        policy.getExpiryDate().toLocalDate())); // convert LocalDateTime to LocalDate for output
            }

            return new CommandResult(resultMessage.toString());

        } catch (Exception e) {
            return new CommandResult(MESSAGE_FAILURE);
        }
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListExpiringPoliciesCommand)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return COMMAND_WORD;
    }
}

